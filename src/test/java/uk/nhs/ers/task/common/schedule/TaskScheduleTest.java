package uk.nhs.ers.task.common.schedule;


import org.testng.Assert;
import org.testng.annotations.Test;

import uk.nhs.ers.task.common.mock.MockScheduledTask;


public class TaskScheduleTest
{
	@Test
	public void testSimple()
	{
		final MockScheduledTask scheduledTask = new MockScheduledTask();
		final String cronSchedule = "asgadfg";
		final TaskSchedule task = new TaskSchedule(scheduledTask, cronSchedule);
		Assert.assertEquals(task.getScheduledTask(), scheduledTask);
		Assert.assertEquals(task.getCronSchedule(), cronSchedule);
	}
}
