package uk.nhs.ers.task.common.helper;

/**
 * {@link AppHelper} provides helper methods for TaskApp operations e.g. argument parsing
 *
 */
public final class AppHelper
{
	/** UNPAUSED */
	public static final String UNPAUSED = "--unpaused";


	private AppHelper()
	{}


	/**
	 * Very rudimentary argument parser. Expects "--unpaused" or nothing
	 *
	 * @param args
	 * @return
	 */
	public static boolean startPaused(final String[] args)
	{
		boolean paused = true;
		
		if ((args != null) && (args.length > 0))
		{
			if ((args.length == 1) && UNPAUSED.equals(args[0]))
			{
				paused = false;
			}
			else
			{
				throw new IllegalArgumentException(String.format("Expected only the '%s' argument", UNPAUSED));
			}
		}

		return paused;
	}


}
