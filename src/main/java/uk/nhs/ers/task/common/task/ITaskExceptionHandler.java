package uk.nhs.ers.task.common.task;


import uk.nhs.ers.task.common.exception.TaskException;


/**
 * {@link ITaskExceptionHandler} provides an interface for Exception handlers that the Task can use
 *
 */
public interface ITaskExceptionHandler
{
	/**
	 * Handle the given task exception
	 *
	 * @param exception exception to handle
	 */
	void handleException(TaskException exception);
}
