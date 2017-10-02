package uk.nhs.ers.task.common.properties;


import java.beans.PropertyChangeEvent;

import org.testng.Assert;
import org.testng.annotations.Test;

import uk.nhs.ers.task.common.thread.IPoolSizeConfigurable;


public class PoolSizeChangedListenerTest
{
	@Test
	public void test()
	{
		final String serviceName = "TestService";
		final TestExecutor executor = new TestExecutor();
		final PoolSizeChangedListener listener = new PoolSizeChangedListener(serviceName, executor);
		final String newValue = "10";
		final PropertyChangeEvent event = new PropertyChangeEvent(this, "bob", "1", newValue);
		listener.propertyChange(event);
		Assert.assertEquals(executor.getNewPoolSize(), Integer.parseInt(newValue));
	}

	private static class TestExecutor implements IPoolSizeConfigurable
	{
		private int newSize = 0;


		@Override
		public void setPoolSize(final int size)
		{
			this.newSize = size;

		}


		public int getNewPoolSize()
		{
			return this.newSize;
		}
	}
}
