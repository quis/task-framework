package uk.nhs.ers.task.common.provider;


import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.nhs.ers.task.common.dao.ITaskDao;
import uk.nhs.ers.task.common.dao.TaskDao;
import uk.nhs.ers.task.common.task.TaskDefinition;


/**
 * {@link DatabaseBackedTaskProvider} provides Tasks from the Task Queue.
 *
 */
public class DatabaseBackedTaskProvider implements ITaskProvider
{
	private static final Logger LOG = LoggerFactory.getLogger(DatabaseBackedTaskProvider.class);
	private final ITaskDao taskDao;


	/**
	 * create with a dataasource
	 *
	 * @param datasource
	 */
	public DatabaseBackedTaskProvider(final DataSource datasource)
	{
		this.taskDao = new TaskDao(datasource);
	}


	/**
	 * Create with a taskDao
	 *
	 * @param taskDao
	 */
	public DatabaseBackedTaskProvider(final ITaskDao taskDao)
	{
		this.taskDao = taskDao;
	}


	@Override
	public List<TaskDefinition> obtainTasks()
	{
		LOG.debug("Obtaining tasks");
		return this.taskDao.getTasks();
	}


	@Override
	public void queueTasks(final List<TaskDefinition> tasks)
	{
		this.taskDao.addTasks(tasks);
	}


	/**
	 * Returns the value for the field {@link taskDao}
	 * 
	 * @return taskDao
	 */
	public ITaskDao getTaskDao()
	{
		return this.taskDao;
	}



}
