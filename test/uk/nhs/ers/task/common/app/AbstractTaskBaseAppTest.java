package uk.nhs.ers.task.common.app;


import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.Assert;
import org.testng.annotations.Test;

import uk.nhs.ers.task.common.control.TaskServiceController;
import uk.nhs.ers.task.common.mock.MockTaskBaseApp;


public class AbstractTaskBaseAppTest
{
	@Test
	public void testSimple()
	{
		final MockTaskBaseApp app = new MockTaskBaseApp();
		final String contextLocation = MockTaskBaseApp.CONTEXT_LOCATION;
		final String[] applicationContextLocations = new String[] { contextLocation };
		Assert.assertEquals(app.getContextLocation()[0], contextLocation);
		Assert.assertEquals(app.getControllerClass(), TaskServiceController.class);
		final String bean = "mockTaskParameters";
		final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(contextLocation);
		Assert.assertEquals(app.createApplicationContext(applicationContextLocations).getBean(bean),
				context.getBean(bean));
		context.close();
	}
}
