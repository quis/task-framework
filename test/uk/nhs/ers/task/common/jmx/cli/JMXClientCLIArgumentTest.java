package uk.nhs.ers.task.common.jmx.cli;


import static org.testng.Assert.assertEquals;

import java.util.Map;
import java.util.Properties;

import org.apache.commons.cli.Option;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import uk.nhs.ers.task.common.jmx.client.AbstractJMXClient;

import com.google.common.collect.Maps;


public class JMXClientCLIArgumentTest
{
	@Mock
	AbstractJMXClient mockClient;
	@Mock
	Option mockOption;
	Option[] mockOptions;
	Properties properties;


	@Test
	public void testGets()
	{
		Assert.assertNotNull(JMXClientCLIArgument.GETRATE.getName(), "Should return a name");
		Assert.assertNotNull(JMXClientCLIArgument.GETRATE.getDescription(), "Should return a description");
	}


	@Test
	public void testFromName()
	{
		Assert.assertEquals(JMXClientCLIArgument.getValueFromName(JMXClientCLIArgument.STATUS.getName()),
				JMXClientCLIArgument.STATUS);
	}


	@Test
	public void testArgs()
	{
		for (final JMXClientCLIArgument arg : JMXClientCLIArgument.values())
		{
			if (arg.equals(JMXClientCLIArgument.SETRATE))
			{
				Assert.assertTrue(arg.hasArg(), "SETRATE should require argument");
			}
			else
			{
				Assert.assertFalse(arg.hasArg(), "JMXClientCLIArgument other than SETRATE should not require argument");
			}
		}
	}


	@BeforeMethod
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		this.mockOptions = new Option[] { this.mockOption };
		this.properties = new Properties();
	}


	@Test
	public void JMXClientCLIArgumentProcessor()
	{
		new JMXClientCLIArgumentProcessor(this.mockClient, this.properties);
		int exceptionCount = 0;
		try
		{
			new JMXClientCLIArgumentProcessor(null, this.properties);
		}
		catch (final NullPointerException npe)
		{
			exceptionCount++;
		}
		assertEquals(exceptionCount, 1);
	}


	@Test
	public void listConfig()
	{
		final Map<String, String> config = Maps.newHashMap();
		config.put("1", "11");
		config.put("2", "21");
		config.put("3", "31");
		Mockito.when(this.mockClient.getCurrentConfiguration()).thenReturn(config);

		Mockito.when(this.mockOption.getOpt()).thenReturn("listconfig");
		final JMXClientCLIArgumentProcessor argumentProcessor = new JMXClientCLIArgumentProcessor(this.mockClient,
				this.properties);
		argumentProcessor.processArguments(this.mockOptions);
		argumentProcessor.processArguments(this.mockOptions);
		argumentProcessor.processArguments(this.mockOptions);
		Mockito.verify(this.mockClient, Mockito.times(3)).getCurrentConfiguration();
		Mockito.verifyNoMoreInteractions(this.mockClient);
	}


	@Test
	public void logStatus()
	{
		final AbstractJMXClient mockThreadedClient = Mockito.mock(AbstractJMXClient.class);
		Mockito.when(mockThreadedClient.getRate()).thenReturn("1", "3", "5", "Not Numeric");

		Mockito.when(this.mockOption.getOpt()).thenReturn("status");
		final JMXClientCLIArgumentProcessor argumentProcessor = new JMXClientCLIArgumentProcessor(mockThreadedClient,
				this.properties);
		argumentProcessor.processArguments(this.mockOptions);
		argumentProcessor.processArguments(this.mockOptions);
		argumentProcessor.processArguments(this.mockOptions);
		Mockito.verify(mockThreadedClient, Mockito.times(3)).getStatus();
		Mockito.verifyNoMoreInteractions(mockThreadedClient);
	}


	@Test
	public void pause()
	{
		Mockito.when(this.mockOption.getOpt()).thenReturn("pause");
		final JMXClientCLIArgumentProcessor argumentProcessor = new JMXClientCLIArgumentProcessor(this.mockClient,
				this.properties);
		argumentProcessor.processArguments(this.mockOptions);
		argumentProcessor.processArguments(this.mockOptions);
		argumentProcessor.processArguments(this.mockOptions);
		Mockito.verify(this.mockClient, Mockito.times(3)).pause();
		Mockito.verify(this.mockClient, Mockito.times(3)).getStatus();
		Mockito.verifyNoMoreInteractions(this.mockClient);
	}


	@Test
	public void resume()
	{
		Mockito.when(this.mockOption.getOpt()).thenReturn("resume");
		final JMXClientCLIArgumentProcessor argumentProcessor = new JMXClientCLIArgumentProcessor(this.mockClient,
				this.properties);
		argumentProcessor.processArguments(this.mockOptions);
		argumentProcessor.processArguments(this.mockOptions);
		argumentProcessor.processArguments(this.mockOptions);
		Mockito.verify(this.mockClient, Mockito.times(3)).resume();
		Mockito.verify(this.mockClient, Mockito.times(3)).getStatus();
		Mockito.verifyNoMoreInteractions(this.mockClient);
	}


	@Test
	public void stop()
	{
		Mockito.when(this.mockOption.getOpt()).thenReturn("stop");
		final JMXClientCLIArgumentProcessor argumentProcessor = new JMXClientCLIArgumentProcessor(this.mockClient,
				this.properties);
		argumentProcessor.processArguments(this.mockOptions);
		argumentProcessor.processArguments(this.mockOptions);
		argumentProcessor.processArguments(this.mockOptions);
		Mockito.verify(this.mockClient, Mockito.times(3)).stop();
		Mockito.verifyNoMoreInteractions(this.mockClient);
	}



	@Test
	public void setRate()
	{
		final AbstractJMXClient mockThreadedClient = Mockito.mock(AbstractJMXClient.class);
		Mockito.when(mockThreadedClient.setRate(Mockito.anyInt())).thenReturn(1, 3, 5);

		Mockito.when(this.mockOption.getOpt()).thenReturn("setrate");
		Mockito.when(this.mockOption.getValue()).thenReturn("2", "4", "6", "Not Numeric");
		final JMXClientCLIArgumentProcessor argumentProcessor = new JMXClientCLIArgumentProcessor(mockThreadedClient,
				this.properties);
		argumentProcessor.processArguments(this.mockOptions);
		argumentProcessor.processArguments(this.mockOptions);
		argumentProcessor.processArguments(this.mockOptions);
		argumentProcessor.processArguments(this.mockOptions);
		Mockito.verify(mockThreadedClient, Mockito.times(1)).setRate(2);
		Mockito.verify(mockThreadedClient, Mockito.times(1)).setRate(4);
		Mockito.verify(mockThreadedClient, Mockito.times(1)).setRate(6);
		Mockito.verify(mockThreadedClient, Mockito.times(3)).getRate();
		Mockito.verifyNoMoreInteractions(mockThreadedClient);
	}



	@Test
	public void getRate()
	{
		final AbstractJMXClient mockThreadedClient = Mockito.mock(AbstractJMXClient.class);
		Mockito.when(mockThreadedClient.getRate()).thenReturn("1", "3", "5");

		Mockito.when(this.mockOption.getOpt()).thenReturn("getrate");
		final JMXClientCLIArgumentProcessor argumentProcessor = new JMXClientCLIArgumentProcessor(mockThreadedClient,
				this.properties);
		argumentProcessor.processArguments(this.mockOptions);
		argumentProcessor.processArguments(this.mockOptions);
		argumentProcessor.processArguments(this.mockOptions);
		Mockito.verify(mockThreadedClient, Mockito.times(3)).getRate();
		Mockito.verifyNoMoreInteractions(mockThreadedClient);
	}
}
