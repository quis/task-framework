package uk.nhs.ers.task.common.exception;

/**
 * Exceptions that are thrown during archiver task creation.
 *
 */
public class TaskException extends Exception
{

	/** serialVersionUID */
	private static final long serialVersionUID = 5210646219579470270L;


	/**
	 * Parameterless constructor
	 */
	public TaskException()
	{
		super();
	}


	/**
	 * Construct an exception with the specified message
	 *
	 * @param message - The message associated with this exception
	 */
	public TaskException(final String message)
	{
		super(message);
	}


	/**
	 * Construct an exception with the specified message and cause
	 *
	 * @param message - The message associated with this exception
	 * @param cause - The cause of this exception
	 */
	public TaskException(final String message, final Throwable cause)
	{
		super(message, cause);
	}


	/**
	 * Constructor taking a Throwable as a cause
	 *
	 * @param cause - the cause
	 */
	public TaskException(final Throwable cause)
	{
		super(cause);
	}
}
