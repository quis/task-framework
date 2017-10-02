package uk.nhs.ers.task.common.mock;


import java.util.HashMap;
import java.util.Map;

import uk.nhs.ers.task.common.properties.ITaskServiceProperties;
import uk.nhs.ers.task.common.properties.ITaskServicePropertyUtilities;


public class MockTaskServicePropertyUtilities implements ITaskServicePropertyUtilities
{
	Map<String, ITaskServiceProperties> values = new HashMap<String, ITaskServiceProperties>();
	Map<String, String> underlyingValues = new HashMap<String, String>();


	public MockTaskServicePropertyUtilities()
	{
		this.values.put(MockTaskServiceProperties.POOL_SIZE.getKey(), MockTaskServiceProperties.POOL_SIZE);
		this.values.put(MockTaskServiceProperties.PROPERTY.getKey(), MockTaskServiceProperties.PROPERTY);
	}


	@Override
	public String getPropertyValue(final ITaskServiceProperties property)
	{
		return this.underlyingValues.get(property.getKey());
	}


	@Override
	public String setPropertyValue(final ITaskServiceProperties property, final String newValue)
	{
		final String previousValue = getPropertyValue(property);
		this.underlyingValues.put(property.getKey(), newValue);
		return previousValue;
	}


	@Override
	public ITaskServiceProperties getTaskServicePropertyWithKey(final String key)
	{
		return this.values.get(key);
	}
}
