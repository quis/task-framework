package uk.nhs.ers.task.common.task;


import java.io.IOException;
import java.sql.Timestamp;

import org.testng.Assert;
import org.testng.annotations.Test;

import uk.nhs.ers.task.common.mock.MockErrorHandler;
import uk.nhs.ers.task.common.mock.MockErroringTask;
import uk.nhs.ers.task.common.mock.MockTask;
import uk.nhs.ers.task.common.mock.MockTaskDao;
import uk.nhs.ers.task.common.mock.MockTaskParameters;


public class AbstractTaskTest
{
	@Test
	public void testTask()
	{
		final MockTask task = new MockTask();
		final MockTaskDao taskDao = new MockTaskDao();
		final String identifier = "asgjkni";
		task.setTaskDao(taskDao);
		task.setIdentifier(identifier);

		Assert.assertEquals(task.getTaskDao(), taskDao);
		Assert.assertEquals(task.getIdentifier(), identifier);

		task.run();

		Assert.assertEquals(taskDao.getLastStatus(), TaskStatus.COMPLETED);
	}


	@Test
	public void testTaskError()
	{
		final MockErroringTask task = new MockErroringTask();
		final MockTaskDao taskDao = new MockTaskDao();
		final MockErrorHandler handler = new MockErrorHandler();
		task.registerErrorHandler(handler);
		task.setTaskDao(taskDao);
		task.run();
		Assert.assertEquals(taskDao.getLastStatus(), TaskStatus.FAILED);
		Assert.assertNotNull(taskDao.getStatusNote(), "Task Status Note should not be Null");
		Assert.assertEquals(taskDao.getStatusTask().hashCode(), task.hashCode());
		Assert.assertTrue(handler.isExceptionHandled());
	}


	@Test
	public void testEncodeDecode() throws IOException
	{
		final MockTask task = new MockTask();
		final String param1 = "Param1";
		final Long param2 = 2L;
		final Timestamp param3 = new Timestamp(0);
		final MockTaskParameters taskParameters = new MockTaskParameters(param1, param2, param3);
		final String encoded = taskParameters.toJSON();
		task.parseParameters(encoded);
		Assert.assertEquals(task.encodeParameters(), encoded);
	}
}
