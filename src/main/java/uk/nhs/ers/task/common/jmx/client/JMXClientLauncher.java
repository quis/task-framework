package uk.nhs.ers.task.common.jmx.client;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.nhs.ers.task.common.jmx.cli.JMXClientCLI;


/**
 * Class containing entry method to the JMX Client. Launches the Client with the supplied arguments.
 *
 */
public class JMXClientLauncher
{

	private static final String APPLICATION_CONTEXT = "/context/service/JMXClientCLIContext.xml";
	private ApplicationContext applicationContext;


	/**
	 * Launch the JMXClient
	 *
	 * @param args - passed through from main
	 */
	public void launch(final String[] args)
	{
		this.applicationContext = createApplicationContext(getApplicationContextLocation());
		final JMXClientCLI cli = getJMXClientCLI();
		cli.parseArguments(args);
	}


	/**
	 * get the application context location
	 *
	 * @return
	 */
	protected String[] getApplicationContextLocation()
	{
		final String[] context = new String[] { APPLICATION_CONTEXT };

		return context;
	}


	/**
	 * Create the application context from the given location.
	 *
	 * @param applicationContextLocation application context location
	 * @return the application context
	 */
	private ApplicationContext createApplicationContext(
			final String[] applicationContextLocation)
	{
		return new ClassPathXmlApplicationContext(applicationContextLocation);
	}


	/**
	 * Get the JMXClientCLI Bean from the context
	 *
	 * @return
	 */
	protected JMXClientCLI getJMXClientCLI()
	{
		return (JMXClientCLI)getApplicationContext().getBean(JMXClientCLI.class);
	}



	/**
	 * Get the application context
	 *
	 * @return
	 */
	protected ApplicationContext getApplicationContext()
	{
		return this.applicationContext;
	}


}
