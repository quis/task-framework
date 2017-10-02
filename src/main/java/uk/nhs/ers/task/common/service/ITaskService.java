package uk.nhs.ers.task.common.service;


import java.util.Map;



/**
 * Interface that defines services within the Archiver application.
 *
 */
public interface ITaskService
{
	/**
	 * Obtain the name of the service.
	 *
	 * @return the name of the service
	 */
	String getName();


	/**
	 * Start the service.
	 */
	void start();


	/**
	 * Stop the service.
	 */
	void stop();


	/**
	 * Pause the service.
	 */
	void pause();


	/**
	 * Checks to see if the service is paused.
	 *
	 * @returns true if the service is paused.
	 */
	boolean isPaused();


	/**
	 * Resume a paused service.
	 */
	void resume();


	/**
	 * Sets the given key and value for the service. Implementations may store certain properties in a Properties
	 * objects and others seperately, e.g. in an on disk store.
	 *
	 * @param key property key
	 * @param newValue property value
	 * @return the previous value associated with the key, may be null
	 */
	String setProperty(String key, String newValue);


	/**
	 * Gets the value for the given key in the services properties.
	 *
	 * @param key property key
	 * @return the value associated with the key, may be null
	 */
	String getProperty(String key);


	/**
	 * Gets the values for all keys in the services properties.
	 *
	 * @return a Map of the key value pairs in the services properties, property values may be null.
	 */
	Map<String, String> getAllProperties();


	/**
	 * Register an event handler on the service for the given type of event.
	 *
	 * @param eventType type of event
	 * @param eventHandler the event handler
	 */
	void registerEventHandler(ServiceEventType eventType,
			IServiceEventHandler eventHandler);


	/**
	 * De-register the event handler on the service for the given type of event.
	 *
	 * @param eventType type of event
	 * @param eventHandler the event handler
	 */
	void deregisterEventHandler(ServiceEventType eventType,
			IServiceEventHandler eventHandler);


	/**
	 *
	 */
	void doStart();



}
