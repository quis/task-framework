package uk.nhs.ers.task.common.mapper;


import uk.nhs.ers.task.common.task.ITask;


/**
 * {@link ITaskMap} provides the interface for a task map enum
 *
 */
public interface ITaskMap
{

	/**
	 * Get the taskClass for this map entry
	 *
	 * @return an ITask derived class
	 */
	Class<? extends ITask> getTaskClass();
}
