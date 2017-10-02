package uk.nhs.ers.task.common.dao;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import uk.nhs.ers.task.common.exception.TaskCreationException;
import uk.nhs.ers.task.common.task.ITask;
import uk.nhs.ers.task.common.task.TaskDefinition;
import uk.nhs.ers.task.common.task.TaskDefinitionMapper;
import uk.nhs.ers.task.common.task.TaskStatus;

import com.google.common.collect.Lists;


/**
 * {@link TaskDao} provides an implementation for common Task queue operations on the Task Schema in the database
 */
public class TaskDao implements ITaskDao
{

	/** GET_SQL */
	private static final String GET_SQL =
			"SELECT id, task_type, parameters, created, active_from, status, status_updated, status_text, lock_expiry, identifier FROM task.tsk_task where id=?";
	private final JdbcTemplate template;
	private final static String INSERT_SQL =
			"INSERT INTO task.tsk_task(task_type,parameters, active_from) VALUES (?, ?, ?); ";
	private final static String FETCH_SQL =
			"SELECT id, task_type, parameters, created, active_from, status, status_updated, status_text, lock_expiry, identifier "
					+ " FROM task.fn_task_acquire_tasks() ";
	private final static String UPDATE_STATUS =
			"UPDATE task.tsk_task SET status=?, status_text=?, status_updated=current_timestamp, identifier=? WHERE id = ?";
	private final static Logger LOG = LoggerFactory.getLogger(TaskDao.class);


	/**
	 * Create a new TaskDao by Datasource
	 *
	 * @param datasource
	 */
	public TaskDao(final DataSource datasource)
	{
		this.template = new JdbcTemplate(datasource);
	}


	/**
	 * Create a new TaskDao by JdbcTemplate
	 *
	 * @param template
	 */
	public TaskDao(final JdbcTemplate template)
	{
		this.template = template;
	}



	@Override
	public void addTasks(final List<TaskDefinition> taskDefs)
	{
		LOG.debug("Inserting {} tasks", taskDefs.size());
		getTemplate().batchUpdate(INSERT_SQL, new TaskInsertBatchPreparedStatementSetter(taskDefs));

	}


	@Override
	public void addTask(final TaskDefinition taskDef)
	{
		final List<TaskDefinition> tasks = new ArrayList<TaskDefinition>();
		tasks.add(taskDef);
		addTasks(tasks);

	}


	@Override
	public List<TaskDefinition> getTasks()
	{
		final List<TaskDefinition> taskDefs = getTemplate().query(FETCH_SQL, new TaskDefinitionMapper());

		return taskDefs;
	}



	/**
	 * Returns the value for the field {@link template}
	 */
	@Override
	public JdbcTemplate getTemplate()
	{
		return this.template;
	}



	static class TaskInsertBatchPreparedStatementSetter implements BatchPreparedStatementSetter
	{
		private final List<TaskDefinition> items;


		TaskInsertBatchPreparedStatementSetter(final List<TaskDefinition> items)
		{
			this.items = Lists.newArrayList(items);
		}


		@Override
		public int getBatchSize()
		{
			return this.items.size();
		}


		@Override
		public void setValues(final PreparedStatement statement, final int index) throws SQLException
		{
			final TaskDefinition item = this.items.get(index);
			int idx = 1;
			statement.setString(idx++, item.getTaskType());
			statement.setString(idx++, item.getTaskParameters());
			statement.setTimestamp(idx++, item.getActiveFrom());
		}



	}



	@Override
	public void updateStatus(final ITask task, final TaskStatus status, final String statusNote)
			throws TaskCreationException
	{
		
		
		if ((task.getIdentifier() != null) && (task.getTaskDef() != null))
		{
			task.getTaskDef().setIdentifier(task.getIdentifier());
		}
		updateStatus(task.getTaskDef(), status, statusNote);

	}


	@Override
	public void updateStatus(final TaskDefinition taskDef, final TaskStatus status, final String statusNote)
	{
		LOG.debug("Setting task [{}] to [{}]", taskDef.getTaskId(), status);
		getTemplate().update(UPDATE_STATUS, status.toString(), statusNote, taskDef.getIdentifier(),
				taskDef.getTaskId());

	}


	/**
	 * Get a single task def by Id
	 *
	 * @param taskId
	 * @return
	 */
	@Override
	public TaskDefinition getTask(final Long taskId)
	{
		return getTemplate().queryForObject(GET_SQL,
				new TaskDefinitionMapper(), taskId);
	}



}
