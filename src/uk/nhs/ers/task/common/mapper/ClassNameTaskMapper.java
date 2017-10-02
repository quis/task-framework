package uk.nhs.ers.task.common.mapper;


import uk.nhs.ers.task.common.factory.ITaskMapper;
import uk.nhs.ers.task.common.task.ITask;


public class ClassNameTaskMapper implements ITaskMapper
{

	@Override
	public Class<? extends ITask> mapClass(final String taskType) throws ClassNotFoundException
	{
		return (Class<? extends ITask>)Class.forName(taskType).asSubclass(ITask.class);
	}


	@Override
	public String unMapClass(final Class<? extends ITask> clazz)
	{
		return clazz.getName();
	}

}
