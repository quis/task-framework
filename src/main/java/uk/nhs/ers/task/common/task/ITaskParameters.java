package uk.nhs.ers.task.common.task;


import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * {@link ITaskParameters} provides an interface for Task Parameters
 *
 */
public interface ITaskParameters
{

	/**
	 * Render this parameters object as a JSON string
	 *
	 * @return Json String representation
	 * @throws JsonProcessingException
	 */
	String toJSON() throws JsonProcessingException;

}
