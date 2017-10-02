package uk.nhs.ers.task.common.mock;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class MockProperties extends Properties
{
	private static final long serialVersionUID = 1L;


	public MockProperties()
	{
		put("Key1", "Value1");
		put("Key2", "Value2");
	}


	public Map<String, String> asMap()
	{
		final Map<String, String> map = new HashMap<String, String>();

		for (final Map.Entry<Object, Object> entry : entrySet())
		{
			map.put((String)entry.getKey(), (String)entry.getValue());
		}
		return map;
	}
}
