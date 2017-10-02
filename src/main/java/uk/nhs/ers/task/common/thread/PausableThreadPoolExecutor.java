package uk.nhs.ers.task.common.thread;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jcip.annotations.GuardedBy;


/**
 * Pausable Thread Pool. Extension of {@link ThreadPoolExecutor} to allow the thread pool to be paused, that is the
 * scheduling of new tasks is prevented until the pool is resumed.
 *
 */
public class PausableThreadPoolExecutor extends ThreadPoolExecutor implements
		IPausable, IPoolSizeConfigurable
{
	private static final Logger LOG = LoggerFactory
			.getLogger(PausableThreadPoolExecutor.class);

	private final ReentrantLock pauseLock = new ReentrantLock();
	private final Condition unpaused = this.pauseLock.newCondition();

	@GuardedBy(value = "pauseLock")
	private boolean paused;


	/**
	 * Construct a pausable thread pool executor.
	 *
	 * @param corePoolSize the number of threads to keep in the pool, even if they are idle, unless
	 *            allowCoreThreadTimeOut is set
	 * @param maximumPoolSize the maximum number of threads to allow in the pool
	 * @param keepAliveTime when the number of threads is greater than the core, this is the maximum time that excess
	 *            idle threads will wait for new tasks before terminating.
	 * @param unit he time unit for the keepAliveTime argument
	 * @param workQueue the queue to use for holding tasks before they are executed. This queue will hold only the
	 *            Runnable tasks submitted by the execute method.
	 */
	public PausableThreadPoolExecutor(final int corePoolSize, final int maximumPoolSize,
			final long keepAliveTime, final TimeUnit unit, final BlockingQueue<Runnable> workQueue)
	{
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}


	/**
	 * Construct a pausable thread pool executor.
	 *
	 * @param corePoolSize the number of threads to keep in the pool, even if they are idle, unless
	 *            allowCoreThreadTimeOut is set
	 * @param maximumPoolSize the maximum number of threads to allow in the pool
	 * @param keepAliveTime when the number of threads is greater than the core, this is the maximum time that excess
	 *            idle threads will wait for new tasks before terminating.
	 * @param unit he time unit for the keepAliveTime argument
	 * @param workQueue the queue to use for holding tasks before they are executed. This queue will hold only the
	 *            Runnable tasks submitted by the execute method.
	 * @param threadFactory thread factory
	 */
	
	public PausableThreadPoolExecutor(final int corePoolSize, final int maximumPoolSize,
			final long keepAliveTime, final TimeUnit unit, final BlockingQueue<Runnable> workQueue,
			final ThreadFactory threadFactory)
	{
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
	}
	


	/*
	 * (non-Javadoc)
	 *
	 * @see uk.nhs.ers.archiver.thread.IPausable#pause()
	 */
	@Override
	public void pause()
	{
		LOG.debug("Thread pool pause requested");
		this.pauseLock.lock();
		try
		{
			this.paused = true;
			
			getQueue().clear();
			LOG.debug("Thread pool paused");
		}
		finally
		{
			this.pauseLock.unlock();
		}

	}


	@Override
	public void execute(final Runnable command)
	{
		this.pauseLock.lock();
		try
		{
			if (isPaused())
			{
				getRejectedExecutionHandler().rejectedExecution(command, this);
			}
			else
			{
				super.execute(command);
			}
		}
		finally
		{
			this.pauseLock.unlock();
		}
	};


	/*
	 * (non-Javadoc)
	 *
	 * @see uk.nhs.ers.archiver.thread.IPausable#resume()
	 */
	@Override
	public void resume()
	{
		LOG.debug("Thread pool resume requested");
		this.pauseLock.lock();
		try
		{
			this.paused = false;
			this.unpaused.signalAll();
			LOG.debug("Thread pool resumed");
		}
		finally
		{
			this.pauseLock.unlock();
		}
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see uk.nhs.ers.archiver.thread.IPoolSizeConfigurable#setPoolSize(int)
	 */
	@Override
	public void setPoolSize(final int size)
	{
		setCorePoolSize(size);
	}


	@Override
	public boolean isPaused()
	{
		return this.paused;
	}
}
