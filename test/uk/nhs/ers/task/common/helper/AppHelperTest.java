package uk.nhs.ers.task.common.helper;


import org.testng.Assert;
import org.testng.annotations.Test;


public class AppHelperTest
{
	@Test
	public void testHappy()
	{
		Assert.assertFalse(AppHelper.startPaused(new String[] { "--unpaused" }), "--unpaused should return false");

		Assert.assertTrue(AppHelper.startPaused(new String[] {}),
				"Nothing should return true");
	}


	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void testTooMany()
	{
		AppHelper.startPaused(new String[] { "--unpaused", "orange" });
	}


	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void testWrongOne()
	{
		AppHelper.startPaused(new String[] { "orange" });
	}
}
