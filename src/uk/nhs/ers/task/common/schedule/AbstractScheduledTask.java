package uk.nhs.ers.task.common.schedule;


import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * {@link AbstractScheduledTask} provides basic logging for the scheduled tasks
 *
 */
public abstract class AbstractScheduledTask implements IScheduledTask
{
	private static final Logger LOG = LoggerFactory.getLogger(ScheduledTaskService.class);
	private String identifier;



	@Override
	public void run()
	{
		this.identifier = UUID.randomUUID().toString();

		LOG.info("Running {}", this);
		doTask();
		LOG.info("Running {}", this);
	}


	abstract protected void doTask();


	@Override
	public String toString()
	{
		return String.format("%s : [%s]", this.getClass().getSimpleName(), this.identifier);
	}
}
