package uk.nhs.ers.task.common.app;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.nhs.ers.task.common.control.IController;


/**
 * Shutdown hook for bringing task applications to controlled stop.
 */
class ShutdownHook implements Runnable
{
	private static final Logger LOG = LoggerFactory
			.getLogger(ShutdownHook.class);
	private final IController controller;


	/**
	 * Construct a shutdown hook with the given controller.
	 *
	 * @param controller the controller
	 */
	ShutdownHook(final IController controller)
	{
		this.controller = controller;
	}


	@Override
	public void run()
	{

		LOG.warn("Shutdown request detected");
		this.controller.stop();
	}

}
