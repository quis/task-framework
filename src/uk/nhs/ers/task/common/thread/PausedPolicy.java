package uk.nhs.ers.task.common.thread;


import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;


/**
 * {@link PausedPolicy} provides a Rejected Execution Handler that deals with the underlying executor being paused.
 *
 */
public class PausedPolicy implements RejectedExecutionHandler
{

	private final IPausable pausable;
	private final RejectedExecutionHandler discard;
	private final RejectedExecutionHandler callerRuns;


	/**
	 * Rejected Execution Handler that deals with the underlying executor being paused.
	 *
	 * @param pausable the pausable
	 */
	public PausedPolicy(final IPausable pausable)
	{
		this.pausable = pausable;
		this.discard = new DiscardPolicy();
		this.callerRuns = new CallerRunsPolicy();


	}


	@Override
	public void rejectedExecution(final Runnable rejectedTask, final ThreadPoolExecutor executor)
	{

		
		
		final RejectedExecutionHandler handler =
				this.pausable.isPaused() ? this.discard : this.callerRuns;

		handler.rejectedExecution(rejectedTask, executor);

	}

}
