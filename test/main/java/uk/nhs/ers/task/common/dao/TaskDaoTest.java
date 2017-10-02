package uk.nhs.ers.task.common.dao;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import uk.nhs.ers.task.common.exception.TaskCreationException;
import uk.nhs.ers.task.common.mock.MockTask;
import uk.nhs.ers.task.common.task.ITask;
import uk.nhs.ers.task.common.task.TaskDefinition;
import uk.nhs.ers.task.common.task.TaskStatus;


public class TaskDaoTest
{
	@Mock
	DataSource mockDataSource;
	@Mock
	JdbcTemplate mockJdbcTemplate;
	@Mock
	PreparedStatement statement;


	@Test
	public void testSetDataSource()
	{
		final ITaskDao dao = new TaskDao(this.mockDataSource);
		Assert.assertEquals(dao.getTemplate().getDataSource(), this.mockDataSource);
	}


	@Test
	public void testSetJdbcTemplate()
	{
		final ITaskDao dao = new TaskDao(this.mockJdbcTemplate);
		Assert.assertEquals(dao.getTemplate(), this.mockJdbcTemplate);
	}



	@Test
	public void testAddTasks()
	{
		final ITaskDao dao = new TaskDao(this.mockJdbcTemplate);
		final List<TaskDefinition> tasks = getTasks();
		for (final TaskDefinition taskDef : tasks)
		{
			dao.addTask(taskDef);
		}
	}


	@Test
	public void testBatchStatement() throws SQLException
	{
		final List<TaskDefinition> tasks = getTasks();
		final TaskDao.TaskInsertBatchPreparedStatementSetter setter =
				new TaskDao.TaskInsertBatchPreparedStatementSetter(tasks);

		Assert.assertEquals(setter.getBatchSize(), tasks.size());

		setter.setValues(this.statement, 0);

		Mockito.verify(this.statement).setString(1, tasks.get(0).getTaskType());
		Mockito.verify(this.statement).setString(2, tasks.get(0).getTaskParameters());
		Mockito.verify(this.statement).setTimestamp(3, tasks.get(0).getActiveFrom());
	}


	@Test
	public void testUpdateStatus() throws TaskCreationException
	{
		final ITaskDao dao = new TaskDao(this.mockJdbcTemplate);

		final String identifier = "526jknmg)fdgs";
		final ITask task = new MockTask();
		task.setTaskDef(new TaskDefinition("task class", "task parameters"));

		task.setIdentifier(identifier);
		dao.updateStatus(task, TaskStatus.COMPLETED, "status note");
		Assert.assertEquals(task.getTaskDef().getIdentifier(), identifier);
	}


	private List<TaskDefinition> getTasks()
	{
		final List<TaskDefinition> tasks = new ArrayList<TaskDefinition>();
		tasks.add(new TaskDefinition("class1", "parameters1"));
		tasks.add(new TaskDefinition("class2", "parameters2"));
		return tasks;
	}


	@BeforeMethod
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
	}
}
