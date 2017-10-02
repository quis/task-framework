package uk.nhs.ers.task.common.service;


import java.util.ArrayList;
import java.util.Map;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.testng.Assert;
import org.testng.annotations.Test;

import uk.nhs.ers.task.common.factory.PoolSizeChangeListenerFactory;
import uk.nhs.ers.task.common.mock.MockProperties;
import uk.nhs.ers.task.common.mock.MockServiceEventHandler;
import uk.nhs.ers.task.common.mock.MockTaskService;
import uk.nhs.ers.task.common.mock.MockTaskServiceProperties;
import uk.nhs.ers.task.common.mock.MockTaskServicePropertyUtilities;
import uk.nhs.ers.task.common.mock.MockThreadPoolFactory;
import uk.nhs.ers.task.common.schedule.ScheduledTaskService;
import uk.nhs.ers.task.common.schedule.TaskSchedule;


public class AbstractTaskServiceTest
{
	@Test
	public void testSimple()
	{
		final String serviceName = "Banana";
		final MockThreadPoolFactory threadPoolFactory = new MockThreadPoolFactory(1, 2, 30);
		final PoolSizeChangeListenerFactory poolSizeChangedFactory = new PoolSizeChangeListenerFactory();
		final MockServiceEventHandler eventHandler = new MockServiceEventHandler();


		final ThreadPoolTaskScheduler scheduleExecutor = new ThreadPoolTaskScheduler();
		scheduleExecutor.initialize();

		final ScheduledTaskService scheduledTaskService =
				new ScheduledTaskService(scheduleExecutor, new ArrayList<TaskSchedule>());

		final MockTaskService service =
				new MockTaskService(serviceName, threadPoolFactory,
						poolSizeChangedFactory, scheduleExecutor, scheduledTaskService);

		service.registerEventHandler(ServiceEventType.PAUSED, eventHandler);
		service.registerEventHandler(ServiceEventType.RESUMED, eventHandler);
		service.registerEventHandler(ServiceEventType.STARTED, eventHandler);
		service.registerEventHandler(ServiceEventType.STOPPED, eventHandler);

		Assert.assertEquals(service.getName(), serviceName);
		Assert.assertEquals(service.getScheduler(), scheduleExecutor);

		Assert.assertFalse(service.isPaused(), "Service should not be paused at start");

		service.start();
		Assert.assertTrue(eventHandler.eventOccurred(ServiceEventType.STARTED, 0));
		Assert.assertTrue(service.isStarted(), "Service should be started");

		service.pause();
		Assert.assertTrue(eventHandler.eventOccurred(ServiceEventType.PAUSED, 1));
		Assert.assertTrue(service.isPaused(), "Service should be paused");

		service.resume();
		Assert.assertTrue(eventHandler.eventOccurred(ServiceEventType.RESUMED, 2));
		Assert.assertFalse(service.isPaused(), "Service should not be paused after resuming");

		service.stop();
		Assert.assertTrue(eventHandler.eventOccurred(ServiceEventType.STOPPED, 3));
		Assert.assertFalse(service.isStarted(), "Service should not be started");
	}


	@Test
	public void testProperties()
	{
		final String serviceName = "Apple";
		final MockThreadPoolFactory threadPoolFactory = new MockThreadPoolFactory(1, 2, 30);
		final PoolSizeChangeListenerFactory poolSizeChangedFactory = new PoolSizeChangeListenerFactory();
		new MockServiceEventHandler();


		final ThreadPoolTaskScheduler scheduleExecutor = new ThreadPoolTaskScheduler();
		scheduleExecutor.initialize();

		final ScheduledTaskService scheduledTaskService =
				new ScheduledTaskService(scheduleExecutor, new ArrayList<TaskSchedule>());

		final MockTaskService service =
				new MockTaskService(serviceName, threadPoolFactory,
						poolSizeChangedFactory, scheduleExecutor, scheduledTaskService);

		final MockProperties properties = new MockProperties();
		service.setProperties(properties);
		final MockTaskServicePropertyUtilities taskPropertyUtilities = new MockTaskServicePropertyUtilities();
		service.setTaskPropertyUtilities(taskPropertyUtilities);
		final String newValue = "new_property_value";
		service.setProperty(MockTaskServiceProperties.PROPERTY.getKey(), newValue);
		final Map<String, String> propertyMap = service.getAllProperties();
		Assert.assertEquals(propertyMap, properties.asMap());
		Assert.assertEquals(service.getTaskPropertyUtilities(), taskPropertyUtilities);
		Assert.assertEquals(service.getProperty("Key1"), propertyMap.get("Key1"));
		Assert.assertEquals(service.getProperty(MockTaskServiceProperties.PROPERTY.getKey()),
				newValue);
		Assert.assertNull(service.getProperty(MockTaskServiceProperties.POOL_SIZE.getKey()));
		Assert.assertEquals(service.getProperties(), properties);
	}
}
