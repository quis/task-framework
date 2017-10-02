package uk.nhs.ers.task.common.factory;



import java.io.IOException;

import uk.nhs.ers.task.common.task.AbstractTask;
import uk.nhs.ers.task.common.task.ITaskParameters;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * {@link TaskCreationFactory} provides an implementation for creating TaskParameters in a factory
 *
 */
public final class TaskParametersFactory
{
	private TaskParametersFactory()
	{}


	/**
	 * create an ITaskParameters descendant from the given JSON representation based on the TaskParameters class for the
	 * given Task
	 *
	 * @param task
	 * @param json
	 * @return An ITaskParameters object
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static ITaskParameters fromJSON(final AbstractTask task, final String json)
			throws JsonParseException, JsonMappingException, IOException
	{
		final ObjectMapper mapper = new ObjectMapper();
		return (ITaskParameters)mapper.readValue(json, task.getParameterClass());

	}


}
