package uk.nhs.ers.task.common.factory;


import uk.nhs.ers.task.common.task.ITask;


/**
 * {@link ITaskMapper} provides an interface for the Task Type to class mapper
 *
 */
public interface ITaskMapper
{
	/**
	 * find the class from the given String representation
	 *
	 * @param taskType
	 * @return Class determined from taskType
	 */
	Class<? extends ITask> mapClass(String taskType) throws ClassNotFoundException;


	/**
	 * find the representation of the given class as a string
	 *
	 * @param clazz
	 * @return
	 */
	String unMapClass(Class<? extends ITask> clazz);

}
