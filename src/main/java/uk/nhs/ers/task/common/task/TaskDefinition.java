package uk.nhs.ers.task.common.task;


import java.sql.Timestamp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;


/**
 * {@link TaskDefinition} provides a POJO object for defining a task in the database and retrieving it. To define, only
 * taskClass and taskparameters are required. activeFrom defaults to immediate. You can override this.
 *
 * When writing to the database via the ITaskDao, only the taskclass, taskParameters and activeFrom will be written.
 * When reading, all things represent what has been read.
 *
 */
public class TaskDefinition
{
	private Long taskId;
	private String taskType;
	private String taskParameters;
	private Timestamp activeFrom;
	private Timestamp lockExpiry;
	private Timestamp created;
	private Timestamp statusUpdated;
	private TaskStatus status;
	private String statusText;
	private String identifier;

	private static final int PRIME1 = 29;
	private static final int PRIME2 = 37;


	/**
	 * Empty constructor
	 */
	public TaskDefinition()
	{
		
	};


	/**
	 * Create task definition from class name and JSON String of parameters
	 *
	 * @param taskType
	 * @param taskParameters
	 */
	public TaskDefinition(final String taskType, final String taskParameters)
	{
		this.taskType = taskType;
		this.taskParameters = taskParameters;
		this.activeFrom = new Timestamp(0);
	}



	/**
	 * Returns the value for the field {@link taskParameters}
	 *
	 * @return taskParameters
	 */
	public String getTaskParameters()
	{
		return this.taskParameters;
	}


	/**
	 * Sets the value for the field {@link taskParameters}
	 *
	 */
	public void setTaskParameters(final String taskParameters)
	{
		this.taskParameters = taskParameters;
	}


	/**
	 * Returns the value for the field {@link taskType}
	 *
	 * @return taskType
	 */
	public String getTaskType()
	{
		return this.taskType;
	}


	/**
	 * Sets the value for the field {@link taskType}
	 */
	public void setTaskType(final String taskType)
	{
		this.taskType = taskType;
	}


	/**
	 * Returns the value for the field {@link activeFrom}
	 *
	 * @return activeFrom
	 */
	public Timestamp getActiveFrom()
	{
		return cloneTimestamp(this.activeFrom);
	}


	/**
	 * Sets the value for the field {@link activeFrom}
	 */
	public void setActiveFrom(final Timestamp activeFrom)
	{
		this.activeFrom = cloneTimestamp(activeFrom);
	}


	/**
	 * Returns the value for the field {@link taskId}
	 *
	 * @return taskId
	 */
	public Long getTaskId()
	{
		return this.taskId;
	}


	/**
	 * Sets the value for the field {@link taskId}
	 */
	public void setTaskId(final Long taskId)
	{
		this.taskId = taskId;
	}



	@Override
	public String toString()
	{
		return String.format("TaskDefinition: [%s, %s, Params: %s]", getTaskId(), getTaskType(), getTaskParameters());
	}


	/**
	 * Has this task expired
	 *
	 * @return boolean
	 */
	public boolean hasExpired()
	{
		return (this.lockExpiry != null) && this.lockExpiry.before(new Timestamp(DateTime.now().getMillis()));
	}


	/**
	 * Returns the value for the field {@link lockExpiry}
	 *
	 * @return lockExpiry
	 */
	public Timestamp getLockExpiry()
	{
		return cloneTimestamp(this.lockExpiry);
	}


	/**
	 * Sets the value for the field {@link lockExpiry}
	 */
	public void setLockExpiry(final Timestamp lockExpiry)
	{
		this.lockExpiry = cloneTimestamp(lockExpiry);
	}


	@Override
	public int hashCode()
	{


		return new HashCodeBuilder(PRIME1, PRIME2)
				.append(this.taskId)
				.append(this.taskType)
				.append(this.taskParameters)
				.append(this.activeFrom)
				.toHashCode();
	}


	@Override
	public boolean equals(final Object obj)
	{
		boolean equal = false;
		if (obj instanceof TaskDefinition)
		{
			if (obj == this)
			{
				equal = true;
			}
			else
			{
				final TaskDefinition other = (TaskDefinition)obj;
				equal = new EqualsBuilder()
						.append(this.taskId, other.taskId)
						.append(this.taskType, other.taskType)
						.append(this.taskParameters, other.taskParameters)
						.append(this.activeFrom, other.activeFrom)
						.isEquals();
			}
		}


		return equal;
	}


	/**
	 * Returns the value for the field {@link created}
	 *
	 * @return created
	 */
	public Timestamp getCreated()
	{
		return cloneTimestamp(this.created);
	}


	/**
	 * Sets the value for the field {@link created}
	 */
	public void setCreated(final Timestamp created)
	{
		this.created = cloneTimestamp(created);
	}


	/**
	 * Returns the value for the field {@link statusUpdated}
	 *
	 * @return statusUpdated
	 */
	public Timestamp getStatusUpdated()
	{
		return cloneTimestamp(this.statusUpdated);
	}


	/**
	 * Sets the value for the field {@link statusUpdated}
	 */
	public void setStatusUpdated(final Timestamp statusUpdated)
	{
		this.statusUpdated = cloneTimestamp(statusUpdated);
	}


	/**
	 * Returns the value for the field {@link status}
	 *
	 * @return status
	 */
	public TaskStatus getStatus()
	{
		return this.status;
	}


	/**
	 * Sets the value for the field {@link status}
	 */
	public void setStatus(final TaskStatus status)
	{
		this.status = status;
	}


	/**
	 * Returns the value for the field {@link statusText}
	 *
	 * @return statusText
	 */
	public String getStatusText()
	{
		return this.statusText;
	}


	/**
	 * Sets the value for the field {@link statusText}
	 */
	public void setStatusText(final String statusText)
	{
		this.statusText = statusText;
	}


	/**
	 * Returns the value for the field {@link identifier}
	 *
	 * @return identifier
	 */
	public String getIdentifier()
	{
		return this.identifier;
	}


	/**
	 * Sets the value for the field {@link identifier}
	 */
	public void setIdentifier(final String identifier)
	{
		this.identifier = identifier;
	}



	/**
	 * Clones a timestamp, to avoid mutability issue
	 *
	 * @param timestamp
	 * @return a clone of the given Timestamp
	 */
	private Timestamp cloneTimestamp(final Timestamp timestamp)
	{
		return timestamp == null ? null : new Timestamp(timestamp.getTime());
	}


}
