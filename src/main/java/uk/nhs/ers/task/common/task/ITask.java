package uk.nhs.ers.task.common.task;


import java.io.IOException;

import uk.nhs.ers.task.common.dao.ITaskDao;
import uk.nhs.ers.task.common.exception.TaskCreationException;
import uk.nhs.ers.task.common.exception.TaskException;
import uk.nhs.ers.task.common.exception.TaskExecutionException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


/**
 * {@link ITask} provides the interface for Tasks as the service expects them.
 *
 */
public interface ITask extends Runnable
{


	/**
	 * parse the parameters given and assign to the TaskParameters object in the class
	 *
	 * @param jsonParams
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	void parseParameters(String jsonParams) throws JsonParseException, JsonMappingException, IOException;


	/**
	 * Take the parameters assigned to the task and encode them to a JSON string
	 *
	 * @return the parameter object as a JSON string
	 * @throws JsonProcessingException
	 */
	String encodeParameters() throws JsonProcessingException;


	/**
	 * Returns the value for the field {@link taskDef}
	 *
	 * @return taskDef
	 * @throws TaskCreationException
	 */
	TaskDefinition getTaskDef() throws TaskCreationException;


	/**
	 * Sets the value for the field {@link taskDef}
	 *
	 * @param taskDef
	 */
	void setTaskDef(TaskDefinition taskDef);


	/**
	 * Returns the value for the field {@link taskDao}
	 *
	 * @return taskDao
	 */
	ITaskDao getTaskDao();


	/**
	 * Sets the value for the field {@link taskDao}
	 *
	 * @param taskDao
	 */
	void setTaskDao(ITaskDao taskDao);


	/**
	 * Do the task for this ITask
	 *
	 * @throws TaskExecutionException
	 */
	void doTask() throws TaskExecutionException;


	/**
	 * Handle the given exception
	 *
	 * @param exception
	 */
	void handleException(TaskException exception);


	/**
	 * Get the type of task this was
	 *
	 * @return taskType
	 */
	String getTaskType();


	/**
	 * Get the assigned identifier
	 *
	 * @return identifier
	 */
	String getIdentifier();


	/**
	 * Get the parameters object
	 *
	 * @return parameters
	 */
	ITaskParameters getParameters();


	/**
	 * Set the Identifier of this Task
	 *
	 * @param identifier
	 */
	void setIdentifier(String identifier);
}
