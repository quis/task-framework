package uk.nhs.ers.task.common.properties;


import java.util.Properties;

import org.apache.commons.lang3.StringUtils;


/**
 * Helper class for obtaining properties in a type safe manner.
 *
 */
public final class PropertiesHelper
{
	private PropertiesHelper()
	{
		
	}


	/**
	 * Obtain the property from the set of properties, falling back on the default if not in the defined properties.
	 *
	 * @param properties the set of all properties
	 * @param taskProperties the required property
	 * @return the string value of the property
	 */
	public static String getStringProperty(final Properties properties,
			final ITaskServiceProperties taskProperties)
	{
		final String propertyValue = properties.getProperty(taskProperties
				.getKey());

		return StringUtils.isEmpty(propertyValue) ? taskProperties
				.getDefaultValue() : propertyValue;
	}


	/**
	 * Obtain the property as an integer from the set of properties, falling back on the default if not in the defined
	 * properties.
	 *
	 * @param properties the set of all properties
	 * @param taskProperties the required property
	 * @return the integer value of the property
	 */
	public static int getIntegerProperty(final Properties properties,
			final ITaskServiceProperties taskProperties)
	{
		final String stringValue = getStringProperty(properties,
				taskProperties);

		return Integer.valueOf(stringValue);

	}
}
