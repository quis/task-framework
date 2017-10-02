package uk.nhs.ers.task.common.mock;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.nhs.ers.task.common.schedule.AbstractScheduledTask;


/**
 * {@link MockScheduledTask} provides an implementation of the scheduledtask for testing.
 *
 */
public class MockScheduledTask extends AbstractScheduledTask
{
	private static final Logger LOG = LoggerFactory.getLogger(MockScheduledTask.class);
	private String identifier;



	@Override
	protected void doTask()
	{
		LOG.debug("MockScheduledTask has RUN!");

	}


	/**
	 * Returns the value for the field {@link identifier}
	 *
	 * @return identifier
	 */
	public String getIdentifier()
	{
		return this.identifier;
	}


	/**
	 * Sets the value for the field {@link identifier}
	 *
	 * @param identifier
	 */
	public void setIdentifier(final String identifier)
	{
		this.identifier = identifier;
	}

}
