package uk.nhs.ers.task.common.jmx.cli;


import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import javax.management.MalformedObjectNameException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.nhs.ers.task.common.jmx.client.IJMXClient;
import uk.nhs.ers.task.common.jmx.factory.IJMXClientFactory;
import uk.nhs.ers.task.common.jmx.properties.JMXClientProperties;


/**
 * JMX Client Command Line Interface class. Parses user input, displays usage if required and delegates input to the
 * argument processor
 *
 */
public class JMXClientCLI
{
	private static final Logger LOG = LoggerFactory.getLogger(JMXClientCLI.class);

	private static final CommandLineParser COMMAND_LINE_PARSER = new DefaultParser();

	private static final HelpFormatter HELP_FORMATTER = new HelpFormatter();

	private final IJMXClientFactory factory;

	private final Properties properties;

	public static final String HELP_HEADER = "e-RS Task service remote client.";

	public static final String HELP_FOOTER = "";


	/**
	 * Constructs a new JMXClientCLI using the supplied client factory.
	 *
	 * @param factory - The JMXClientFactory to use when creating new JMXClients
	 * @param properties - The properties object to use for configurable values
	 */
	public JMXClientCLI(final IJMXClientFactory factory, final Properties properties)
	{
		this.factory = factory;
		this.properties = properties;
	}


	/**
	 * Parses the arguments supplied at the CLI and process them.
	 *
	 * @param args - An array of user supplied strings
	 */
	public void parseArguments(final String... args)
	{
		try
		{
			final Options options = buildOptions();
			final CommandLine line = COMMAND_LINE_PARSER.parse(options, args);
			LOG.debug(String.format("Processing input [%s] using [%s]", Arrays.toString(args), options));
			processInput(options, line);
		}
		catch (final ParseException pe)
		{
			LOG.error("Parsing failed.", pe);
		}
	}


	/**
	 * Attempts to process the supplied user input. Displays usage information if required.
	 *
	 * @param options - CLI Options acceptable as user input
	 * @param line - The parsed CLI Input
	 */
	public void processInput(final Options options, final CommandLine line)
	{

		if (shouldPrintUsage(line))
		{
			printUsage(options, HELP_FORMATTER);
		}
		else
		{
			try
			{

				final IJMXClient client = setup();
				final JMXClientCLIArgumentProcessor argProcessor = new JMXClientCLIArgumentProcessor(client,
						this.properties);
				argProcessor.processArguments(line.getOptions());
			}
			catch (MalformedObjectNameException | IOException e)
			{
				LOG.error("Failed to connect to client", e);
			}
			catch (final JMXClientCLIArgumentException iae)
			{
				LOG.error("Failed to connect to client", iae);
				LOG.info("Unknown service specified.");
			}
		}
	}


	private Options buildOptions()
	{
		LOG.debug("Constructing options");
		final Options options = new Options();
		for (final JMXClientCLIArgument arg : JMXClientCLIArgument.values())
		{
			options.addOption(arg.getName(), arg.hasArg(), arg.getDescription());
		}
		LOG.trace("Options: {}", options);
		HELP_FORMATTER.setOptionComparator(null);
		return options;
	}



	private IJMXClient setup() throws IOException, MalformedObjectNameException
	{
		LOG.debug("Setting up client connection to server");
		return this.factory.createClient();
	}


	protected boolean shouldPrintUsage(final CommandLine line)
	{
		boolean returnValue = false;
		if ((line == null) || line.hasOption(JMXClientCLIArgument.HELP.getName()))
		{
			returnValue = true;
		}

		return returnValue;
	}



	protected void printUsage(final Options options, final HelpFormatter formatter)
	{
		LOG.debug("Printing usage information");
		formatter.printHelp(getName(), HELP_HEADER, options, HELP_FOOTER, true);
	}


	/**
	 * Get the name from the properties
	 *
	 * @return
	 */
	private String getName()
	{
		return this.properties.getProperty(JMXClientProperties.CLIENT_NAME.getKey());
	}


}
