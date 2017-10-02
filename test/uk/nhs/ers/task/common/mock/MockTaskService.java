package uk.nhs.ers.task.common.mock;


import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import uk.nhs.ers.task.common.factory.IPauseableThreadPoolFactory;
import uk.nhs.ers.task.common.factory.IPoolSizeChangeListenerFactory;
import uk.nhs.ers.task.common.schedule.ScheduledTaskService;
import uk.nhs.ers.task.common.service.AbstractTaskService;


@ManagedResource()
public class MockTaskService extends AbstractTaskService implements MockTaskServiceMBean
{
	private boolean started = false;


	public MockTaskService(final String serviceName, final IPauseableThreadPoolFactory threadPoolFactory,
			final IPoolSizeChangeListenerFactory poolChangeListenerFactory,
			final ThreadPoolTaskScheduler scheduleExecutor, final ScheduledTaskService scheduledTaskService)
	{
		super(serviceName, threadPoolFactory, poolChangeListenerFactory, scheduleExecutor, scheduledTaskService);

	}


	@Override
	public void doStart()
	{
		setStarted(true);
	}


	@Override
	public void doStop()
	{
		setStarted(false);
	}


	@Override
	protected String getPoolSizePropertyName()
	{
		return MockTaskServiceProperties.POOL_SIZE.getKey();
	}


	/**
	 * Returns the value for the field {@link started}
	 */
	public boolean isStarted()
	{
		return this.started;
	}


	/**
	 * Sets the value for the field {@link started}
	 */
	public void setStarted(final boolean started)
	{
		this.started = started;
	}
}
