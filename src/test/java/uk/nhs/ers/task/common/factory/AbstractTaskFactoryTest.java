package uk.nhs.ers.task.common.factory;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.context.support.GenericApplicationContext;
import org.testng.Assert;
import org.testng.annotations.Test;

import uk.nhs.ers.task.common.exception.TaskCreationException;
import uk.nhs.ers.task.common.mapper.ClassNameTaskMapper;
import uk.nhs.ers.task.common.mock.MockNoConstructorTask;
import uk.nhs.ers.task.common.mock.MockProperties;
import uk.nhs.ers.task.common.mock.MockTask;
import uk.nhs.ers.task.common.mock.MockTaskDao;
import uk.nhs.ers.task.common.mock.MockTaskFactory;
import uk.nhs.ers.task.common.mock.MockTaskParameters;
import uk.nhs.ers.task.common.mock.MockTestingTaskFactory;
import uk.nhs.ers.task.common.provider.DatabaseBackedTaskProvider;
import uk.nhs.ers.task.common.task.AbstractTask;
import uk.nhs.ers.task.common.task.ITask;
import uk.nhs.ers.task.common.task.TaskDefinition;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


public class AbstractTaskFactoryTest
{
	@Test
	public void testGets()
	{
		final MockTaskDao mockTaskDao = new MockTaskDao();
		final ClassNameTaskMapper taskMapper = new ClassNameTaskMapper();
		final MockTaskFactory factory =
				new MockTaskFactory(mockTaskDao, new DatabaseBackedTaskProvider(mockTaskDao), new MockProperties(),
						taskMapper);
		Assert.assertEquals(factory.getTaskManagerForTesting().getClass().getSimpleName(), "TaskManager");
		Assert.assertEquals(factory.getTaskMapperForTest(), taskMapper);
	}


	@Test
	public void testCreate() throws IOException, TaskCreationException
	{
		final String param1 = "Param1";
		final Long param2 = 2L;
		final Timestamp param3 = new Timestamp(0);
		final MockTaskParameters taskParameters = new MockTaskParameters(param1, param2, param3);
		final String encoded = taskParameters.toJSON();
		final TaskDefinition taskDef = new TaskDefinition(MockTask.class.getName(), encoded);
		final MockTaskDao mockTaskDao = new MockTaskDao();
		final MockTaskFactory factory =
				new MockTaskFactory(mockTaskDao, new DatabaseBackedTaskProvider(mockTaskDao), new MockProperties(),
						new ClassNameTaskMapper());
		final ITask task = factory.create(taskDef);

		Assert.assertEquals(task.encodeParameters(), encoded);
		Assert.assertTrue(task instanceof MockTask, "Should be a MockTask");
	}


	@Test
	public void testCreateWithoutIdentifier() throws IOException, TaskCreationException
	{
		final String param1 = "Param1";
		final Long param2 = 2L;
		final Timestamp param3 = new Timestamp(0);

		final MockTaskParameters taskParameters = new MockTaskParameters(param1, param2, param3);
		final String encoded = taskParameters.toJSON();


		final TaskDefinition taskDef = new TaskDefinition(MockTask.class.getName(), encoded);
		taskDef.setIdentifier(null);

		final MockTaskDao mockTaskDao = new MockTaskDao();
		final MockTaskFactory factory =
				new MockTaskFactory(mockTaskDao, new DatabaseBackedTaskProvider(mockTaskDao), new MockProperties(),
						new ClassNameTaskMapper());
		final ITask task = factory.create(taskDef);

		Assert.assertNotNull(task.getIdentifier());
	}


	@Test
	public void testCreateWithIdentifier() throws IOException, TaskCreationException
	{
		final String param1 = "Param1";
		final Long param2 = 2L;
		final Timestamp param3 = new Timestamp(0);
		final String identifier = "asdgjn fsad;lkjgf;kl";

		final MockTaskParameters taskParameters = new MockTaskParameters(param1, param2, param3);
		final String encoded = taskParameters.toJSON();


		final TaskDefinition taskDef = new TaskDefinition(MockTask.class.getName(), encoded);

		taskDef.setIdentifier(identifier);

		final MockTaskDao mockTaskDao = new MockTaskDao();
		final MockTaskFactory factory =
				new MockTaskFactory(mockTaskDao, new DatabaseBackedTaskProvider(mockTaskDao), new MockProperties(),
						new ClassNameTaskMapper());
		final ITask task = factory.create(taskDef);

		Assert.assertEquals(task.getIdentifier(), identifier);
	}


	@Test(expectedExceptions = { TaskCreationException.class })
	public void testBadCreateClassInvalid() throws JsonProcessingException, TaskCreationException
	{
		final String param1 = "Param1";
		final Long param2 = 2L;
		final Timestamp param3 = new Timestamp(0);

		final MockTaskParameters taskParameters = new MockTaskParameters(param1, param2, param3);
		final String encoded = taskParameters.toJSON();


		final MockTaskDao mockTaskDao = new MockTaskDao();
		final MockTaskFactory factory =
				new MockTaskFactory(mockTaskDao, new DatabaseBackedTaskProvider(mockTaskDao), new MockProperties(),
						new ClassNameTaskMapper());

		factory.createForTesting(new TaskDefinition("Banana", encoded));


	}


	@Test(expectedExceptions = { TaskCreationException.class })
	public void testBadCreateClassAbstract() throws JsonProcessingException, TaskCreationException
	{
		final String param1 = "Param1";
		final Long param2 = 2L;
		final Timestamp param3 = new Timestamp(0);

		final MockTaskParameters taskParameters = new MockTaskParameters(param1, param2, param3);
		final String encoded = taskParameters.toJSON();



		final MockTaskDao mockTaskDao = new MockTaskDao();
		final MockTaskFactory factory =
				new MockTaskFactory(mockTaskDao, new DatabaseBackedTaskProvider(mockTaskDao), new MockProperties(),
						new ClassNameTaskMapper());

		factory.createForTesting(new TaskDefinition(AbstractTask.class.getName(), encoded));

	}


	@Test(expectedExceptions = { TaskCreationException.class })
	public void testBadCreateParams() throws TaskCreationException
	{

		final String encoded = "I'm a monkey";


		final MockTaskDao mockTaskDao = new MockTaskDao();
		final MockTaskFactory factory =
				new MockTaskFactory(mockTaskDao, new DatabaseBackedTaskProvider(mockTaskDao), new MockProperties(),
						new ClassNameTaskMapper());

		factory.createForTesting(new TaskDefinition(AbstractTask.class.getName(), encoded));


	}


	@Test(expectedExceptions = { TaskCreationException.class })
	public void testBadCreateClassNoConstructor() throws TaskCreationException
	{


		final TaskDefinition taskDef = new TaskDefinition(MockNoConstructorTask.class.getName(), "Banana");

		final MockTaskDao mockTaskDao = new MockTaskDao();
		final MockTaskFactory factory =
				new MockTaskFactory(mockTaskDao, new DatabaseBackedTaskProvider(mockTaskDao), new MockProperties(),
						new ClassNameTaskMapper());
		factory.create(taskDef);

	}


	@Test
	public void testActions() throws InterruptedException
	{
		final MockTaskDao mockTaskDao = new MockTaskDao();
		final MockTaskFactory factory =
				new MockTaskFactory(mockTaskDao, new DatabaseBackedTaskProvider(mockTaskDao), new MockProperties(),
						new ClassNameTaskMapper());


		Assert.assertFalse(factory.getTaskExecutor().isShutdown());

		final MockRunnable immediateTask = new MockRunnable(0);
		final MockRunnable delayTask = new MockRunnable(2000);


		factory.getTaskExecutor().submit(immediateTask);


		Thread.sleep(100);
		Assert.assertTrue(immediateTask.getHasRun().get(), "Task should have run");

		immediateTask.reset();
		Assert.assertFalse(immediateTask.getHasRun().get(), "Task should have been reset");

		factory.pause(100);


		factory.getTaskExecutor().submit(delayTask);
		final Future<?> secondTaskRun = factory.getTaskExecutor().submit(immediateTask);


		Thread.sleep(100);
		Assert.assertFalse(immediateTask.getHasRun().get(), "Task should not have run yet");

		factory.stop();


		Thread.sleep(2000);
		Assert.assertFalse(immediateTask.getHasRun().get(), "Task should not have run");
		Thread.sleep(100);
		Assert.assertFalse(secondTaskRun.isDone(), "Second task should have been cancelled");



	}



	class MockRunnable implements Runnable
	{
		private final int delay;
		private final AtomicBoolean hasRun = new AtomicBoolean(false);


		/**
		 * Returns the value for the field {@link hasRun}
		 */
		public AtomicBoolean getHasRun()
		{
			return this.hasRun;
		}


		public MockRunnable(final int delay)
		{
			this.delay = delay;
		}


		@Override
		public void run()
		{
			try
			{
				Thread.sleep(this.delay);
				this.hasRun.set(true);

			}
			catch (final InterruptedException e)
			{
				Assert.fail("Interrupted");
			}

		}


		/**
		 * reset the hasrun flag
		 */
		public void reset()
		{
			this.hasRun.set(false);
		}

	}



	@Test
	public void testProtected()
	{
		final MockTaskDao mockTaskDao = new MockTaskDao();
		final MockProperties properties = new MockProperties();
		final DatabaseBackedTaskProvider taskProvider = new DatabaseBackedTaskProvider(mockTaskDao);
		final MockTestingTaskFactory factory = new MockTestingTaskFactory(mockTaskDao,
				taskProvider, properties, new ClassNameTaskMapper());

		Assert.assertEquals(factory.getTaskProviderForTest(), taskProvider);
		Assert.assertEquals(factory.getProperties(), properties);
		factory.getTaskQueueForTest();

	}


	@Test
	public void testRun() throws TaskCreationException, InterruptedException
	{
		final MockTaskDao mockTaskDao = new MockTaskDao();
		final MockProperties properties = new MockProperties();
		final DatabaseBackedTaskProvider taskProvider = new DatabaseBackedTaskProvider(mockTaskDao);
		final MockTestingTaskFactory factory = new MockTestingTaskFactory(mockTaskDao,
				taskProvider, properties, new ClassNameTaskMapper());

		factory.newTask();
		Thread.sleep(1000);
		Assert.assertTrue(factory.hasRun(), "Expected the TaskManager to have run");
	}


	@Test
	public void testApplicationContext()
	{
		final MockTaskDao mockTaskDao = new MockTaskDao();
		final MockProperties properties = new MockProperties();
		final DatabaseBackedTaskProvider taskProvider = new DatabaseBackedTaskProvider(mockTaskDao);
		final MockTestingTaskFactory factory = new MockTestingTaskFactory(mockTaskDao,
				taskProvider, properties, new ClassNameTaskMapper());


		final GenericApplicationContext context = new GenericApplicationContext();

		factory.setApplicationContext(context);

		Assert.assertEquals(factory.getApplicationContext(), context);

	}


	@Test
	public void testExtractDefinition()
			throws JsonParseException, JsonMappingException, JsonProcessingException, IOException
	{
		final MockTaskDao mockTaskDao = new MockTaskDao();
		final MockProperties properties = new MockProperties();
		final DatabaseBackedTaskProvider taskProvider = new DatabaseBackedTaskProvider(mockTaskDao);
		final MockTestingTaskFactory factory = new MockTestingTaskFactory(mockTaskDao,
				taskProvider, properties, new ClassNameTaskMapper());

		final MockTask task = new MockTask();
		final String params = (new MockTaskParameters("1", 2L, new Timestamp(3)).toJSON());
		task.parseParameters(params);
		final TaskDefinition taskDef = factory.extractDefinition(task);
		Assert.assertEquals(taskDef.getTaskParameters(), params);
	}
}
