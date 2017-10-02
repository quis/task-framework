package uk.nhs.ers.task.common.jmx.factory;


import java.io.IOException;
import java.util.Properties;

import javax.management.MalformedObjectNameException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.nhs.ers.task.common.jmx.cli.JMXClientCLIArgumentException;
import uk.nhs.ers.task.common.jmx.client.IJMXClient;

import com.google.common.base.Preconditions;


/**
 * Factory for constructing JMXClients connected to the desired remote service
 */
public abstract class AbstractJMXClientFactory implements IJMXClientFactory
{

	private static final Logger LOG = LoggerFactory.getLogger(AbstractJMXClientFactory.class);
	private static final String URL_SUFFIX = "/jmxrmi";
	private static final String URL_PREFIX = "service:jmx:rmi:///jndi/rmi://";
	private final Properties properties;


	/**
	 * Creates a JMXClientFactory with the specified properties. The properties should include Server and Port of the
	 * remote service that can be connected to
	 *
	 * @param properties - The properties containing details of the remote service
	 */
	public AbstractJMXClientFactory(final Properties properties)
	{
		this.properties = Preconditions.checkNotNull(properties);
	}



	@Override
	public IJMXClient createClient() throws MalformedObjectNameException, IOException
	{


		final String server = this.properties.getProperty("jmxclient.server");
		final String port = this.properties.getProperty("jmxclient.port");
		if (StringUtils.isBlank(server) || StringUtils.isBlank(port))
		{
			throw new JMXClientCLIArgumentException("Cannot find server/port for service");
		}

		LOG.debug("Attempting to connect. MBean: {} server: {}, port: {}", getMBeanObjectName(), server,
				port);

		final IJMXClient client = createClient(createMBeanServerURL(server, port), getMBeanObjectName());


		return client;
	}


	/**
	 * get the specific implementation of IJMXClient
	 *
	 * @param mBeanServerUrl
	 * @param mBeanObjectName
	 * @return IJMXClient
	 * @throws IOException
	 * @throws MalformedObjectNameException
	 */
	protected abstract IJMXClient createClient(String mBeanServerUrl, String mBeanObjectName)
			throws MalformedObjectNameException, IOException;


	/**
	 * Get the MBean Object Name for this implementation
	 *
	 * @return String MBean Object Name
	 */
	protected abstract String getMBeanObjectName();



	/**
	 * form a jmx url from the server and port
	 *
	 * @param server
	 * @param port
	 * @return
	 */
	protected String createMBeanServerURL(final String server, final String port)
	{
		return URL_PREFIX + server + ":" + port + URL_SUFFIX;
	}
}
