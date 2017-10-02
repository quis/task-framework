package uk.nhs.ers.task.common.thread;


import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.testng.annotations.Test;

import uk.nhs.ers.task.common.mock.MockExecutionRecordingTask;



public class PausedPolicyTest
{
	@Test
	public void unpaused() throws InterruptedException
	{
		final IPausable pausable = new MockPausable(false);

		final RejectedExecutionHandler handler = new PausedPolicy(pausable);

		final MockExecutionRecordingTask task = new MockExecutionRecordingTask();
		handler.rejectedExecution(task,
				new ThreadPoolExecutor(0, 1, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1)));

		assertTrue(task.isComplete());
	}


	@Test
	public void paused() throws InterruptedException
	{
		final IPausable pausable = new MockPausable(true);

		final RejectedExecutionHandler handler = new PausedPolicy(pausable);

		final MockExecutionRecordingTask task = new MockExecutionRecordingTask();
		handler.rejectedExecution(task,
				new ThreadPoolExecutor(0, 1, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1)));

		assertFalse(task.isComplete(50, TimeUnit.MILLISECONDS));
	}


	private static final class MockPausable implements IPausable
	{
		private final AtomicBoolean paused;


		MockPausable(final boolean initialState)
		{
			this.paused = new AtomicBoolean(initialState);
		}


		@Override
		public void pause()
		{
			this.paused.set(true);
		}


		@Override
		public void resume()
		{
			this.paused.set(false);
		}


		@Override
		public boolean isPaused()
		{
			return this.paused.get();
		}
	}
}
