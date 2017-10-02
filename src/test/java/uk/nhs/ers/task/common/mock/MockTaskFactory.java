package uk.nhs.ers.task.common.mock;


import java.util.Properties;

import uk.nhs.ers.task.common.dao.ITaskDao;
import uk.nhs.ers.task.common.exception.TaskCreationException;
import uk.nhs.ers.task.common.factory.AbstractTaskFactory;
import uk.nhs.ers.task.common.factory.ITaskMapper;
import uk.nhs.ers.task.common.provider.ITaskProvider;
import uk.nhs.ers.task.common.task.ITask;
import uk.nhs.ers.task.common.task.TaskDefinition;


public class MockTaskFactory extends AbstractTaskFactory<MockTask>
{
	public MockTaskFactory(final ITaskDao taskDao, final ITaskProvider taskProvider, final Properties properties,
			final ITaskMapper taskMapper)
	{
		super(taskDao, taskProvider, properties, taskMapper);
	}


	@Override
	protected int getDefaultPollPeriodDays()
	{
		return 1;
	}


	@Override
	protected int getTaskQueueSize()
	{
		return 1;
	}


	@Override
	protected void decorateTask(final ITask task)
	{
		// no impl
	}


	@Override
	protected int getDefaultOfferPeriodDays()
	{
		return 1;
	}


	@Override
	protected int getSleepPeriod()
	{
		return 1;
	}


	/** Create method for testing */
	public ITask createForTesting(final TaskDefinition taskDef) throws TaskCreationException
	{
		return create(taskDef);
	}


	/**
	 * Expose the method for testing
	 *
	 * @return the task manager runnable
	 */
	public Runnable getTaskManagerForTesting()
	{
		return getTaskManager();
	}


	public ITaskMapper getTaskMapperForTest()
	{
		return getTaskMapper();
	}
}
