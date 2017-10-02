package uk.nhs.ers.task.common.mock;


import java.io.IOException;

import uk.nhs.ers.task.common.dao.ITaskDao;
import uk.nhs.ers.task.common.exception.TaskCreationException;
import uk.nhs.ers.task.common.exception.TaskException;
import uk.nhs.ers.task.common.exception.TaskExecutionException;
import uk.nhs.ers.task.common.task.ITask;
import uk.nhs.ers.task.common.task.ITaskParameters;
import uk.nhs.ers.task.common.task.TaskDefinition;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


public class MockNoConstructorTask implements ITask
{
	private ITaskDao taskDao;
	private String aString;


	public MockNoConstructorTask(final String someString)
	{
		setaString(someString);
	}


	@Override
	public void run()
	{
		// no impl
	}


	@Override
	public void parseParameters(final String jsonParams) throws JsonParseException, JsonMappingException, IOException
	{
		// no impl
	}


	@Override
	public String encodeParameters() throws JsonProcessingException
	{
		return null;
	}


	@Override
	public TaskDefinition getTaskDef() throws TaskCreationException
	{
		return null;
	}


	@Override
	public void setTaskDef(final TaskDefinition taskDef)
	{
		// no impl
	}


	@Override
	public ITaskDao getTaskDao()
	{
		return this.taskDao;
	}


	@Override
	public void setTaskDao(final ITaskDao taskDao)
	{
		this.taskDao = taskDao;
	}


	@Override
	public void doTask() throws TaskExecutionException
	{
		// no impl
	}


	@Override
	public void handleException(final TaskException exception)
	{
		// no impl
	}


	@Override
	public String getTaskType()
	{
		return null;
	}


	@Override
	public String getIdentifier()
	{
		return null;
	}


	@Override
	public ITaskParameters getParameters()
	{
		return null;
	}


	@Override
	public void setIdentifier(final String identifier)
	{
		// no impl
	}


	/**
	 * Returns the value for the field {@link aString}
	 */
	public String getaString()
	{
		return this.aString;
	}


	/**
	 * Sets the value for the field {@link aString}
	 */
	public void setaString(final String aString)
	{
		this.aString = aString;
	}
}
