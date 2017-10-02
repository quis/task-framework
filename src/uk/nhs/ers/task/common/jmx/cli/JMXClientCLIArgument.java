package uk.nhs.ers.task.common.jmx.cli;

/**
 * Enum containing valid CLI user input arguments, their description and if they require an accompanying argument.
 *
 */
public enum JMXClientCLIArgument
{
	HELP("help", "Shows this usage information", false),
	PAUSE("pause", "Pause the remote service.", false),
	RESUME("resume", "Resume the remote service.", false),
	STOP("stop", "Stop the remote service.", false),
	LISTCONFIG("listconfig", "List the remote service current configuration.", false),
	STATUS("status", "Get the current status of the remote service.", false),
	SETRATE("setrate", "Set the rate of the remote service.", true),
	GETRATE("getrate", "Get the rate of the remote service.", false);

	private final String longName;
	private final String description;
	private final boolean hasArg;


	private JMXClientCLIArgument(final String longName, final String description, final boolean hasArg)
	{
		this.longName = longName;
		this.description = description;
		this.hasArg = hasArg;
	}


	/**
	 * Gets the name of this argument.
	 *
	 * @returns the argument name
	 */
	public String getName()
	{
		return this.longName;
	}


	/**
	 * Gets the description of this argument.
	 *
	 * @returns the description
	 */
	public String getDescription()
	{
		return this.description;
	}


	/**
	 * Checks if this argument requires an additional accompanying argument.
	 *
	 * @returns True if it does, otherwise false.
	 */
	public boolean hasArg()
	{
		return this.hasArg;
	}


	/**
	 * Gets the matching JMXClientCLIArgument for the specified name, or null if one doesn't exist.
	 *
	 * @param name - The name of the desired argument
	 * @returns The matching JMXClientCLIArgument if one exists, otherwise null.
	 */
	public static JMXClientCLIArgument getValueFromName(final String name)
	{
		JMXClientCLIArgument returnValue = null;
		for (final JMXClientCLIArgument value : values())
		{
			if (value.longName.equals(name))
			{
				returnValue = value;
				break;
			}
		}
		return returnValue;
	}
}
