package uk.nhs.ers.task.common.task;


import java.sql.SQLException;
import java.sql.Timestamp;

import org.testng.Assert;
import org.testng.annotations.Test;

import uk.nhs.ers.task.common.mock.MockResultSet;
import uk.nhs.ers.task.common.task.TaskDefinitionMapper.TaskDefinitionColumn;


public class TaskDefinitionMapperTest
{

	@Test
	public void testSimple() throws SQLException
	{
		final String taskClass = "task class";
		final String taskParameters = "task parameters";
		final Timestamp activeFrom = new Timestamp(1000);
		final Long taskId = 15L;
		final Timestamp lockExpiry = new Timestamp(2000);
		final Timestamp created = new Timestamp(3000);
		final String identifier = "task identifier";
		final TaskStatus status = TaskStatus.COMPLETED;
		final String statusText = "status text";
		final Timestamp statusUpdated = new Timestamp(4000);

		final MockResultSet resultSet = new MockResultSet();
		resultSet.addField(TaskDefinitionColumn.ACTIVE_FROM.getColumnName(), activeFrom);
		resultSet.addField(TaskDefinitionColumn.TASK_TYPE.getColumnName(), taskClass);
		resultSet.addField(TaskDefinitionColumn.ID.getColumnName(), taskId);
		resultSet.addField(TaskDefinitionColumn.LOCK_EXPIRY.getColumnName(), lockExpiry);
		resultSet.addField(TaskDefinitionColumn.PARAMETERS.getColumnName(), taskParameters);
		resultSet.addField(TaskDefinitionColumn.CREATED.getColumnName(), created);
		resultSet.addField(TaskDefinitionColumn.IDENTIFIER.getColumnName(), identifier);
		resultSet.addField(TaskDefinitionColumn.STATUS.getColumnName(), status.name());
		resultSet.addField(TaskDefinitionColumn.STATUS_TEXT.getColumnName(), statusText);
		resultSet.addField(TaskDefinitionColumn.STATUS_UPDATED.getColumnName(), statusUpdated);


		final TaskDefinitionMapper mapper = new TaskDefinitionMapper();
		final TaskDefinition taskDef = mapper.mapRow(resultSet, 0);

		Assert.assertEquals(taskDef.getTaskType(), taskClass);
		Assert.assertEquals(taskDef.getTaskParameters(), taskParameters);
		Assert.assertEquals(taskDef.getActiveFrom(), activeFrom);
		Assert.assertEquals(taskDef.getTaskId(), taskId);
		Assert.assertEquals(taskDef.getLockExpiry(), lockExpiry);
		Assert.assertEquals(taskDef.getIdentifier(), identifier);
		Assert.assertEquals(taskDef.getStatusText(), statusText);
		Assert.assertEquals(taskDef.getStatus(), status);
		Assert.assertEquals(taskDef.getStatusUpdated(), statusUpdated);
		Assert.assertEquals(taskDef.getCreated(), created);
	}


	@Test
	public void testEnum()
	{
		Assert.assertEquals(TaskDefinitionColumn.valueOf("ACTIVE_FROM"), TaskDefinitionColumn.ACTIVE_FROM);
	}
}
