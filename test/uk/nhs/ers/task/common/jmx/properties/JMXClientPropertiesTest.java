package uk.nhs.ers.task.common.jmx.properties;


import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.Test;


public class JMXClientPropertiesTest
{
	@Test
	public void testSimple()
	{
		Assert.assertEquals(JMXClientProperties.SERVICE_PORT.getKey(), "jmxclient.port");
		Assert.assertEquals(JMXClientProperties.SERVICE_SERVER.getKey(), "jmxclient.server");
		Assert.assertEquals(JMXClientProperties.CLIENT_SLEEP_LENGTH.getKey(), "jmxclient.sleep.length");
		Assert.assertTrue(Integer.valueOf(JMXClientProperties.CLIENT_SLEEP_LENGTH.getDefaultValue()) > 0,
				"Client sleep length default should be integer > 0");
	}


	@Test
	public void testProperties()
	{
		final Properties properties = JMXClientProperties.toProperties();
		Assert.assertEquals(properties.getProperty(JMXClientProperties.SERVICE_PORT.getKey()),
				JMXClientProperties.SERVICE_PORT.getDefaultValue());
	}
}
