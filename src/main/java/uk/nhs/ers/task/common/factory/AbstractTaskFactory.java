package uk.nhs.ers.task.common.factory;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import uk.nhs.ers.task.common.dao.ITaskDao;
import uk.nhs.ers.task.common.exception.TaskCreationException;
import uk.nhs.ers.task.common.provider.ITaskProvider;
import uk.nhs.ers.task.common.task.ITask;
import uk.nhs.ers.task.common.task.TaskDefinition;
import uk.nhs.ers.task.common.task.TaskStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ThreadFactoryBuilder;


/**
 *
 * {@link AbstractTaskFactory} provides Abstract Factory for creating Tasks
 *
 * @param <T> the particular type of ITask that this factory will create
 */
@SuppressWarnings("PMD.TooManyMethods")
public abstract class AbstractTaskFactory<T extends ITask> implements ITaskFactory, ApplicationContextAware
{
	private static final Logger LOG = LoggerFactory.getLogger(AbstractTaskFactory.class);
	private final AtomicBoolean backgroundServiceStarted = new AtomicBoolean(false);
	private final Properties properties;
	private final ExecutorService taskExecutor = Executors
			.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("ObtainTasks-%d").build());

	private ApplicationContext context;

	private final ITaskDao taskDao;
	private final ITaskProvider taskProvider;

	private final BlockingQueue<TaskDefinition> taskQueue;
	private final ITaskMapper taskMapper;


	/**
	 * base constructor for a TaskFactory
	 *
	 * @param taskDao
	 * @param taskProvider
	 * @param properties
	 * @param taskMapper
	 */
	public AbstractTaskFactory(final ITaskDao taskDao, final ITaskProvider taskProvider, final Properties properties,
			final ITaskMapper taskMapper)
	{
		this.properties = properties;
		this.taskQueue = Queues.newArrayBlockingQueue(getTaskQueueSize());
		this.taskDao = taskDao;
		this.taskProvider = taskProvider;
		this.taskMapper = taskMapper;
	}


	/**
	 * Start the candidate task manager.
	 */
	protected void start()
	{
		final Runnable runner = getTaskManager();

		getTaskExecutor().execute(runner);
		this.backgroundServiceStarted.set(true);
	}


	/**
	 * Stop the candidate Executor
	 */
	public void stop()
	{
		this.taskExecutor.shutdownNow();
	}


	/**
	 * Sleep for the supplied number of milliseconds
	 *
	 * @param millis
	 */
	protected void pause(final long millis)
	{
		try
		{
			Thread.sleep(millis);
		}
		catch (final InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
	}


	/**
	 * Get the task executor
	 *
	 * @returns the executor
	 */
	public ExecutorService getTaskExecutor()
	{
		return this.taskExecutor;
	}


	/**
	 * Create a new ITask from the given taskDefinition object
	 *
	 * @param taskDef
	 * @param taskDao
	 * @return
	 * @throws TaskCreationException
	 */
	protected ITask create(final TaskDefinition taskDef) throws TaskCreationException
	{

		final ITask task = createTaskClass(taskDef);


		try
		{
			task.parseParameters(taskDef.getTaskParameters());
		}
		catch (final IOException ioe)
		{
			throw new TaskCreationException(
					String.format("Cannot create task parameters from %s", taskDef.getTaskParameters()), ioe);
		}

		task.setTaskDef(taskDef);
		task.setTaskDao(this.taskDao);


		if (taskDef.getIdentifier() != null)
		{
			task.setIdentifier(taskDef.getIdentifier());
		}

		return task;
	}


	/**
	 * Create an ITask object from the given TaskDefinition
	 *
	 * @param taskDef
	 * @param task
	 * @return
	 * @throws TaskCreationException
	 */
	private ITask createTaskClass(final TaskDefinition taskDef) throws TaskCreationException
	{
		ITask task = null;

		try
		{
			final Class<? extends ITask> clazz = getTaskMapper().mapClass(taskDef.getTaskType());


			task = clazz.getConstructor().newInstance();
		}
		catch (final ClassNotFoundException cnfe)
		{
			throw new TaskCreationException(String.format("Cannot find class %s", taskDef.getTaskType()), cnfe);
		}
		catch (InstantiationException | IllegalAccessException ex)
		{
			throw new TaskCreationException(String.format("Cannot create task from %s", taskDef.getTaskType()), ex);
		}
		catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ce)
		{
			throw new TaskCreationException(String.format("No default constructor on %s", taskDef.getTaskType()), ce);
		}
		return task;
	}


	/**
	 * Poll for a task from the queue
	 *
	 * @return
	 * @throws TaskCreationException
	 */
	protected TaskDefinition pollForTask() throws TaskCreationException
	{

		if (!this.backgroundServiceStarted.get())
		{
			start();
		}

		TaskDefinition taskDef = null;
		do
		{
			try
			{
				LOG.debug("Trying to obtain next task from pending queue, current queueSize={}",
						getTaskQueueSize());
				taskDef = this.taskQueue.poll(getDefaultPollPeriodDays(), TimeUnit.DAYS);
				LOG.debug("Obtained task from pending queue [{}]", taskDef);
			}
			catch (final InterruptedException e)
			{
				LOG.warn("Task polling interrupted.", e);
				Thread.currentThread().interrupt();
				stop();
				throw new TaskCreationException("Task polling interrupted", e);
			}
		}
		while ((null == taskDef) || taskDef.hasExpired());

		return taskDef;
	}


	/**
	 * Get the default poll period
	 *
	 * @return
	 */
	protected abstract int getDefaultPollPeriodDays();


	/**
	 * get the task queue size
	 *
	 * @return
	 */
	protected abstract int getTaskQueueSize();


	/**
	 * Get the task mapper for this implementation
	 *
	 * @return
	 */
	protected ITaskMapper getTaskMapper()
	{
		return this.taskMapper;
	}


	/**
	 * get a Runnable TaskManager
	 *
	 * @return
	 */
	protected Runnable getTaskManager()
	{
		return new TaskManager();
	}


	/**
	 * Get the task queue
	 *
	 * @return
	 */
	protected BlockingQueue<TaskDefinition> getTaskQueue()
	{
		return this.taskQueue;
	}


	@Override
	public ITask newTask() throws TaskCreationException
	{
		ITask task = null;
		final TaskDefinition taskDef = pollForTask();
		try
		{
			task = create(taskDef);
			decorateTask(task);
		}
		catch (final TaskCreationException tce)
		{
			this.taskDao.updateStatus(taskDef, TaskStatus.FAILED, tce.getMessage());
			throw tce;
		}
		return task;
	}


	/**
	 * Decorate a task with any other bits and bobs it needs
	 *
	 * @param task
	 */
	protected abstract void decorateTask(ITask task);


	protected abstract int getDefaultOfferPeriodDays();


	protected abstract int getSleepPeriod();


	private final class TaskManager implements Runnable
	{

		@Override
		public void run()
		{
			LOG.info("Task Manager Started");

			do
			{
				LOG.debug("Requesting more tasks...");
				final List<TaskDefinition> newTaskDefs = getTaskProvider().obtainTasks();

				LOG.debug("Obtained [{}] tasks from task provider.", newTaskDefs.size());

				if (newTaskDefs.isEmpty())
				{
					waitForTasks();
				}
				else
				{
					for (final TaskDefinition taskDef : newTaskDefs)
					{
						LOG.debug("Offering task [{}]", taskDef);
						try
						{


							while (!getTaskQueue().offer(taskDef,
									getDefaultOfferPeriodDays(),
									TimeUnit.DAYS))
							{

								LOG.info("Could not add task [{}] to queue, retrying...", taskDef);
							}
						}
						catch (final InterruptedException e)
						{
							LOG.warn("Offering task [{}] to queue interrupted", taskDef, e);
							Thread.currentThread().interrupt();
						}
					}
				}
			}
			while (!Thread.currentThread().isInterrupted());
		}


		/**
		 * wait for the configured amount of time before checking for tasks again
		 */
		private void waitForTasks()
		{
			final int sleepPeriod = getSleepPeriod();

			LOG.debug("No tasks available. Pausing for {}ms before trying again", sleepPeriod);
			pause(sleepPeriod);

		}
	}


	/**
	 * Get the task provider
	 *
	 * @return the taskProvider
	 */
	protected ITaskProvider getTaskProvider()
	{
		return this.taskProvider;
	}


	/**
	 * Returns the value for the field {@link properties}
	 *
	 * @return This factory's properties
	 */
	public Properties getProperties()
	{
		return this.properties;
	}


	/**
	 * get the application context
	 *
	 * @return ApplicationContext
	 */
	public ApplicationContext getApplicationContext()
	{
		return this.context;
	}


	@Override
	public void setApplicationContext(final ApplicationContext actx) throws BeansException
	{
		this.context = actx;
	}


	@Override
	public TaskDefinition extractDefinition(final ITask task) throws JsonProcessingException
	{
		return new TaskDefinition(getTaskMapper().unMapClass(task.getClass()), task.encodeParameters());
	}
}

