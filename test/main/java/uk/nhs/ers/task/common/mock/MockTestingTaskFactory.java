package uk.nhs.ers.task.common.mock;


import java.sql.Timestamp;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;

import uk.nhs.ers.task.common.dao.ITaskDao;
import uk.nhs.ers.task.common.exception.TaskCreationException;
import uk.nhs.ers.task.common.factory.ITaskMapper;
import uk.nhs.ers.task.common.provider.ITaskProvider;
import uk.nhs.ers.task.common.task.TaskDefinition;

import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * {@link MockTestingTaskFactory} provides mock testing methods
 *
 */
public class MockTestingTaskFactory extends MockTaskFactory
{

	private boolean hasRun = false;


	/**
	 * @param taskDao
	 * @param taskProvider
	 * @param properties
	 */
	public MockTestingTaskFactory(final ITaskDao taskDao, final ITaskProvider taskProvider, final Properties properties,
			final ITaskMapper taskMapper)
	{
		super(taskDao, taskProvider, properties, taskMapper);

	}


	public void reset()
	{
		this.hasRun = false;
	}



	@Override
	protected Runnable getTaskManager()
	{
		return new SimpleRunnable();
	}


	@Override
	protected TaskDefinition pollForTask() throws TaskCreationException
	{

		start();

		try
		{
			return new TaskDefinition(MockTask.class.getName(),
					new MockTaskParameters("string", 1L, new Timestamp(1)).toJSON());
		}
		catch (final JsonProcessingException e)
		{
			throw new TaskCreationException(e);
		}
	}



	public BlockingQueue<TaskDefinition> getTaskQueueForTest()
	{
		return getTaskQueue();
	}


	/**
	 * Get the task provider fro testing
	 *
	 * @return
	 */
	public ITaskProvider getTaskProviderForTest()
	{
		return getTaskProvider();
	}



	/**
	 * Returns the value for the field {@link hasRun}
	 */
	public boolean hasRun()
	{
		return this.hasRun;
	}



	class SimpleRunnable implements Runnable
	{

		@Override
		public void run()
		{
			MockTestingTaskFactory.this.hasRun = true;

		}

	}



}
