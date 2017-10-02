package uk.nhs.ers.task.common.exception;


import org.testng.Assert;
import org.testng.annotations.Test;


public class TaskCreationExceptionTest
{
	@Test
	public void testSimple()
	{
		final String message = "Banana";
		final TaskCreationException tce = new TaskCreationException(message);

		Assert.assertEquals(tce.getMessage(), message);
	}


	@Test
	public void testOverride()
	{
		final String message = "Banana";
		final String otherMessage = "Orange";
		final Exception ex = new Exception(otherMessage);
		final TaskCreationException tce = new TaskCreationException(message, ex);

		Assert.assertEquals(tce.getMessage(), message);
		Assert.assertEquals(tce.getCause(), ex);
	}


	@Test
	public void testNoOverride()
	{
		final String otherMessage = "Orange";
		final Exception ex = new Exception(otherMessage);
		final TaskCreationException tce = new TaskCreationException(ex);

		Assert.assertTrue(tce.getMessage().contains(otherMessage), "Should contain original message");
		Assert.assertEquals(tce.getCause(), ex);
	}
}
