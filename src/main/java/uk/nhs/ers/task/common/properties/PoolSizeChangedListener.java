package uk.nhs.ers.task.common.properties;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.nhs.ers.task.common.thread.IPoolSizeConfigurable;



/**
 * Listener for changes to core pool size.
 *
 */
public class PoolSizeChangedListener implements PropertyChangeListener
{
	private static final Logger LOG = LoggerFactory
			.getLogger(PoolSizeChangedListener.class);

	private final String serviceName;
	private final IPoolSizeConfigurable executor;


	/**
	 * Construct a listener for the given service and executor.
	 *
	 * @param serviceName service name
	 * @param executor executor
	 */
	public PoolSizeChangedListener(final String serviceName,
			final IPoolSizeConfigurable executor)
	{
		this.executor = executor;
		this.serviceName = serviceName;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans. PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(final PropertyChangeEvent evt)
	{
		final int newPoolSize = getNewPoolSize(evt);
		LOG.info("Pool resize event received for service {}, new size is {}",
				this.serviceName, newPoolSize);
		this.executor.setPoolSize(getNewPoolSize(evt));

	}


	/**
	 * Determine the new pool size from the given event.
	 *
	 * @param event change event
	 * @return new pool size
	 */
	private int getNewPoolSize(final PropertyChangeEvent event)
	{
		final String poolSizeString = (String)event.getNewValue();

		return Integer.parseInt(poolSizeString);
	}

}
