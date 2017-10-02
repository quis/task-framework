package uk.nhs.ers.task.common.factory;


import java.beans.PropertyChangeListener;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.Test;

import uk.nhs.ers.task.common.mock.MockPoolSizeConfigurable;


public class PoolSizeChangeListenerFactoryTest
{
	@Test
	public void testNewListener()
	{
		final String serviceName = UUID.randomUUID().toString();

		final MockPoolSizeConfigurable executor = new MockPoolSizeConfigurable();

		final PoolSizeChangeListenerFactory factory = new PoolSizeChangeListenerFactory();
		final PropertyChangeListener listener = factory.newListener(serviceName, executor);

		Assert.assertNotNull(listener);
	}
}
