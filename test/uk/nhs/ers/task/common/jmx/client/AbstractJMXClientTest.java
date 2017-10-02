package uk.nhs.ers.task.common.jmx.client;


import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import uk.nhs.ers.task.common.factory.PoolSizeChangeListenerFactory;
import uk.nhs.ers.task.common.mock.MockJMXClient;
import uk.nhs.ers.task.common.mock.MockTaskService;
import uk.nhs.ers.task.common.mock.MockThreadPoolFactory;
import uk.nhs.ers.task.common.schedule.ScheduledTaskService;
import uk.nhs.ers.task.common.schedule.TaskSchedule;


public class AbstractJMXClientTest
{
	private static final String OBJECT_NAME = "uk.nhs.ers.task:type=MockTaskService";
	private static final String SERVER_URL = "service:jmx:rmi:///jndi/rmi://localhost:9990/jmxrmi";
	private MockTaskService mock;
	private MockJMXClient client;

	@Mock
	MockJMXClient mockClient;


	@BeforeClass
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
	}


	private void createMockMBean() throws Exception
	{
		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		final ObjectName name = new ObjectName(OBJECT_NAME);

		final MockThreadPoolFactory threadPoolFactory = new MockThreadPoolFactory(1, 2, 30);
		final PoolSizeChangeListenerFactory poolSizeChangedFactory = new PoolSizeChangeListenerFactory();



		final ThreadPoolTaskScheduler scheduleExecutor = new ThreadPoolTaskScheduler();
		scheduleExecutor.initialize();

		final ScheduledTaskService scheduledTaskService =
				new ScheduledTaskService(scheduleExecutor, new ArrayList<TaskSchedule>());

		final MockTaskService service =
				new MockTaskService("service", threadPoolFactory,
						poolSizeChangedFactory, scheduleExecutor, scheduledTaskService);


		if (mbs.isRegistered(name))
		{

			mbs.unregisterMBean(name);
		}
		mbs.registerMBean(service, name);
		this.mock = service;
	}


	@Test
	public void testGetPropertyKey() throws MalformedObjectNameException, IOException
	{
		Mockito.when(this.mockClient.getPropertyKey(JMXClientProperty.MAX_THREADS)).thenCallRealMethod();
		Mockito.when(this.mockClient.getPropertyKey(JMXClientProperty.THREAD_POOL_SIZE)).thenCallRealMethod();
		Assert.assertEquals(this.mockClient.getPropertyKey(JMXClientProperty.MAX_THREADS), "key-MAX_THREADS");
		Assert.assertEquals(this.mockClient.getPropertyKey(JMXClientProperty.THREAD_POOL_SIZE), "key-THREAD_POOL_SIZE");
	}
}
