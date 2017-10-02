package uk.nhs.ers.task.common.factory;



import uk.nhs.ers.task.common.exception.TaskCreationException;
import uk.nhs.ers.task.common.task.ITask;
import uk.nhs.ers.task.common.task.TaskDefinition;

import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * Factory interface for generating tasks.
 */
public interface ITaskFactory
{
	/**
	 * Create a new task.
	 *
	 * @return new task
	 */
	ITask newTask() throws TaskCreationException;


	/**
	 * Get the task definition for the given task
	 *
	 * @param task
	 * @return TaskDefinition
	 * @throws JsonProcessingException
	 */
	TaskDefinition extractDefinition(ITask task) throws JsonProcessingException;


}
