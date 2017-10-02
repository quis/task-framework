package uk.nhs.ers.task.common.task;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


/**
 * {@link AbstractTaskParameters} provides the basic implementation of a Taskparameters object
 *
 */
public abstract class AbstractTaskParameters implements ITaskParameters
{

	@Override
	public String toJSON() throws JsonProcessingException
	{
		final ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		return mapper.writeValueAsString(this);
	}


}
