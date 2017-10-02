package uk.nhs.ers.task.common.task;


import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TaskDefinitionTest
{
	@Test
	public void testSimple()
	{
		final String taskClass = "task class";
		final String taskParameters = "task parameters";
		final Timestamp activeFrom = new Timestamp(2000);
		final Timestamp lockExpiry = new Timestamp(1000);
		final Long idValue = 5L;

		final TaskDefinition taskDef = new TaskDefinition(taskClass, taskParameters);
		taskDef.setActiveFrom(activeFrom);
		taskDef.setTaskId(idValue);
		taskDef.setLockExpiry(lockExpiry);


		Assert.assertEquals(taskDef.getTaskType(), taskClass);
		Assert.assertEquals(taskDef.getTaskParameters(), taskParameters);
		Assert.assertEquals(taskDef.getActiveFrom(), activeFrom);
		Assert.assertEquals(taskDef.getLockExpiry(), lockExpiry);
		Assert.assertEquals(taskDef.getTaskId(), idValue);
		Assert.assertEquals(taskDef.hasExpired(), true);

		taskDef.setLockExpiry(new Timestamp(DateTime.now().plusDays(10).getMillis()));
		Assert.assertEquals(taskDef.hasExpired(), false);

		System.out.println(taskDef);

	}


	@Test
	public void testEquals()
	{
		final String taskClass = "task class";
		final String taskParameters = "task parameters";
		final Timestamp activeFrom = new Timestamp(2000);
		final Timestamp lockExpiry = new Timestamp(1000);
		final Long idValue = 5L;

		final TaskDefinition taskDef = new TaskDefinition(taskClass, taskParameters);
		taskDef.setActiveFrom(activeFrom);
		taskDef.setTaskId(idValue);
		taskDef.setLockExpiry(lockExpiry);
		final TaskDefinition taskDef2 = new TaskDefinition(taskClass, taskParameters);
		taskDef2.setActiveFrom(activeFrom);
		taskDef2.setTaskId(idValue);
		taskDef2.setLockExpiry(new Timestamp(3000));
		Assert.assertEquals(taskDef.hashCode(), taskDef2.hashCode());
		Assert.assertEquals(taskDef, taskDef2);

		taskDef2.setTaskType("Banana");
		Assert.assertFalse(taskDef.equals(taskDef2));

		Assert.assertFalse(taskDef.equals(activeFrom));
	}
}
