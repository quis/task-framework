package uk.nhs.ers.task.common.mock;


import java.util.concurrent.atomic.AtomicBoolean;

import uk.nhs.ers.task.common.control.IController;


public class MockActionRecordingController implements IController
{
	private final AtomicBoolean started = new AtomicBoolean(false);
	private final AtomicBoolean stopped = new AtomicBoolean(false);
	private final AtomicBoolean paused = new AtomicBoolean(false);


	@Override
	public void start()
	{
		this.started.set(true);
	}


	@Override
	public void stop()
	{
		this.stopped.set(true);
	}


	public boolean isStarted()
	{
		return this.started.get();
	}


	public boolean isStopped()
	{
		return this.stopped.get();
	}


	@Override
	public void pause()
	{
		this.paused.set(true);
	}


	public boolean isPaused()
	{
		return this.paused.get();
	}
}
