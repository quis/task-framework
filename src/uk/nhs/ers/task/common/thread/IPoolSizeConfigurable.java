package uk.nhs.ers.task.common.thread;

/**
 * Defines an entity with a configurable pool size.
 */
public interface IPoolSizeConfigurable
{
	/**
	 * Set the pool size.
	 *
	 * @param size size of the pool
	 */
	void setPoolSize(int size);
}
