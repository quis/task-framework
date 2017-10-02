package uk.nhs.ers.task.common.jmx.factory;


import java.io.IOException;
import java.util.Properties;

import javax.management.MalformedObjectNameException;

import org.testng.Assert;
import org.testng.annotations.Test;

import uk.nhs.ers.task.common.jmx.cli.JMXClientCLIArgumentException;
import uk.nhs.ers.task.common.jmx.properties.JMXClientProperties;
import uk.nhs.ers.task.common.mock.MockJMXClientFactory;


public class AbstractJMXClientFactoryTest
{
	@Test(expectedExceptions = { JMXClientCLIArgumentException.class })
	public void testNoServer() throws MalformedObjectNameException, IOException
	{
		final Properties properties = new Properties();
		properties.put("jmxclient.server", "asdf");
		properties.put("jmxclient.port", "");
		final MockJMXClientFactory factory = new MockJMXClientFactory(properties);
		factory.createClient();
	}


	@Test(expectedExceptions = { JMXClientCLIArgumentException.class })
	public void testNoPort() throws MalformedObjectNameException, IOException
	{
		final Properties properties = new Properties();
		properties.put("jmxclient.server", "");
		properties.put("jmxclient.port", "asdf");
		final MockJMXClientFactory factory = new MockJMXClientFactory(properties);
		factory.createClient();
	}


	@Test
	public void testUrlBuild()
	{
		final String server = "server";
		final String port = "port";

		final MockJMXClientFactory factory = new MockJMXClientFactory(JMXClientProperties.toProperties());
		Assert.assertEquals(factory.createMBeanServerURLTest(server, port),
				"service:jmx:rmi:///jndi/rmi://server:port/jmxrmi");
	}
}
