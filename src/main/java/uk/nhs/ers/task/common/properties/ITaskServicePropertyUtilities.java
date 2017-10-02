package uk.nhs.ers.task.common.properties;



/**
 * {@link ITaskServicePropertyUtilities} provides an interface for Task Service Properties Utilities
 *
 */
public interface ITaskServicePropertyUtilities
{

	/**
	 * Get the value of a property ifrom a store
	 *
	 * @param property The property to get the value of
	 * @return the value associated with the specified property.
	 */
	String getPropertyValue(ITaskServiceProperties property);


	/**
	 * Set the value of a property in the store.
	 *
	 * @param property The property to update
	 * @param newValue The value the property should be updated to.
	 * @return the previous value associated with the specified property.
	 */
	String setPropertyValue(ITaskServiceProperties property, String newValue);


	/**
	 * Check it this is a TaskProperty
	 *
	 * @param key
	 * @return
	 */
	ITaskServiceProperties getTaskServicePropertyWithKey(String key);

}
