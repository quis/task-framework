package uk.nhs.ers.task.common.jmx.cli;

/**
 * Custom exception class for distinguishing between user input IllegalArgumentExceptions and others.
 *
 */
public class JMXClientCLIArgumentException extends IllegalArgumentException
{
	/** serialVersionUID */
	private static final long serialVersionUID = -8532685906610953858L;


	/**
	 * Constructs a JMXClientCLIArgumentException with the supplied message
	 *
	 * @param message - The description accompanying this exception
	 */
	public JMXClientCLIArgumentException(final String message)
	{
		super(message);
	}
}
