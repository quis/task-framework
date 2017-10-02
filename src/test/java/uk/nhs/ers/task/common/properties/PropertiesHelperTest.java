package uk.nhs.ers.task.common.properties;


import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.Test;


public class PropertiesHelperTest
{
	@Test
	public void getStringProperty_ContainsKey()
	{
		final String key = "test.key";
		final String value = "wibble";
		final String defaultValue = "wobble";

		final Properties properties = new Properties();
		properties.put(key, value);

		final ITaskServiceProperties archiverProperies = new TestTaskProperty(
				key, defaultValue);

		final String result = PropertiesHelper.getStringProperty(properties,
				archiverProperies);

		Assert.assertEquals(result, value);
	}


	@Test
	public void getStringProperty_DoesNotContainKey()
	{
		final String key = "test.key";
		final String defaultValue = "wobble";

		
		final Properties properties = new Properties();

		final ITaskServiceProperties archiverProperies = new TestTaskProperty(
				key, defaultValue);

		final String result = PropertiesHelper.getStringProperty(properties,
				archiverProperies);

		Assert.assertEquals(result, defaultValue);
	}


	@Test
	public void getIntegerProperty_ContainsKey()
	{
		final String key = "test.key";
		final String value = "50";
		final String defaultValue = "55";

		final Properties properties = new Properties();
		properties.put(key, value);

		final ITaskServiceProperties archiverProperies = new TestTaskProperty(
				key, defaultValue);

		final int result = PropertiesHelper.getIntegerProperty(properties,
				archiverProperies);

		Assert.assertEquals(result, Integer.valueOf(value).intValue());
	}


	@Test
	public void getIntegerProperty_DoesNotContainKey()
	{
		final String key = "test.key";
		final String defaultValue = "55";

		
		final Properties properties = new Properties();

		final ITaskServiceProperties archiverProperies = new TestTaskProperty(
				key, defaultValue);

		final int result = PropertiesHelper.getIntegerProperty(properties,
				archiverProperies);

		Assert.assertEquals(result, Integer.valueOf(defaultValue).intValue());
	}

	private static class TestTaskProperty implements ITaskServiceProperties
	{
		private final String key;
		private final String defaultValue;


		private TestTaskProperty(final String key, final String defaultValue)
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

}
