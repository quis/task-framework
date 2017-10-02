package uk.nhs.ers.task.common.thread;

/**
 * Defines a pause/resume paradigm.
 */
public interface IPausable
{
	/**
	 * Pause the entity.
	 */
	void pause();


	/**
	 * Resume the entity.
	 */
	void resume();


	/**
	 * Determine if the pausable is currently paused.
	 *
	 * @return true if paused, false otherwise
	 */
	boolean isPaused();
}
