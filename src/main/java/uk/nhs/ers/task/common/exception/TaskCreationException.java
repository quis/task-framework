package uk.nhs.ers.task.common.exception;

/**
 * Exceptions that are thrown during archiver task creation.
 *
 */
public class TaskCreationException extends TaskException
{
	/** serialVersionUID */
	private static final long serialVersionUID = 5732885401623130659L;


	/**
	 * Construct an exception with the specified message
	 *
	 * @param message - The message associated with this exception
	 */
	public TaskCreationException(final String message)
	{
		super(message);
	}


	/**
	 * Construct an exception with the specified message and cause
	 *
	 * @param message - The message associated with this exception
	 * @param cause - The cause of this exception
	 */
	public TaskCreationException(final String message, final Throwable cause)
	{
		super(message, cause);
	}


	/**
	 * Constructor taking a Throwable as a cause
	 *
	 * @param cause - the cause
	 */
	public TaskCreationException(final Throwable cause)
	{
		super(cause);
	}
}
