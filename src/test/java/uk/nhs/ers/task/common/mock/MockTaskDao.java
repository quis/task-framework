package uk.nhs.ers.task.common.mock;


import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import uk.nhs.ers.task.common.dao.ITaskDao;
import uk.nhs.ers.task.common.task.ITask;
import uk.nhs.ers.task.common.task.TaskDefinition;
import uk.nhs.ers.task.common.task.TaskStatus;


public class MockTaskDao implements ITaskDao
{
	final List<TaskDefinition> storedTaskDefs = new ArrayList<TaskDefinition>();

	private TaskStatus lastStatus;
	private ITask statusTask;
	private TaskDefinition statusTaskDef;
	private String statusNote;


	@Override
	public void addTasks(final List<TaskDefinition> taskDefs)
	{
		this.storedTaskDefs.addAll(taskDefs);
	}


	@Override
	public List<TaskDefinition> getTasks()
	{
		return this.storedTaskDefs;
	}


	@Override
	public void updateStatus(final ITask task, final TaskStatus status, final String statusNote)
	{
		this.lastStatus = status;
		this.statusTask = task;
		this.statusNote = statusNote;
	}


	@Override
	public void updateStatus(final TaskDefinition taskDef, final TaskStatus status, final String statusNote)
	{
		this.lastStatus = status;
		setStatusTaskDef(taskDef);
		this.statusNote = statusNote;

	}


	/**
	 * Returns the value for the field {@link lastStatus}
	 */
	public TaskStatus getLastStatus()
	{
		return this.lastStatus;
	}


	/**
	 * Sets the value for the field {@link lastStatus}
	 */
	public void setLastStatus(final TaskStatus lastStatus)
	{
		this.lastStatus = lastStatus;
	}


	/**
	 * Returns the value for the field {@link statusTask}
	 */
	public ITask getStatusTask()
	{
		return this.statusTask;
	}


	/**
	 * Sets the value for the field {@link statusTask}
	 */
	public void setStatusTask(final ITask statusTask)
	{
		this.statusTask = statusTask;
	}


	/**
	 * Returns the value for the field {@link statusNote}
	 */
	public String getStatusNote()
	{
		return this.statusNote;
	}


	/**
	 * Sets the value for the field {@link statusNote}
	 */
	public void setStatusNote(final String statusNote)
	{
		this.statusNote = statusNote;
	}


	@Override
	public JdbcTemplate getTemplate()
	{
		return null;
	}


	@Override
	public TaskDefinition getTask(final Long taskId)
	{

		return null;
	}


	@Override
	public void addTask(final TaskDefinition taskDef)
	{
		this.storedTaskDefs.add(taskDef);
	}


	/**
	 * Returns the value for the field {@link statusTaskDef}
	 */
	public TaskDefinition getStatusTaskDef()
	{
		return this.statusTaskDef;
	}


	/**
	 * Sets the value for the field {@link statusTaskDef}
	 */
	public void setStatusTaskDef(final TaskDefinition statusTaskDef)
	{
		this.statusTaskDef = statusTaskDef;
	}
}
