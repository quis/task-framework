package uk.nhs.ers.task.common.exception;


import org.testng.Assert;
import org.testng.annotations.Test;


public class TaskExecutionExceptionTest
{
	@Test
	public void testSimple()
	{
		final String message = "Banana";
		final TaskExecutionException tee = new TaskExecutionException(message);

		Assert.assertEquals(tee.getMessage(), message);
	}


	@Test
	public void testOverride()
	{
		final String message = "Banana";
		final String otherMessage = "Orange";
		final Exception ex = new Exception(otherMessage);
		final TaskExecutionException tee = new TaskExecutionException(message, ex);

		Assert.assertEquals(tee.getMessage(), message);
		Assert.assertEquals(tee.getCause(), ex);
	}


	@Test
	public void testNoOverride()
	{
		final String otherMessage = "Orange";
		final Exception ex = new Exception(otherMessage);
		final TaskExecutionException tee = new TaskExecutionException(ex);

		Assert.assertTrue(tee.getMessage().contains(otherMessage), "Should contain original message");
		Assert.assertEquals(tee.getCause(), ex);
	}
}
