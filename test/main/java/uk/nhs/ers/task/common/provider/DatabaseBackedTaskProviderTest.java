package uk.nhs.ers.task.common.provider;


import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import uk.nhs.ers.task.common.mock.MockTaskDao;
import uk.nhs.ers.task.common.task.TaskDefinition;


public class DatabaseBackedTaskProviderTest
{
	@Mock
	DataSource mockDataSource;


	@Test
	public void testAddRetrieveTasks()
	{
		final MockTaskDao taskDao = new MockTaskDao();
		final DatabaseBackedTaskProvider provider = new DatabaseBackedTaskProvider(taskDao);
		final List<TaskDefinition> taskDefs = getTaskDefList();
		provider.queueTasks(taskDefs);
		final List<TaskDefinition> fetchedTasks = provider.obtainTasks();
		Assert.assertTrue(fetchedTasks.containsAll(taskDefs), "Not all taskDefs in fetchedTasks");
		Assert.assertTrue(taskDefs.containsAll(fetchedTasks), "Not all fetchedTasks in taskDefs");
	}


	@Test
	public void testDao()
	{
		final MockTaskDao taskDao = new MockTaskDao();
		final DatabaseBackedTaskProvider provider = new DatabaseBackedTaskProvider(taskDao);
		Assert.assertEquals(provider.getTaskDao(), taskDao);
	}


	@Test
	public void testDatasource()
	{
		final DatabaseBackedTaskProvider provider = new DatabaseBackedTaskProvider(this.mockDataSource);
		Assert.assertEquals(provider.getTaskDao().getTemplate().getDataSource(), this.mockDataSource);
	}


	/**
	 *
	 * @return
	 */
	private List<TaskDefinition> getTaskDefList()
	{
		final List<TaskDefinition> taskDefs = new ArrayList<TaskDefinition>();
		taskDefs.add(new TaskDefinition("One", "Two"));
		taskDefs.add(new TaskDefinition("Three", "Four"));
		taskDefs.add(new TaskDefinition("Five", "Six"));
		return taskDefs;
	}


	@BeforeClass
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
	}
}
