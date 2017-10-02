package uk.nhs.ers.task.common.task;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


/**
 * {@link TaskDefinitionMapper} provides A mapper from resultSet to TaskDefinition
 *
 */
public class TaskDefinitionMapper implements RowMapper<TaskDefinition>
{

	@Override
	public TaskDefinition mapRow(final ResultSet resultSet, final int index) throws SQLException
	{
		final TaskDefinition taskDef = new TaskDefinition();
		taskDef.setTaskId(resultSet.getLong(TaskDefinitionColumn.ID.getColumnName()));
		taskDef.setTaskType(resultSet.getString(TaskDefinitionColumn.TASK_TYPE.getColumnName()));
		taskDef.setTaskParameters(resultSet.getString(TaskDefinitionColumn.PARAMETERS.getColumnName()));
		taskDef.setActiveFrom(resultSet.getTimestamp(TaskDefinitionColumn.ACTIVE_FROM.getColumnName()));

		taskDef.setLockExpiry(resultSet.getTimestamp(TaskDefinitionColumn.LOCK_EXPIRY.getColumnName()));
		taskDef.setCreated(resultSet.getTimestamp(TaskDefinitionColumn.CREATED.getColumnName()));
		taskDef.setStatusUpdated(resultSet.getTimestamp(TaskDefinitionColumn.STATUS_UPDATED.getColumnName()));
		taskDef.setStatusText(resultSet.getString(TaskDefinitionColumn.STATUS_TEXT.getColumnName()));
		taskDef.setStatus(TaskStatus.valueOf(resultSet.getString(TaskDefinitionColumn.STATUS.getColumnName())));
		taskDef.setIdentifier(resultSet.getString(TaskDefinitionColumn.IDENTIFIER.getColumnName()));

		return taskDef;
	}


	/**
	 *
	 * {@link TaskDefinitionColumn} provides Column definitions for the {@link TaskDefinitionMapper}
	 *
	 */
	public enum TaskDefinitionColumn
	{
		ID("id"), 
		TASK_TYPE("task_type"), 
		PARAMETERS("parameters"), 
		ACTIVE_FROM("active_from"), 
		LOCK_EXPIRY("lock_expiry"), 
		CREATED("created"), 
		STATUS_UPDATED("status_updated"), 
		STATUS("status"), 
		STATUS_TEXT("status_text"), 
		IDENTIFIER("identifier"); 
		private final String columnName;


		TaskDefinitionColumn(final String columnName)
		{
			this.columnName = columnName;
		}


		/**
		 * Returns the value for the field {@link columnName}
		 *
		 * @return the column name
		 */
		public String getColumnName()
		{
			return this.columnName;
		}



	}

}
