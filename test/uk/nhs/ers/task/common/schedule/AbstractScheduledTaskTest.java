package uk.nhs.ers.task.common.schedule;


import org.testng.annotations.Test;

import uk.nhs.ers.task.common.mock.MockScheduledTask;


public class AbstractScheduledTaskTest
{
	@Test
	public void testSimple()
	{
		final MockScheduledTask scheduledTask = new MockScheduledTask();
		scheduledTask.run();
	}
}
