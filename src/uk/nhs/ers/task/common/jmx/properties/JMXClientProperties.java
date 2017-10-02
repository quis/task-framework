package uk.nhs.ers.task.common.jmx.properties;


import java.util.Properties;

import uk.nhs.ers.task.common.properties.ITaskServiceProperties;


/**
 * Properties specific to the JMXClient. Includes default connection details for Archiver Services.
 */
public enum JMXClientProperties implements ITaskServiceProperties
{

	SERVICE_PORT("jmxclient.port", "9995"),
	SERVICE_SERVER("jmxclient.server", "localhost"),
	CLIENT_SLEEP_LENGTH("jmxclient.sleep.length", "250"),
	CLIENT_NAME("jmxclient.name", "Undefined");


	private final String key;
	private final String defaultValue;


	private JMXClientProperties(final String key, final String defaultValue)
	{
		this.key = key;
		this.defaultValue = defaultValue;

	}


	@Override
	public String getKey()
	{
		return this.key;
	}



	@Override
	public String getDefaultValue()
	{
		return this.defaultValue;
	}


	/**
	 * Convert these enumerations to properties.
	 *
	 * @return properties
	 */
	public static Properties toProperties()
	{
		final Properties props = new Properties();
		for (final JMXClientProperties taskProperty : values())
		{
			props.put(taskProperty.getKey(), taskProperty.getDefaultValue());
		}
		return props;
	}


}
