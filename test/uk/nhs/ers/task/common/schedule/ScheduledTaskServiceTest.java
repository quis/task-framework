package uk.nhs.ers.task.common.schedule;


import java.util.ArrayList;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.testng.Assert;
import org.testng.annotations.Test;

import uk.nhs.ers.task.common.factory.PoolSizeChangeListenerFactory;
import uk.nhs.ers.task.common.mock.MockScheduledTask;
import uk.nhs.ers.task.common.mock.MockTaskService;
import uk.nhs.ers.task.common.mock.MockThreadPoolFactory;


public class ScheduledTaskServiceTest
{
	@Test
	public void testService() throws InterruptedException
	{
		final String serviceName = "Cherry";
		final MockThreadPoolFactory threadPoolFactory = new MockThreadPoolFactory(1, 2, 30);
		final PoolSizeChangeListenerFactory poolSizeChangedFactory = new PoolSizeChangeListenerFactory();


		final ThreadPoolTaskScheduler scheduleExecutor = new ThreadPoolTaskScheduler();
		scheduleExecutor.initialize();

		final ArrayList<TaskSchedule> taskSchedules = new ArrayList<TaskSchedule>();
		taskSchedules.add(new TaskSchedule(new MockScheduledTask(), "* * * * * * "));


		final ScheduledTaskService scheduledTaskService =
				new ScheduledTaskService(scheduleExecutor, taskSchedules);

		final MockTaskService service =
				new MockTaskService(serviceName, threadPoolFactory,
						poolSizeChangedFactory, scheduleExecutor, scheduledTaskService);


		Assert.assertEquals(scheduledTaskService.getActiveFuturesCount(), 0);
		service.start();
		Assert.assertEquals(scheduledTaskService.getActiveFuturesCount(), 1);
		Thread.sleep(3000);
		Assert.assertEquals(scheduledTaskService.getActiveFuturesCount(), 1);
		service.pause();
		Assert.assertEquals(scheduledTaskService.getActiveFuturesCount(), 0);
		service.resume();
		Assert.assertEquals(scheduledTaskService.getActiveFuturesCount(), 1);
		service.stop();
		Assert.assertEquals(scheduledTaskService.getActiveFuturesCount(), 0);
	}
}
