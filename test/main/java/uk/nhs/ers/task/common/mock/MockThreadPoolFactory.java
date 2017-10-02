package uk.nhs.ers.task.common.mock;



import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import uk.nhs.ers.task.common.factory.IPauseableThreadPoolFactory;
import uk.nhs.ers.task.common.thread.PausableThreadPoolExecutor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;


public class MockThreadPoolFactory implements IPauseableThreadPoolFactory
{
	private int queueCapacity = 1;
	private int poolSize = 2;
	private int keepAlive = 30;


	public MockThreadPoolFactory(final int queueCapacity, final int poolSize, final int keepAlive)
	{
		this.queueCapacity = queueCapacity;
		this.poolSize = poolSize;
		this.keepAlive = keepAlive;
	}


	@Override
	public PausableThreadPoolExecutor getThreadPool()
	{
		final BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(
				this.queueCapacity);

		final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("TaskPool-Thread-%d").build();

		final PausableThreadPoolExecutor executor = new PausableThreadPoolExecutor(
				this.poolSize, this.poolSize, this.keepAlive, TimeUnit.MINUTES, workQueue, threadFactory);
		return executor;
	}


	/**
	 * Returns the value for the field {@link queueCapacity}
	 */
	public int getQueueCapacity()
	{
		return this.queueCapacity;
	}


	/**
	 * Sets the value for the field {@link queueCapacity}
	 */
	public void setQueueCapacity(final int queueCapacity)
	{
		this.queueCapacity = queueCapacity;
	}


	/**
	 * Returns the value for the field {@link poolSize}
	 */
	public int getPoolSize()
	{
		return this.poolSize;
	}


	/**
	 * Sets the value for the field {@link poolSize}
	 */
	public void setPoolSize(final int poolSize)
	{
		this.poolSize = poolSize;
	}


	/**
	 * Returns the value for the field {@link keepAlive}
	 */
	public int getKeepAlive()
	{
		return this.keepAlive;
	}


	/**
	 * Sets the value for the field {@link keepAlive}
	 */
	public void setKeepAlive(final int keepAlive)
	{
		this.keepAlive = keepAlive;
	}
}
