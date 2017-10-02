package uk.nhs.ers.task.common.factory;


import uk.nhs.ers.task.common.thread.PausableThreadPoolExecutor;


/**
 * Factory for obtaining pausable thread pools.
 *
 */
public interface IPauseableThreadPoolFactory
{
	/**
	 * Obtain a pausable thread pool.
	 *
	 * @return a pausable thread pool
	 */
	PausableThreadPoolExecutor getThreadPool();
}
