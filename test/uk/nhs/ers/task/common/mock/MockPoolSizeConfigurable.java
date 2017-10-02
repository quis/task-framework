package uk.nhs.ers.task.common.mock;


import uk.nhs.ers.task.common.thread.IPoolSizeConfigurable;


public class MockPoolSizeConfigurable implements IPoolSizeConfigurable
{
	public static final int DEFAULT = -1;
	private int poolSize = DEFAULT;


	@Override
	public void setPoolSize(final int size)
	{
		this.poolSize = size;
	}


	public boolean hasChanged()
	{
		return this.poolSize != DEFAULT;
	}


	public int getPoolSize()
	{
		return this.poolSize;
	}
}
