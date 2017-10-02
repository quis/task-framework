package uk.nhs.ers.task.common.jmx.client;


import java.util.Map;

import uk.nhs.ers.task.common.service.ITaskService;


public interface IJMXClient
{

	String STATE_PAUSED = "Paused";
	String STATE_RUNNING = "Running";


	/**
	 * @return the remoteService
	 */
	ITaskService getRemoteService();


	/**
	 * Gets the name of the remote service
	 *
	 * @return the remote service name.
	 */
	String getName();


	/**
	 * Tells the remote service to stop.
	 */
	void stop();


	/**
	 * Tells the remote service to pause. This remembers the current rate and then sets it to 0.
	 */
	void pause();


	/**
	 * Tells the remote service to resume. This should return the service rate to its value before pausing.
	 */
	void resume();


	/**
	 * Attempts to close the connection to the remote MBean.
	 *
	 * @return true if connection was successfully closed, otherwise false.
	 */
	boolean closeConnection();


	/**
	 * Gets the current status of a remote service.
	 *
	 * @returns the remote task service status.
	 */
	String getStatus();


	/**
	 * Gets the current configuration of a remote service.
	 *
	 * @returns a Map containing the key-value pairs of properties set for the remote service.
	 */
	Map<String, String> getCurrentConfiguration();


	/**
	 * Sets the rate of the remote service to the number of threads specified.
	 *
	 * @param threadCount - The number of threads the remote service should have in its pool
	 * @return
	 */
	int setRate(int threadCount);


	/**
	 * Gets the rate of the remote service.
	 *
	 * @returns The number of threads in the remote services pool
	 */
	String getRate();


	/**
	 * Get the property key for the given property
	 *
	 * @param property JMXClientProperty
	 *
	 * @return The property key
	 */
	String getPropertyKey(JMXClientProperty property);
}
