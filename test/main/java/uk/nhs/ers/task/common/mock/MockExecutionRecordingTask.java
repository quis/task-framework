package uk.nhs.ers.task.common.mock;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import uk.nhs.ers.task.common.exception.TaskExecutionException;
import uk.nhs.ers.task.common.task.AbstractTask;
import uk.nhs.ers.task.common.task.AbstractTaskParameters;
import uk.nhs.ers.task.common.task.ITask;


public class MockExecutionRecordingTask extends AbstractTask
{
	private final CountDownLatch latch = new CountDownLatch(1);
	private final AtomicBoolean completed = new AtomicBoolean(false);
	private final ITask wrappedTask;


	public MockExecutionRecordingTask(final ITask wrappedTask)
	{
		this.wrappedTask = wrappedTask;
	}


	public MockExecutionRecordingTask()
	{
		this.wrappedTask = new MockTask();
		this.wrappedTask.setTaskDao(new MockTaskDao());
	}


	@Override
	public void run()
	{
		this.wrappedTask.run();
		this.completed.set(true);
		this.latch.countDown();
	}


	public boolean isComplete() throws InterruptedException
	{
		this.latch.await();
		return this.completed.get();
	}


	public boolean isComplete(final long timeout, final TimeUnit period) throws InterruptedException
	{
		this.latch.await(timeout, period);
		return this.completed.get();
	}


	@Override
	public void doTask() throws TaskExecutionException
	{
		// no impl
	}


	@Override
	public Class<? extends AbstractTaskParameters> getParameterClass()
	{
		return null;
	}
}
