package uk.nhs.ers.task.common.app;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.nhs.ers.task.common.control.IController;
import uk.nhs.ers.task.common.control.TaskServiceController;
import uk.nhs.ers.task.common.helper.AppHelper;


/**
 * Abstract base application for Task applications.
 */
public abstract class AbstractTaskBaseApp
{
	private static final Logger LOG = LoggerFactory.getLogger(AbstractTaskBaseApp.class);



	/**
	 * Obtain the application context location for the application.
	 *
	 * @return application context location.
	 */
	protected abstract String[] getApplicationContextLocation();


	/**
	 * Run the application.
	 *
	 * @param args application arguments
	 * @throws AppArgumentException
	 */
	protected final void run(final String[] args)
	{
		final ApplicationContext applicationContext = createApplicationContext(getApplicationContextLocation());
		final IController controller = applicationContext.getBean(getControllerClass());


		final ShutdownHook shutdownHook = new ShutdownHook(controller);
		Runtime.getRuntime().addShutdownHook(new Thread(shutdownHook));

		LOG.debug(String.format("Running app with arguments [%s]", StringUtils.join(args, ",")));

		
		if (AppHelper.startPaused(args))
		{
			controller.pause();
		}
		controller.start();
	}



	/**
	 * Obtain the controller class.
	 *
	 * @return controller class type
	 */
	protected Class<TaskServiceController> getControllerClass()
	{
		return TaskServiceController.class;
	}


	/**
	 * Create the application context from the given location.
	 *
	 * @param applicationContextLocation application context location
	 * @return the application context
	 */
	protected ApplicationContext createApplicationContext(
			final String[] applicationContextLocation)
	{
		return new ClassPathXmlApplicationContext(applicationContextLocation);
	}
}
