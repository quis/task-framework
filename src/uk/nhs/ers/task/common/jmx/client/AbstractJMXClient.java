/*
 * Client.java - JMX client that interacts with the JMX agent. It gets attributes and performs operations on the Hello
 * MBean and the QueueSampler MXBean example. It also listens for Hello MBean notifications.
 */

package uk.nhs.ers.task.common.jmx.client;


import java.io.IOException;
import java.util.Map;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.nhs.ers.task.common.service.ITaskService;

import com.google.common.base.Preconditions;


/**
 * Contains methods required for connecting and interacting with remote Task Services. Should be extended for each
 * individual Task Service
 *
 */
public abstract class AbstractJMXClient implements IJMXClient
{

	private static final Logger LOG = LoggerFactory.getLogger(AbstractJMXClient.class);
	private final ITaskService remoteService;
	private JMXConnector jmxConnector = null;
	private final MBeanServerConnection mBeanServerConnection;


	/**
	 * Creates a JMXClient for a remote Task Service at the specified URL. Provides a way of invoking methods on a
	 * currently running archiving service.
	 *
	 * @param mBeanServerURL the NMX Service URL
	 * @param mBeanObjectName String representation fo the remote object name
	 * @param serviceClass The class for the remote service.
	 * @throws IOException
	 * @throws MalformedObjectNameException
	 */
	public AbstractJMXClient(final String mBeanServerURL, final String mBeanObjectName,
			final Class<? extends ITaskService> serviceClass)
			throws IOException, MalformedObjectNameException
	{

		Preconditions.checkArgument(!mBeanServerURL.isEmpty(), "Server URL cannot be empty or null");
		Preconditions.checkArgument(!mBeanObjectName.isEmpty(), "Object Name cannot be empty or null");

		try
		{
			this.mBeanServerConnection = connectToMBeanServer(mBeanServerURL);

			this.remoteService = createMBeanProxy(mBeanObjectName, serviceClass);
		}
		catch (final IOException | MalformedObjectNameException e)
		{
			LOG.info("Unable to connect to the remote service");
			LOG.error("IOException occurred while connecting to  MBean Server", e);
			throw e;
		}
		LOG.info("Successfully connected to remote service: {}", this.remoteService.getName());
	}


	/**
	 * @return the remoteService
	 */
	@Override
	public ITaskService getRemoteService()
	{
		return this.remoteService;
	}


	/**
	 * Attempts to get an MBeanServerConnection to an MBeanServer at the specified URL.
	 *
	 * @param url the JMX Service url
	 *
	 */
	private MBeanServerConnection connectToMBeanServer(final String url) throws IOException
	{
		LOG.debug("Create an RMI connector client and connect it to the RMI connector server");
		MBeanServerConnection connection = null;
		try
		{
			final JMXServiceURL jmxServiceUrl = new JMXServiceURL(url);
			this.jmxConnector = JMXConnectorFactory.connect(jmxServiceUrl, null);
			LOG.debug("Getting an MBeanServerConnection");
			connection = this.jmxConnector.getMBeanServerConnection();
		}
		catch (final IOException ioe)
		{
			LOG.info("A communication problem occurred whilst connecting to the remote service");
			LOG.error("IOException thrown during connection to remote MBean", ioe);
			throw ioe;
		}
		return connection;
	}



	/**
	 * Creates a proxy for controlling a remote MBean
	 *
	 * @param objectName A string representation of the remote MBean object name
	 * @param interfaceClass Class the management interface that the MBean exports
	 * @returns a proxy local instance of the remote MBean
	 *
	 */
	private <T extends ITaskService> T createMBeanProxy(final String objectName, final Class<T> interfaceClass)
			throws MalformedObjectNameException
	{
		T returnObject = null;
		LOG.debug("Creating MBean Proxy");
		LOG.debug("Supplied ObjectName: {}", objectName);
		try
		{
			final ObjectName mbeanName = new ObjectName(objectName);
			returnObject = JMX.newMBeanProxy(this.mBeanServerConnection, mbeanName, interfaceClass, false);
		}
		catch (final MalformedObjectNameException mone)
		{
			LOG.error("The supplied Object Name does not have the right format", mone);
			throw mone;
		}
		return returnObject;
	}


	/**
	 * Gets the name of the remote service
	 *
	 * @return the remote service name.
	 */
	@Override
	public String getName()
	{
		LOG.info("Getting the name of the remote Service");
		return this.remoteService.getName();
	}


	/**
	 * Tells the remote service to stop.
	 */
	@Override
	public void stop()
	{
		LOG.info("Stopping the remote Service");
		this.remoteService.stop();
	}


	/**
	 * Tells the remote service to pause. This remembers the current rate and then sets it to 0.
	 */
	@Override
	public void pause()
	{
		LOG.info("Pausing the remote Service");
		this.remoteService.pause();

	}


	/**
	 * Tells the remote service to resume. This should return the service rate to its value before pausing.
	 */
	@Override
	public void resume()
	{
		LOG.info("Resuming the remote Service");
		this.remoteService.resume();
	}


	/**
	 * Attempts to close the connection to the remote MBean.
	 *
	 * @return true if connection was successfully closed, otherwise false.
	 */
	@Override
	public boolean closeConnection()
	{
		LOG.info("Closing the connection to the server");
		boolean wasSuccessful = true;
		try
		{
			this.jmxConnector.close();
		}
		catch (final IOException ioe)
		{
			LOG.info("Failed to cleanly disconnect from remote service");
			LOG.error("Failed to close the JMX Connector", ioe);
			wasSuccessful = false;
		}
		return wasSuccessful;
	}



	/**
	 * Tells the remote service to update the named property with the specified value.
	 *
	 * @param key - The property to be updated.
	 * @param value - The new value for the specified property.
	 * @return the previous value of the property, or -1 if it wasn't numeric.
	 */
	protected int setProperty(final String key, final int value)
	{
		LOG.debug("Invoking setProperty({}, {}) on remote Service", key, value);
		final String oldValue = this.remoteService.setProperty(key,
				String.valueOf(value));
		LOG.debug("setProperty Returned: {}", oldValue);
		int returnValue = -1;
		if (StringUtils.isNumeric(oldValue))
		{
			returnValue = Integer.parseInt(oldValue);
		}
		else
		{
			LOG.info("Previous value wasn't an integer ({}), returning {}", oldValue, returnValue);
		}
		return returnValue;
	}


	/**
	 * Gets the value of the specified property from the remote service.
	 *
	 * @param key - The property to be retrieved.
	 * @return the value of the property, or -1 if it wasn't numeric.
	 */
	protected int getProperty(final String key)
	{
		LOG.debug("Invoking getProperty({}) on remote Service", key);
		final String value = this.remoteService.getProperty(key);
		LOG.debug("getProperty Returned: {}", value);
		int returnValue = -1;
		if (StringUtils.isNumeric(value))
		{
			returnValue = Integer.parseInt(value);
		}
		else
		{
			LOG.info("Previous value wasn't an integer ({}), returning {}", value, returnValue);
		}
		return returnValue;
	}



	/**
	 * Gets the current configuration of a remote service.
	 *
	 * @returns a Map containing the key-value pairs of properties set for the remote service.
	 */
	@Override
	public Map<String, String> getCurrentConfiguration()
	{
		LOG.info("Getting Current Configuration of the remote Service");
		return this.remoteService.getAllProperties();
	}



	@Override
	public int setRate(final int threadCount)
	{
		
		final String property = getRemoteService().getProperty(getPropertyKey(JMXClientProperty.MAX_THREADS));
		final int maxThreadCount = Integer.valueOf(property);
		int previousThreadCount;
		if (threadCount <= maxThreadCount)
		{
			LOG.info("Setting the maximum rate to {} on the remote Service", threadCount);
			previousThreadCount =
					setProperty(getPropertyKey(JMXClientProperty.THREAD_POOL_SIZE), threadCount);
		}
		else
		{
			previousThreadCount = Integer
					.valueOf(getRemoteService()
							.getProperty(getPropertyKey(JMXClientProperty.THREAD_POOL_SIZE)));
			LOG.info("Cannot set the rate to above the Max Thread Count of [{}]", maxThreadCount);
		}
		return previousThreadCount;
	}


	@Override
	public String getRate()
	{
		LOG.info("Getting the maximum rate of the remote Service");
		return getRemoteService().getProperty(getPropertyKey(JMXClientProperty.THREAD_POOL_SIZE));
	}


	@Override
	public String getStatus()
	{
		LOG.info("Getting Status of the remote Service");
		LOG.debug("Invoking getProperty({}) on remote Service to determine status",
				getPropertyKey(JMXClientProperty.THREAD_POOL_SIZE));
		final boolean isPaused = getRemoteService().isPaused();
		String returnString = "";
		if (isPaused)
		{
			returnString = STATE_PAUSED;
		}
		else
		{
			final String currentRate = getRemoteService()
					.getProperty(getPropertyKey(JMXClientProperty.THREAD_POOL_SIZE));
			returnString = "Running with " + currentRate + " active threads";
		}
		return returnString;
	}


}
