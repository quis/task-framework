package uk.nhs.ers.task.common.task;


import org.testng.Assert;
import org.testng.annotations.Test;


public class EnumTest
{
	@Test
	public void testTaskStatus()
	{
		Assert.assertEquals(TaskStatus.valueOf("COMPLETED"), TaskStatus.COMPLETED);
		Assert.assertEquals(TaskStatus.valueOf("FAILED"), TaskStatus.FAILED);
		Assert.assertEquals(TaskStatus.valueOf("PENDING"), TaskStatus.PENDING);

	}


	@Test
	public void testTaskExecutionStatus()
	{
		Assert.assertEquals(TaskExecutionStatus.valueOf("CREATED"), TaskExecutionStatus.CREATED);
		Assert.assertEquals(TaskExecutionStatus.valueOf("ERROR"), TaskExecutionStatus.ERROR);
		Assert.assertEquals(TaskExecutionStatus.valueOf("OK"), TaskExecutionStatus.OK);
		Assert.assertEquals(TaskExecutionStatus.valueOf("RUNNING"), TaskExecutionStatus.RUNNING);
	}
}
