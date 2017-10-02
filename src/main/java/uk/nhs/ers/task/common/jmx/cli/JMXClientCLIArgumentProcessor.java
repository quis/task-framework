package uk.nhs.ers.task.common.jmx.cli;


import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.cli.Option;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.nhs.ers.task.common.jmx.client.IJMXClient;
import uk.nhs.ers.task.common.jmx.properties.JMXClientProperties;

import com.google.common.util.concurrent.Uninterruptibles;


/**
 * Processes individual arguments supplied by the user. Invokes the desired operation on the supplied client.
 *
 */
public class JMXClientCLIArgumentProcessor
{
	private static final TimeUnit SHORT_SLEEP_UNIT = TimeUnit.MILLISECONDS;
	private final IJMXClient client;
	private static final Logger LOG = LoggerFactory.getLogger(JMXClientCLIArgumentProcessor.class);
	private final Properties properties;


	/**
	 * Creates an argument processor that invokes methods on the supplied JMXClient.
	 *
	 * @param client - The client to interact with when processing arguments
	 * @param properties - The properties object to use for configurable values
	 */
	public JMXClientCLIArgumentProcessor(final IJMXClient client, final Properties properties)
	{
		this.properties = properties;
		this.client = checkNotNull(client);
	}


	private int getSleepLength()
	{
		String stringLength = this.properties.getProperty(JMXClientProperties.CLIENT_SLEEP_LENGTH.getKey());
		if (!StringUtils.isNumeric(stringLength))
		{
			stringLength = JMXClientProperties.CLIENT_SLEEP_LENGTH.getDefaultValue();
		}
		return Integer.valueOf(stringLength);
	}


	/**
	 * Processes a single CLI argument
	 *
	 * @param option - The option associated with the CLI argument, containing any required values.
	 */
	
	public void processArgument(final Option option) 
	{
		final JMXClientCLIArgument argument = JMXClientCLIArgument.getValueFromName(option.getOpt());

		try
		{
			switch (argument)
			{
			case PAUSE:
				pause();
				logStatus();
				break;
			case RESUME:
				resume();
				logStatus();
				break;
			case STOP:
				stop();
				break;
			case LISTCONFIG:
				listConfig(this.client.getCurrentConfiguration().entrySet());
				break;
			case STATUS:
				logStatus();
				break;
			case SETRATE:
				setRate(option.getValue());
				break;
			case GETRATE:
				getRate();
				break;
			case HELP:
				/* Intentional fall through */

			default:
				break;
			}
		}
		catch (final Exception ex)
		{
			
			
			LOG.info("An error occurred, please review the logs for more information");
			
			LOG.error(String.format("Operation failed [%s](%s) - %s", argument.getName(), option.getValue(),
					ex.getMessage()), ex);

		}

	}
	

	private void stop()
	{
		this.client.stop();
		shortSleep();
	}


	private void resume()
	{
		this.client.resume();
		shortSleep();
	}


	private void pause()
	{
		this.client.pause();
		shortSleep();
	}


	private void shortSleep()
	{
		Uninterruptibles.sleepUninterruptibly(getSleepLength(), SHORT_SLEEP_UNIT);
	}



	private void getRate()
	{
		LOG.info("Rate: " + this.client.getRate());
	}



	private void setRate(final String value)
	{

		int intValue = -1;
		if (StringUtils.isNumeric(value))
		{
			intValue = Integer.parseInt(value);
		}
		if (intValue > 0)
		{
			LOG.info("Previous rate: " + this.client.setRate(Integer.parseInt(value)));
			LOG.info("New rate: " + this.client.getRate());
		}
		else if (intValue == 0)
		{
			LOG.info("Unable to set Rate to 0, use the pause operation instead", value);
		}
		else
		{
			LOG.info("Unable to set Rate, invalid value supplied={}", value);
		}

	}



	private void listConfig(final Set<Entry<String, String>> entries)
	{
		for (final Entry<String, String> entry : entries)
		{
			LOG.info(entry.getKey() + " : " + entry.getValue());
		}
	}



	private void logStatus()
	{
		LOG.info(this.client.getStatus());
	}


	/**
	 * Processes all arguments in the supplied array
	 *
	 * @param options - The arguments to be processed.
	 */
	public void processArguments(final Option[] options)
	{
		for (final Option option : options)
		{
			processArgument(option);
		}
	}
}
