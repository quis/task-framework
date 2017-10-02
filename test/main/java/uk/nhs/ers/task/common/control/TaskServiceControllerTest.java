package uk.nhs.ers.task.common.control;


import java.util.ArrayList;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.testng.Assert;
import org.testng.annotations.Test;

import uk.nhs.ers.task.common.factory.PoolSizeChangeListenerFactory;
import uk.nhs.ers.task.common.mock.MockTaskService;
import uk.nhs.ers.task.common.mock.MockThreadPoolFactory;
import uk.nhs.ers.task.common.schedule.ScheduledTaskService;
import uk.nhs.ers.task.common.schedule.TaskSchedule;


public class TaskServiceControllerTest
{
	@Test
	public void testSimple()
	{
		final String serviceName = "Banana";
		final String controllerName = "Apple";
		final MockThreadPoolFactory threadPoolFactory = new MockThreadPoolFactory(1, 2, 30);
		final PoolSizeChangeListenerFactory poolSizeChangedFactory = new PoolSizeChangeListenerFactory();
		final ThreadPoolTaskScheduler scheduleExecutor = new ThreadPoolTaskScheduler();
		scheduleExecutor.initialize();
		final ScheduledTaskService scheduledTaskService =
				new ScheduledTaskService(scheduleExecutor, new ArrayList<TaskSchedule>());
		final MockTaskService service =
				new MockTaskService(serviceName, threadPoolFactory,
						poolSizeChangedFactory, scheduleExecutor, scheduledTaskService);
		final TaskServiceController controller = new TaskServiceController(service, controllerName);
		controller.start();
		Assert.assertTrue(service.isStarted(), "Service should be started");
		controller.pause();
		Assert.assertTrue(service.isPaused(), "Service should be paused");
		controller.stop();
		Assert.assertFalse(service.isStarted(), "Service should be stopped");
	}
}
