package uk.nhs.ers.task.common.mock;


import java.util.ArrayList;
import java.util.List;

import uk.nhs.ers.task.common.service.IServiceEventHandler;
import uk.nhs.ers.task.common.service.ServiceEvent;
import uk.nhs.ers.task.common.service.ServiceEventType;


public class MockServiceEventHandler implements IServiceEventHandler
{
	private final List<ServiceEventType> events = new ArrayList<ServiceEventType>();


	@Override
	public void handleServiceEvent(final ServiceEvent event)
	{
		this.events.add(event.getEventType());
	}


	public boolean eventOccurred(final ServiceEventType eventType)
	{
		return this.events.contains(eventType);
	}


	public boolean eventOccurred(final ServiceEventType eventType, final int index)
	{
		return this.events.get(index) == eventType;
	}


	public void clearEvents()
	{
		this.events.clear();
	}
}
