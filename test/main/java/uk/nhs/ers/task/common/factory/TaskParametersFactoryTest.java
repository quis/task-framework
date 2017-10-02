package uk.nhs.ers.task.common.factory;


import java.io.IOException;
import java.sql.Timestamp;

import org.testng.Assert;
import org.testng.annotations.Test;

import uk.nhs.ers.task.common.mock.MockTask;
import uk.nhs.ers.task.common.mock.MockTaskParameters;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;


public class TaskParametersFactoryTest
{
	@Test
	public void testFromJsonHappy() throws JsonParseException, JsonMappingException, IOException
	{
		final MockTaskParameters parameters = (MockTaskParameters)TaskParametersFactory.fromJSON(new MockTask(),
				new MockTaskParameters("One", 2L, new Timestamp(3)).toJSON());

		Assert.assertEquals("One", parameters.getParam1());
		Assert.assertEquals(new Long(2), parameters.getParam2());
		Assert.assertEquals(new Timestamp(3), parameters.getParam3());
	}


	@Test(expectedExceptions = { JsonParseException.class })
	public void testFromJsonNotJson() throws JsonParseException, JsonMappingException, IOException
	{
		TaskParametersFactory.fromJSON(new MockTask(), "ThisIsn'tValidJson");
	}


	@Test(expectedExceptions = { JsonMappingException.class })
	public void testFromJsonInvalidMapping() throws JsonParseException, JsonMappingException, IOException
	{
		TaskParametersFactory.fromJSON(new MockTask(), "{\"Field1\":\"One\"}");
	}
}
