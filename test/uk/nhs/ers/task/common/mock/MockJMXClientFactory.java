package uk.nhs.ers.task.common.mock;


import java.io.IOException;
import java.util.Properties;

import javax.management.MalformedObjectNameException;

import uk.nhs.ers.task.common.jmx.client.IJMXClient;
import uk.nhs.ers.task.common.jmx.factory.AbstractJMXClientFactory;
import uk.nhs.ers.task.common.service.ITaskService;


public class MockJMXClientFactory extends AbstractJMXClientFactory
{
	Class<? extends ITaskService> clazz = MockTaskService.class;


	public MockJMXClientFactory(final Properties properties)
	{
		super(properties);

	}


	public MockJMXClientFactory(final Properties properties, final Class<? extends ITaskService> clazz)
	{
		super(properties);
		this.clazz = clazz;
	}


	@Override
	protected IJMXClient createClient(final String mBeanServerUrl, final String mBeanObjectName)
			throws MalformedObjectNameException, IOException
	{

		return new MockJMXClient(mBeanServerUrl, mBeanObjectName, this.clazz);
	}


	@Override
	protected String getMBeanObjectName()
	{
		return "bob";
	}


	public String createMBeanServerURLTest(final String server, final String port)
	{
		return createMBeanServerURL(server, port);
	}
}
