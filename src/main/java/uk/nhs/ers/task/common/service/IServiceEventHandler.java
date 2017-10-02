package uk.nhs.ers.task.common.service;

/**
 * Handler of service events.
 */
public interface IServiceEventHandler
{
	/**
	 * Handle the given service event.
	 *
	 * @param event service event
	 */
	void handleServiceEvent(ServiceEvent event);
}
