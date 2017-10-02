package uk.nhs.ers.task.common.factory;


import java.beans.PropertyChangeListener;

import uk.nhs.ers.task.common.properties.PoolSizeChangedListener;
import uk.nhs.ers.task.common.thread.IPoolSizeConfigurable;



/**
 * Factory for creating pool size change listeners.
 *
 */
public class PoolSizeChangeListenerFactory implements
		IPoolSizeChangeListenerFactory
{
	/*
	 * (non-Javadoc)
	 *
	 * @see uk.nhs.ers.archiver.factory.IPoolSizeChangeListenerFactory#newListener (java.lang.String,
	 * uk.nhs.ers.task.thread.PausableThreadPoolExecutor)
	 */
	@Override
	public PropertyChangeListener newListener(final String serviceName,
			final IPoolSizeConfigurable executor)
	{
		return new PoolSizeChangedListener(serviceName, executor);
	}

}
