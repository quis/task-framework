package uk.nhs.ers.task.common.factory;


import java.beans.PropertyChangeListener;

import uk.nhs.ers.task.common.thread.IPoolSizeConfigurable;



/**
 * {@link IPoolSizeChangeListenerFactory} provides an interface for Pool Size Change Listener factory implentations
 *
 */
public interface IPoolSizeChangeListenerFactory
{
	/**
	 * Factory method for providing listeners for the given service and executor.
	 *
	 * @param serviceName name of the service
	 * @param executor configurable executor
	 * @return listener
	 */
	PropertyChangeListener newListener(String serviceName,
			IPoolSizeConfigurable executor);
}
