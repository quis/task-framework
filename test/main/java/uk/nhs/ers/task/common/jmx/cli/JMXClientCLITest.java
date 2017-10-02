package uk.nhs.ers.task.common.jmx.cli;


import java.io.IOException;
import java.util.Properties;

import javax.management.MalformedObjectNameException;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import uk.nhs.ers.task.common.jmx.client.AbstractJMXClient;
import uk.nhs.ers.task.common.jmx.factory.AbstractJMXClientFactory;
import uk.nhs.ers.task.common.jmx.properties.JMXClientProperties;


public class JMXClientCLITest
{
	@Mock
	AbstractJMXClientFactory factory;
	@Mock
	AbstractJMXClient client;


	@BeforeClass
	public void setup() throws MalformedObjectNameException, IOException
	{
		MockitoAnnotations.initMocks(this);
		Mockito.when(this.factory.createClient()).thenReturn(this.client);
	}


	@Test
	public void testSimple()
	{
		final Properties properties = JMXClientProperties.toProperties();
		final JMXClientCLI cli = new JMXClientCLI(this.factory, properties);
		cli.parseArguments("-status");
	}


	@Test
	public void testSetRateNoArg()
	{
		final Properties properties = JMXClientProperties.toProperties();
		final JMXClientCLI cli = new JMXClientCLI(this.factory, properties);
		cli.parseArguments(new String[] { "-setrate" });
	}


	@Test
	public void testNoArgs()
	{
		final Properties properties = JMXClientProperties.toProperties();
		final JMXClientCLI cli = new JMXClientCLI(this.factory, properties);
		cli.parseArguments((String[])null);
	}
}
