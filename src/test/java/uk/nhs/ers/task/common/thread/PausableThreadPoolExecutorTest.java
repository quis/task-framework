package uk.nhs.ers.task.common.thread;



import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;


public class PausableThreadPoolExecutorTest
{
	@Test
	public void runATask() throws InterruptedException
	{
		final PausableThreadPoolExecutor executor = new PausableThreadPoolExecutor(
				1, 1, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1));

		final TestTask task = new TestTask();
		executor.execute(task);

		Assert.assertTrue(task.waitForTaskToRun(1000), "Task has not run");

	}


	@Test
	public void pauseAndResume() throws InterruptedException
	{
		final PausableThreadPoolExecutor executor = new PausableThreadPoolExecutor(
				1, 1, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1));

		executor.pause();

		final TestTask task = new TestTask();
		try
		{
			executor.execute(task);
		}
		catch (final RejectedExecutionException ree)
		{}
		catch (final Exception e)
		{
			Assert.fail("Incorrect exception");
		}

		Assert.assertFalse(task.waitForTaskToRun(500), "Task should not have run");

		executor.resume();
		executor.execute(task);

		Assert.assertTrue(task.waitForTaskToRun(500), "Task has not run");

	}


	@Test
	public void pauseAndResumeMulipleTasks() throws InterruptedException
	{
		final PausableThreadPoolExecutor executor = new PausableThreadPoolExecutor(
				1, 1, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(15));

		final DelayedTestTask firstTask = new DelayedTestTask();
		executor.execute(firstTask);

		final List<TestTask> expectedCompletedTasks = new ArrayList<TestTask>();
		expectedCompletedTasks.add(firstTask);

		for (int i = 0; i < 5; i++)
		{
			final TestTask task = new TestTask();
			executor.execute(task);
			expectedCompletedTasks.add(task);
		}

		final DelayedTestTask secondTask = new DelayedTestTask();
		expectedCompletedTasks.add(secondTask);
		executor.execute(secondTask);

		final List<TestTask> expectedSkippedTasks = new ArrayList<TestTask>();
		for (int i = 0; i < 5; i++)
		{
			final TestTask task = new TestTask();
			executor.execute(task);
			expectedSkippedTasks.add(task);
		}

		firstTask.waitForTaskToStart(500, TimeUnit.MILLISECONDS);
		firstTask.triggerTask();
		firstTask.waitForTaskToRun(100);

		secondTask.waitForTaskToStart(500, TimeUnit.MILLISECONDS);

		executor.pause();

		secondTask.triggerTask();

		for (final TestTask testTask : expectedCompletedTasks)
		{
			Assert.assertTrue(testTask.waitForTaskToRun(50));
		}

		Assert.assertEquals(0, executor.getQueue().size());

		for (final TestTask testTask : expectedSkippedTasks)
		{
			Assert.assertFalse(testTask.waitForTaskToRun(50));
		}

	}

	private static class TestTask implements Runnable
	{
		private final CountDownLatch latch;


		private TestTask()
		{
			this.latch = new CountDownLatch(1);

		}


		@Override
		public void run()
		{
			this.latch.countDown();
		}


		public boolean waitForTaskToRun(final long waitMillis)
				throws InterruptedException
		{
			return this.latch.await(waitMillis, TimeUnit.MILLISECONDS);
		}
	}

	private static class DelayedTestTask extends TestTask
	{
		private final CountDownLatch delayLatch = new CountDownLatch(1);
		private final CountDownLatch taskStartedRunning = new CountDownLatch(1);


		public void triggerTask()
		{
			this.delayLatch.countDown();
		}


		public void waitForTaskToStart(final int duration, final TimeUnit unit) throws InterruptedException
		{
			this.taskStartedRunning.await(duration, unit);
		}


		@Override
		public void run()
		{
			try
			{
				this.taskStartedRunning.countDown();
				this.delayLatch.await();
				super.run();
			}
			catch (final InterruptedException e)
			{
				Assert.fail("Task triggered early");
			}
		}
	}
}

