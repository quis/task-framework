package uk.nhs.ers.task.common.schedule;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;


/**
 * {@link ScheduledTaskService} provides the management of scheduled tasks
 *
 */
public class ScheduledTaskService
{
	private static final Logger LOG = LoggerFactory.getLogger(ScheduledTaskService.class);
	private final ThreadPoolTaskScheduler scheduler;
	private final List<TaskSchedule> taskSchedules;
	private List<ScheduledFuture<?>> scheduledFutures = new ArrayList<ScheduledFuture<?>>();


	/**
	 * Construct a new ScheduledTaskService with a scheduler and a list of tasks to schedule
	 * 
	 * @param scheduler
	 * @param taskSchedules
	 */
	public ScheduledTaskService(final ThreadPoolTaskScheduler scheduler, final List<TaskSchedule> taskSchedules)
	{
		this.scheduler = scheduler;
		this.taskSchedules = taskSchedules;
	}


	/**
	 * Schedule all the scheduled tasks.
	 */
	public void start()
	{
		LOG.info("Scheduled Task Service starting with {} scheduled tasks configured", this.taskSchedules.size());
		this.scheduledFutures = new ArrayList<ScheduledFuture<?>>();
		
		for (final TaskSchedule taskSchedule : this.taskSchedules)
		{
			
			final CronTrigger trigger = new CronTrigger(taskSchedule.getCronSchedule());

			
			this.scheduledFutures.add(this.scheduler.schedule(taskSchedule.getScheduledTask(), trigger));

			LOG.info("Scheduled task: {}", taskSchedule);
		}


	}


	/**
	 * Stop running the scheduled tasks. Used when pausing the main service.
	 */
	public void stop()
	{
		LOG.info("Scheduled Task Service stopping with {} scheduled futures to cancel", this.scheduledFutures.size());
		for (final ScheduledFuture<?> future : this.scheduledFutures)
		{

			if ((future != null) && !future.isDone())
			{
				future.cancel(false); 
			}


		}
	}


	/**
	 * Get the number of active futures.
	 *
	 * @return
	 */
	public int getActiveFuturesCount()
	{
		int activeFutures = 0;
		for (final ScheduledFuture<?> future : this.scheduledFutures)
		{

			if ((future != null) && !future.isDone())
			{
				activeFutures++;
			}


		}
		return activeFutures;
	}



}
