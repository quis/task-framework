package uk.nhs.ers.task.common.provider;


import java.util.List;

import uk.nhs.ers.task.common.task.TaskDefinition;


/**
 * {@link ITaskProvider} provides an interface for the Task Providers - adding and getting tasks from the queue
 *
 */
public interface ITaskProvider
{
	/**
	 * Obtain a list of tasks to process
	 *
	 * @return List<IHealthcheckTask>
	 */
	List<TaskDefinition> obtainTasks();


	/**
	 * add tasks to the queue
	 *
	 * @param tasks
	 */
	void queueTasks(List<TaskDefinition> tasks);

}
