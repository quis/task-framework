package uk.nhs.ers.task.common.jmx.factory;


import java.io.IOException;

import javax.management.MalformedObjectNameException;

import uk.nhs.ers.task.common.jmx.client.IJMXClient;


public interface IJMXClientFactory
{

	/**
	 * Create a new JMXClient
	 *
	 * @return
	 * @throws MalformedObjectNameException
	 * @throws IOException
	 */
	IJMXClient createClient() throws MalformedObjectNameException, IOException;

}
