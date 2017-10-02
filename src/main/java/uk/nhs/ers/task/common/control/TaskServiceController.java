package uk.nhs.ers.task.common.control;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.nhs.ers.task.common.service.ITaskService;


/**
 * Controller for task services.
 */
public class TaskServiceController implements IController
{
	private static final Logger LOG = LoggerFactory.getLogger(TaskServiceController.class);

	private final String controllerName;
	private final ITaskService service;


	/**
	 * Construct a controller to manage the given service.
	 *
	 * @param service the service the controller will manage
	 * @param controllerName name of the controller
	 */
	public TaskServiceController(final ITaskService service,
			final String controllerName)
	{
		this.service = service;
		this.controllerName = controllerName;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see uk.nhs.ers.archiver.controller.IController#start()
	 */
	@Override
	public void start()
	{
		LOG.info("Starting controller name={}", getControllerName());

		getService().start();

		LOG.info("Started controller name={}", getControllerName());
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see uk.nhs.ers.archiver.controller.IController#stop()
	 */
	@Override
	public void stop()
	{
		LOG.info("Stopping controller. name={}", getControllerName());

		getService().stop();

		LOG.info("Stopped controller. name={}", getControllerName());
	}


	/**
	 * Obtain the service under control.
	 *
	 * @return the service
	 */
	private ITaskService getService()
	{
		return this.service;
	}


	/**
	 * Obtain the name of the controller.
	 *
	 * @return the controllerName
	 */
	private String getControllerName()
	{
		return this.controllerName;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see uk.nhs.ers.task.controller.IController#pause()
	 */
	@Override
	public void pause()
	{
		LOG.info("Pausing controller. name={}", getControllerName());

		getService().pause();

		LOG.info("Paused controller. name={}", getControllerName());

	}

}
