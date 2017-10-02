package uk.nhs.ers.task.common.service;


import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.Test;


public class ServiceEventTest
{
	@Test
	public void construct()
	{
		final String serviceName = UUID.randomUUID().toString();
		final ServiceEventType type = ServiceEventType.STARTED;

		final ServiceEvent event = new ServiceEvent(serviceName, type);

		Assert.assertEquals(event.getServiceName(), serviceName);
		Assert.assertEquals(event.getEventType(), type);
	}
}
