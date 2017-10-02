package uk.nhs.ers.task.common.task;


import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.nhs.ers.task.common.dao.ITaskDao;
import uk.nhs.ers.task.common.exception.TaskCreationException;
import uk.nhs.ers.task.common.exception.TaskException;
import uk.nhs.ers.task.common.exception.TaskExecutionException;
import uk.nhs.ers.task.common.factory.TaskParametersFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.Sets;



/**
 * {@link AbstractTask} provides the basic implementation of a runnable task and its management
 *
 * @param <T>
 *
 */
public abstract class AbstractTask implements ITask, ITaskExceptionHandler
{

	private static final Logger LOG = LoggerFactory.getLogger(AbstractTask.class);
	private ITaskDao taskDao;
	private TaskDefinition taskDef;
	private ITaskParameters parameters;
	private String identifier;
	private TaskExecutionStatus status;
	private final Set<ITaskExceptionHandler> errorHandlers = Sets.newHashSet();


	/**
	 * get the Class of TaskParameters that this Task uses
	 *
	 * @return a descendant of AbstractTaskParameters
	 */
	public abstract Class<? extends AbstractTaskParameters> getParameterClass();


	/**
	 * Construct an archiver task.
	 */
	public AbstractTask()
	{
		this.identifier = UUID.randomUUID().toString();
		this.status = TaskExecutionStatus.CREATED;
		registerErrorHandler(this);
	}



	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		final long startTime = System.currentTimeMillis();
		this.status = TaskExecutionStatus.RUNNING;
		LOG.debug("Doing task: [{}]", this);
		try
		{
			doTask();
			this.status = TaskExecutionStatus.OK;
			this.taskDao.updateStatus(this, TaskStatus.COMPLETED, null);
		}
		catch (final TaskExecutionException | TaskCreationException tee)
		{
			this.status = TaskExecutionStatus.ERROR;
			informErrorHandlers(tee);
			try
			{
				this.taskDao.updateStatus(this, TaskStatus.FAILED, ExceptionUtils.getStackTrace(tee));
			}
			catch (final TaskCreationException tee2)
			{
				
				informErrorHandlers(tee2);
			}
		}
		finally
		{
			LOG.info(
					"Task Completed. type={} identifier={} status={} duration={} ms ",
					getTaskType(), getIdentifier(), this.status,
					getElapsedTime(startTime));
		}

	}


	@Override
	public void handleException(final TaskException exception)
	{
		LOG.error(String.format("Error detected. Task type=%s identifier=%s ",
				getTaskType(), getIdentifier()), exception);
	}


	/**
	 * register an error handler for this task
	 *
	 * @param handler
	 */
	public final void registerErrorHandler(final ITaskExceptionHandler handler)
	{
		this.errorHandlers.add(handler);
	}


	/**
	 * Inform all the error handlers that an error was thrown
	 *
	 * @param exception
	 */
	private void informErrorHandlers(final TaskException exception)
	{
		for (final ITaskExceptionHandler handler : this.errorHandlers)
		{
			handler.handleException(exception);
		}
	}


	/**
	 * how long since the given time?
	 *
	 * @param startTime
	 * @return milliseconds
	 */
	private long getElapsedTime(final long startTime)
	{
		return System.currentTimeMillis() - startTime;
	}


	@Override
	public void parseParameters(final String jsonParams) throws JsonParseException, JsonMappingException, IOException
	{
		this.parameters = TaskParametersFactory.fromJSON(this, jsonParams);

	}


	@Override
	public String encodeParameters() throws JsonProcessingException
	{
		return this.parameters.toJSON();

	}


	@Override
	public final String getTaskType()
	{
		return this.getClass().getSimpleName();

	}


	/**
	 * Returns the value for the field {@link taskDef} and also populates it if not set (for adding tasks)
	 *
	 * @return taskDefinition
	 * @throws TaskCreationException
	 * @throws JsonProcessingException
	 */
	@Override
	public TaskDefinition getTaskDef() throws TaskCreationException
	{
		return this.taskDef;
	}


	/**
	 * Sets the value for the field {@link taskDef}
	 */
	@Override
	public void setTaskDef(final TaskDefinition taskDef)
	{
		this.taskDef = taskDef;
	}


	/**
	 * Returns the value for the field {@link taskDao}
	 *
	 * @return taskDao
	 */
	@Override
	public ITaskDao getTaskDao()
	{
		return this.taskDao;
	}


	/**
	 * Sets the value for the field {@link taskDao}
	 */
	@Override
	public void setTaskDao(final ITaskDao taskDao)
	{
		this.taskDao = taskDao;
	}


	/**
	 * Returns the value for the field {@link identifier}
	 */
	@Override
	public String getIdentifier()
	{
		return this.identifier;
	}



	@Override
	public ITaskParameters getParameters()
	{
		return this.parameters;
	}


	@Override
	public void setIdentifier(final String identifier)
	{
		this.identifier = identifier;
	}

}
