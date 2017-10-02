package uk.nhs.ers.task.common.properties;

/**
 * Interface to the TaskService properties.
 */
public interface ITaskServiceProperties
{
	/**
	 * Obtain the key for the property.
	 *
	 * @return the key
	 */
	String getKey();


	/**
	 * Obtain the default value of the property. To be used where the property is not defined in the properties file.
	 *
	 * @return the default value of the property.
	 */
	String getDefaultValue();



}
