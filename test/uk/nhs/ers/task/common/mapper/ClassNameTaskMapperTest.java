package uk.nhs.ers.task.common.mapper;


import org.testng.Assert;
import org.testng.annotations.Test;

import uk.nhs.ers.task.common.mock.MockTask;


public class ClassNameTaskMapperTest
{
	@Test
	public void testSimple() throws ClassNotFoundException
	{
		final ClassNameTaskMapper mapper = new ClassNameTaskMapper();
		Assert.assertEquals(mapper.mapClass(MockTask.class.getName()), MockTask.class);
		Assert.assertEquals(mapper.unMapClass(MockTask.class), MockTask.class.getName());
	}
}
