package uk.nhs.ers.task.common.task;


import java.sql.Timestamp;

import org.testng.Assert;
import org.testng.annotations.Test;

import uk.nhs.ers.task.common.mock.MockEmptyTaskParameters;
import uk.nhs.ers.task.common.mock.MockTaskParameters;

import com.fasterxml.jackson.core.JsonProcessingException;


public class AbstractTaskParametersTest
{
	@Test
	public void testToJson() throws JsonProcessingException
	{
		final String param1 = "Param1";
		final Long param2 = 2L;
		final Timestamp param3 = new Timestamp(0);
		final MockTaskParameters taskParameters = new MockTaskParameters(param1, param2, param3);
		Assert.assertEquals(taskParameters.toJSON(),
				"{\"param1\":\"Param1\",\"param2\":2,\"param3\":0}");
	}


	@Test
	public void testEmpty() throws JsonProcessingException
	{
		final MockEmptyTaskParameters parameters = new MockEmptyTaskParameters();
		Assert.assertEquals(parameters.toJSON(), "{}");
	}
}
