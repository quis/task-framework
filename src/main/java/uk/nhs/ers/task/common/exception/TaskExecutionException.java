package uk.nhs.ers.task.common.exception;

/**
 * Exception that wraps exceptions thrown from within tasks.
 *
 */
public class TaskExecutionException extends TaskException
{

	private static final long serialVersionUID = -2547483027247525914L;


	/**
	 * Construct a basic exception.
	 */
	public TaskExecutionException()
	{
		super();
	}


	/**
	 * Construct a task execution exception with the given message and cause.
	 *
	 * @param message message about the exception
	 * @param cause cause of the exception
	 */
	public TaskExecutionException(final String message, final Throwable cause)
	{
		super(message, cause);

	}


	/**
	 * Construct a task execution exception with the given message.
	 *
	 * @param message message about the exception
	 */
	public TaskExecutionException(final String message)
	{
		super(message);

	}


	/**
	 * Construct a task execution exception with the given cause.
	 *
	 * @param cause cause of the exception
	 */
	public TaskExecutionException(final Throwable cause)
	{
		super(cause);

	}

}
