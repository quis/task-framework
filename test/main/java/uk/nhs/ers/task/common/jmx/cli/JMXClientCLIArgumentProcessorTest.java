package uk.nhs.ers.task.common.jmx.cli;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.cli.Option;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import uk.nhs.ers.task.common.jmx.properties.JMXClientProperties;
import uk.nhs.ers.task.common.mock.MockJMXClient;


public class JMXClientCLIArgumentProcessorTest
{
	@Mock
	MockJMXClient mockClient;
	Properties properties = JMXClientProperties.toProperties();


	@BeforeClass
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
	}


	@Test
	public void testSimple()
	{
		final JMXClientCLIArgumentProcessor processor =
				new JMXClientCLIArgumentProcessor(this.mockClient, this.properties);

		final Option option = new Option("getrate", false, "Get Rate");

		processor.processArgument(option);
	}


	@Test
	public void testMultiple()
	{
		final JMXClientCLIArgumentProcessor processor =
				new JMXClientCLIArgumentProcessor(this.mockClient, this.properties);

		final List<Option> options = new ArrayList<Option>();

		for (final JMXClientCLIArgument arg : JMXClientCLIArgument.values())
		{
			options.add(new Option(arg.getName(), arg.hasArg(), arg.getDescription()));
		}
		processor.processArguments(options.toArray(new Option[] {}));

	}
}
