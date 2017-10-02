package uk.nhs.ers.task.common.service;

/**
 * Service Events.
 *
 */
public final class ServiceEvent
{
	private final String serviceName;
	private final ServiceEventType eventType;


	/**
	 * Construct a service event for the given service and type of event.
	 *
	 * @param serviceName name of the service
	 * @param eventType type of event
	 */
	public ServiceEvent(final String serviceName, final ServiceEventType eventType)
	{
		this.serviceName = serviceName;
		this.eventType = eventType;
	}


	/**
	 * Obtain the name of the service.
	 *
	 * @return name of the service
	 */
	public String getServiceName()
	{
		return this.serviceName;
	}


	/**
	 * Obtain the type of service event.
	 *
	 * @return service event type
	 */
	public ServiceEventType getEventType()
	{
		return this.eventType;
	}
}
