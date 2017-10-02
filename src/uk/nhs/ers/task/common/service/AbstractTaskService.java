package uk.nhs.ers.task.common.service;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import uk.nhs.ers.task.common.factory.IPauseableThreadPoolFactory;
import uk.nhs.ers.task.common.factory.IPoolSizeChangeListenerFactory;
import uk.nhs.ers.task.common.properties.ITaskServiceProperties;
import uk.nhs.ers.task.common.properties.ITaskServicePropertyUtilities;
import uk.nhs.ers.task.common.schedule.ScheduledTaskService;
import uk.nhs.ers.task.common.thread.PausableThreadPoolExecutor;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;


/**
 * {@link AbstractTaskService} provides the basic implementation of what a Task Service does.
 *
 */
@ManagedResource()
@SuppressWarnings("PMD.TooManyMethods") 
public abstract class AbstractTaskService implements ITaskService
{
	private static final Logger LOG = LoggerFactory.getLogger(AbstractTaskService.class);
	private static final int SERVICE_STOP_WAIT_TIME = 60;


	private final ConcurrentMap<ServiceEventType, Collection<IServiceEventHandler>> eventHandlerMap =
			new ConcurrentHashMap<>();
	private final PausableThreadPoolExecutor threadPool;
	private final String serviceName;
	private final PropertyChangeSupport propertyChangeSupport;
	private final IPoolSizeChangeListenerFactory poolChangeListenerFactory;
	private final ThreadPoolTaskScheduler scheduleExecutor;
	private final ScheduledTaskService scheduledTaskService;
	private ITaskServicePropertyUtilities taskServicePropertyUtilities;

	private Properties properties;


	/**
	 * @param serviceName
	 * @param threadPoolFactory
	 * @param poolChangeListenerFactory
	 * @param scheduleExecutor
	 * @param scheduledTaskService
	 */
	public AbstractTaskService(final String serviceName,
			final IPauseableThreadPoolFactory threadPoolFactory,
			final IPoolSizeChangeListenerFactory poolChangeListenerFactory,
			final ThreadPoolTaskScheduler scheduleExecutor,
			final ScheduledTaskService scheduledTaskService)
	{
		this.propertyChangeSupport = new PropertyChangeSupport(this);
		this.serviceName = serviceName;
		this.threadPool = threadPoolFactory.getThreadPool();
		this.poolChangeListenerFactory = poolChangeListenerFactory;
		this.scheduleExecutor = scheduleExecutor;
		this.scheduledTaskService = scheduledTaskService;

		initHandlerMap();
		registerPoolSizeChangeListener();
	}


	@Override
	@ManagedAttribute
	public final String getName()
	{
		return this.serviceName;
	}


	@Override
	public void start()
	{
		LOG.info("Service start requested. Service name={}", getName());

		
		doStart();

		
		if (!this.threadPool.isPaused())
		{
			this.scheduledTaskService.start();
		}

		notifyEventHandlers(ServiceEventType.STARTED);

		LOG.info("Service started. Service name={}", getName());
	}


	@ManagedOperation
	@Override
	public void stop()
	{
		LOG.info("Service stop requested. Service name={}", getName());

		
		doStop();

		
		this.scheduledTaskService.stop();

		
		this.scheduleExecutor.shutdown();

		
		final List<Runnable> skippedTasks = this.threadPool.shutdownNow();

		if (!skippedTasks.isEmpty() && LOG.isDebugEnabled())
		{
			LOG.debug("Service stop skipped tasks, count={}, tasks={}",
					skippedTasks.size(), skippedTasks);
		}

		final boolean terminatedOk = waitForShutdown(getServiceStopWaitTime());

		notifyEventHandlers(ServiceEventType.STOPPED);

		LOG.info("Service stopped. Service name={} orderly={}", getName(),
				terminatedOk);
	}



	@ManagedOperation
	@Override
	public void pause()
	{
		LOG.info("Request received to pause service name={}", getName());

		
		this.threadPool.pause();

		
		this.scheduledTaskService.stop();

		notifyEventHandlers(ServiceEventType.PAUSED);

		LOG.info("Service paused name={}", getName());
	}



	@ManagedAttribute
	@Override
	public boolean isPaused()
	{
		LOG.info("Request received to check if paused, service name={}", getName());

		return this.threadPool.isPaused();
	}



	@ManagedOperation
	@Override
	public void resume()
	{
		LOG.info("Request received to resume service name={}", getName());

		
		this.threadPool.resume();
		
		this.scheduledTaskService.start();

		notifyEventHandlers(ServiceEventType.RESUMED);

		LOG.info("Service resumed name={}", getName());
	}



	/**
	 * Hook for stopping a service. Override for service specific implementations.
	 */
	protected void doStop()
	{
		LOG.trace("Base implementation of service stop. Override as necessary");
	}



	@ManagedOperation
	@Override
	public String setProperty(final String key, final String newValue)
	{
		Preconditions.checkNotNull(key, "Unable to set a null key");

		String previousValue = null;

		if (null == this.properties)
		{
			LOG.warn(
					"Attempt to set properties on service that does not have any properties. Service name={}",
					getName());
		}
		else
		{
			
			final ITaskServiceProperties storedProperty =
					this.taskServicePropertyUtilities.getTaskServicePropertyWithKey(key);

			
			if ((null == newValue) && (storedProperty == null))
			{
				LOG.debug("Removing property [{}] from service name={}", key, getName());
				previousValue = (String)this.properties.remove(key);
			}
			
			else if (storedProperty != null)
			{
				LOG.debug("Service name={} updating database property ({}) to ({})", getName(), key, newValue);
				previousValue = this.taskServicePropertyUtilities.setPropertyValue(storedProperty, newValue);
			}
			
			else
			{
				LOG.debug("Service name={} updating java property ({}) to ({})", getName(), key, newValue);
				previousValue = (String)getProperties().setProperty(key, newValue);
			}

			LOG.debug(
					"Service {} property changed. Propery name={} oldValue={} newValue={}",
					getName(), key, previousValue, newValue);

			final PropertyChangeEvent event = new PropertyChangeEvent(this,
					key, previousValue, newValue);
			this.propertyChangeSupport.firePropertyChange(event);
		}

		return previousValue;
	}



	@Override
	public void deregisterEventHandler(final ServiceEventType eventType,
			final IServiceEventHandler eventHandler)
	{
		this.eventHandlerMap.get(eventType).remove(eventHandler);
	}


	@Override
	@ManagedOperation
	public String getProperty(final String key)
	{
		LOG.debug("Request to getProperty({})", key);
		String value = null;
		final ITaskServiceProperties property = this.taskServicePropertyUtilities.getTaskServicePropertyWithKey(key);
		if (property != null)
		{
			value = this.taskServicePropertyUtilities.getPropertyValue(property);
			LOG.debug("Service name={} property retrieved from store. Property name={} Value={}", getName(), key,
					value);
		}
		else
		{
			value = this.properties.getProperty(key);
			LOG.debug("Service name={} property retrieved from java Properties object. Property name={} Value={}",
					getName(), key, value);
		}
		return value;
	}


	/**
	 * Get the properies object
	 *
	 * @return properties
	 */
	public Properties getProperties()
	{
		return this.properties;
	}



	@Override
	@ManagedAttribute
	public Map<String, String> getAllProperties()
	{
		return Maps.fromProperties(this.properties);
	}



	@Override
	public void registerEventHandler(final ServiceEventType eventType,
			final IServiceEventHandler eventHandler)
	{
		this.eventHandlerMap.get(eventType).add(eventHandler);
	}



	/**
	 * Initialise the handler may for event types.
	 */
	private void initHandlerMap()
	{
		for (final ServiceEventType eventType : ServiceEventType.values())
		{
			this.eventHandlerMap
					.put(eventType,
							Collections
									.synchronizedCollection(new ArrayList<IServiceEventHandler>()));
		}
	}


	/**
	 * Register a listener for pool size change events.
	 */
	private final void registerPoolSizeChangeListener()
	{
		final String propertyName = getPoolSizePropertyName();

		if (null == propertyName)
		{
			LOG.info(
					"Service name={} does not have a configurable thread pool",
					getName());
		}
		else
		{
			registerPropertyChangeListener(propertyName,
					this.poolChangeListenerFactory
							.newListener(getName(), this.threadPool));
		}
	}


	/**
	 * get the pool size property name
	 *
	 * @return poolSizePropertyName
	 */
	protected abstract String getPoolSizePropertyName();


	/**
	 * Register the given listener for property changes.
	 *
	 * @param propertyName property name
	 * @param listener property change listener.
	 */
	protected final void registerPropertyChangeListener(final String propertyName,
			final PropertyChangeListener listener)
	{
		this.propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}


	/**
	 * Register the given listener for property changes.
	 *
	 * @param listener property change listener.
	 */
	public final void registerPropertyChangeListener(
			final PropertyChangeListener listener)
	{
		this.propertyChangeSupport.addPropertyChangeListener(listener);
	}


	/**
	 * Notify registered event handlers interested in the given event type.
	 *
	 * @param eventType type of event
	 */
	private void notifyEventHandlers(final ServiceEventType eventType)
	{
		final ServiceEvent event = new ServiceEvent(getName(), eventType);

		for (final IServiceEventHandler eventHandler : this.eventHandlerMap
				.get(eventType))
		{
			eventHandler.handleServiceEvent(event);
		}

	}


	/**
	 * Obtain the service stop time in seconds.
	 *
	 * @return service stop time in seconds
	 */
	protected int getServiceStopWaitTime()
	{
		return SERVICE_STOP_WAIT_TIME;
	}



	/**
	 * Wait for the executor service to shutdown.
	 *
	 * @param waitTime period in seconds to wait for the executor to shutdown
	 * @return true if the executor shutdown within the given time period (seconds).
	 */
	private boolean waitForShutdown(final int waitTime)
	{
		boolean terminatedOK = false;
		try
		{
			terminatedOK = this.threadPool
					.awaitTermination(waitTime, TimeUnit.SECONDS);

		}
		catch (final InterruptedException e)
		{
			LOG.warn("Service stop interrupted. Service name={}", getName());

			
			Thread.currentThread().interrupt();
		}
		return terminatedOK;
	}



	/**
	 * Set the properties of this service.
	 *
	 * @param properties properties for the service
	 */
	public void setProperties(final Properties properties)
	{
		this.properties = properties;
	}


	/**
	 * Set the Database property utilities of this service.
	 *
	 * @param taskPropertyUtilities the databasePropertyUtilities for the service
	 */
	public void setTaskPropertyUtilities(final ITaskServicePropertyUtilities taskPropertyUtilities)
	{
		this.taskServicePropertyUtilities = taskPropertyUtilities;
	}


	/**
	 * Get the Database property utilities of this service.
	 *
	 * @return the databasePropertyUtilities for the service
	 */
	public ITaskServicePropertyUtilities getTaskPropertyUtilities()
	{
		return this.taskServicePropertyUtilities;
	}



	/**
	 * Obtain the executor for the service.
	 *
	 * @return the executor.
	 */
	protected final PausableThreadPoolExecutor getExecutor()
	{
		return this.threadPool;
	}


	/**
	 * Obtain the task scheduler.
	 *
	 * @return task scheduler
	 */
	protected final ThreadPoolTaskScheduler getScheduler()
	{
		return this.scheduleExecutor;
	}

}
