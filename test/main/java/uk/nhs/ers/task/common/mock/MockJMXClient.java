package uk.nhs.ers.task.common.mock;


import java.io.IOException;

import javax.management.MalformedObjectNameException;

import uk.nhs.ers.task.common.jmx.client.AbstractJMXClient;
import uk.nhs.ers.task.common.jmx.client.JMXClientProperty;
import uk.nhs.ers.task.common.service.ITaskService;


public class MockJMXClient extends AbstractJMXClient
{
	public MockJMXClient(final String mBeanServerURL, final String mBeanObjectName,
			final Class<? extends ITaskService> serviceClass)
			throws IOException, MalformedObjectNameException
	{
		super(mBeanServerURL, mBeanObjectName, serviceClass);
	}


	@Override
	public String getPropertyKey(final JMXClientProperty property)
	{
		return String.format("key-%s", property.toString());
	}
}
