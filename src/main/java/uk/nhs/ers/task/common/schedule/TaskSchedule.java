package uk.nhs.ers.task.common.schedule;

/**
 * {@link TaskSchedule} provides a configuration item for scheduling a task
 *
 */
public class TaskSchedule
{

	private final IScheduledTask scheduledTask;
	private final String cronSchedule;


	/**
	 * Construct a new Task Schedule - a scheduled task with a cron schedule
	 * 
	 * @param scheduledTask
	 * @param cronSchedule
	 */
	public TaskSchedule(final IScheduledTask scheduledTask, final String cronSchedule)
	{
		this.scheduledTask = scheduledTask;
		this.cronSchedule = cronSchedule;
	}


	/**
	 * Returns the value for the field {@link scheduledTask}
	 *
	 * @return the scheduledTask
	 */
	public IScheduledTask getScheduledTask()
	{
		return this.scheduledTask;
	}


	/**
	 * Returns the value for the field {@link cronSchedule}
	 *
	 * @return the cron schedule
	 */
	public String getCronSchedule()
	{
		return this.cronSchedule;
	}


	@Override
	public String toString()
	{
		return String.format("%s : [%s]", this.scheduledTask.getClass().getSimpleName(), this.cronSchedule);
	}


}
