package uk.nhs.ers.task.common.mock;


import uk.nhs.ers.task.common.properties.ITaskServiceProperties;


public enum MockTaskServiceProperties implements ITaskServiceProperties
{
	PROPERTY("property_key", "property_value"),
	POOL_SIZE("pool_size_key", "pool_size_value");

	private final String key;
	private final String defaultValue;


	private MockTaskServiceProperties(final String key, final String defaultValue)
	{
		this.key = key;
		this.defaultValue = defaultValue;
	}


	@Override
	public String getKey()
	{
		return this.key;
	}


	@Override
	public String getDefaultValue()
	{
		return this.defaultValue;
	}
}
