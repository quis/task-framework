package uk.nhs.ers.task.common.mock;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.nhs.ers.task.common.task.AbstractTask;
import uk.nhs.ers.task.common.task.AbstractTaskParameters;


public class MockTask extends AbstractTask
{
	private static final Logger LOG = LoggerFactory.getLogger(MockTask.class);


	public MockTask()
	{}


	@Override
	public Class<? extends AbstractTaskParameters> getParameterClass()
	{
		return MockTaskParameters.class;
	}



	@Override
	public void doTask()
	{
		LOG.debug("MockTask has run");
	}
}
