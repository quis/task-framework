package uk.nhs.ers.task.common.mock;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.nhs.ers.task.common.exception.TaskExecutionException;
import uk.nhs.ers.task.common.task.AbstractTask;
import uk.nhs.ers.task.common.task.AbstractTaskParameters;


public class MockErroringTask extends AbstractTask
{
	private static final Logger LOG = LoggerFactory.getLogger(MockErroringTask.class);


	@Override
	public Class<? extends AbstractTaskParameters> getParameterClass()
	{
		return MockTaskParameters.class;
	}


	@Override
	public void doTask() throws TaskExecutionException
	{
		LOG.debug("MockErroringTask is running");
		throw new TaskExecutionException("intentional fail!");
	}
}
