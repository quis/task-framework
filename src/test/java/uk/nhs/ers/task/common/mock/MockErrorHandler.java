package uk.nhs.ers.task.common.mock;


import uk.nhs.ers.task.common.exception.TaskException;
import uk.nhs.ers.task.common.task.ITaskExceptionHandler;


public class MockErrorHandler implements ITaskExceptionHandler
{
	private boolean exceptionHandled = false;


	@Override
	public void handleException(final TaskException exception)
	{
		setExceptionHandled(true);
	}


	/**
	 * Returns the value for the field {@link exceptionHandled}
	 */
	public boolean isExceptionHandled()
	{
		return this.exceptionHandled;
	}


	/**
	 * Sets the value for the field {@link exceptionHandled}
	 */
	public void setExceptionHandled(final boolean exceptionHandled)
	{
		this.exceptionHandled = exceptionHandled;
	}
}
