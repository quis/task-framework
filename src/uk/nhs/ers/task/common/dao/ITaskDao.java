package uk.nhs.ers.task.common.dao;


import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import uk.nhs.ers.task.common.exception.TaskCreationException;
import uk.nhs.ers.task.common.exception.TaskExecutionException;
import uk.nhs.ers.task.common.task.ITask;
import uk.nhs.ers.task.common.task.TaskDefinition;
import uk.nhs.ers.task.common.task.TaskStatus;


/**
 * {@link ITaskDao} provides an interface for common Task queue operations
 *
 */
public interface ITaskDao
{

	/**
	 * write Tasks to the task queue
	 *
	 * @param taskDefs
	 */
	void addTasks(List<TaskDefinition> taskDefs);


	/**
	 * get Tasks from the task queue
	 *
	 * @return list of tasks
	 */
	List<TaskDefinition> getTasks();


	/**
	 * Update the status of a task on the queue Also sets the identifier in the database for future reference.
	 *
	 * @param task
	 * @param status
	 * @param statusNote
	 * @throws TaskExecutionException
	 * @throws TaskCreationException
	 */
	void updateStatus(ITask task, TaskStatus status, String statusNote)
			throws TaskCreationException;


	/**
	 * Updates the status of this task in the queue. Also sets the identifier in the database for future reference.
	 *
	 * @param taskDef
	 * @param status
	 * @param statusNote
	 * @throws TaskExecutionException
	 */
	void updateStatus(TaskDefinition taskDef, TaskStatus status, String statusNote);


	/**
	 * Get the underlying JdbcTemplate
	 *
	 * @return JdbcTemplate
	 */
	JdbcTemplate getTemplate();


	/**
	 * Get a single task by Id
	 *
	 * @param taskId
	 * @return
	 */
	TaskDefinition getTask(Long taskId);


	/**
	 * add a single task
	 *
	 * @param taskDef
	 */
	void addTask(TaskDefinition taskDef);
}
