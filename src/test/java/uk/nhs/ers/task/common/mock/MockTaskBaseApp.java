package uk.nhs.ers.task.common.mock;


import uk.nhs.ers.task.common.app.AbstractTaskBaseApp;


public class MockTaskBaseApp extends AbstractTaskBaseApp
{
	public final static String CONTEXT_LOCATION = "/context/MockAppContext.xml";


	@Override
	protected String[] getApplicationContextLocation()
	{
		return new String[] { CONTEXT_LOCATION };
	}



	public String[] getContextLocation()
	{
		return getApplicationContextLocation();
	}
}
