package uk.nhs.ers.task.common.app;


import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import uk.nhs.ers.task.common.mock.MockActionRecordingController;


public class ShutdownHookTest
{
	@Test
	public void hookInvokesController()
	{
		final MockActionRecordingController controller = new MockActionRecordingController();
		final ShutdownHook hook = new ShutdownHook(controller);
		assertFalse(controller.isStopped());
		hook.run();
		assertTrue(controller.isStopped());
	}
}
