package com.farrout.Pong.Events;

public class EventDispatcher {

	private Event event;
	
	public EventDispatcher(Event event) {
		this.event = event;
	}
	
	public void dispatch(Event.Type type, EventHandler handler) {
		
		//If a layer further up the stack has already handled the event, dont touch
		if (event.handled)
			return;
		
		//If the type we're trying to run matches, then handle the event
		if (event.getType() == type) {
			event.handled = handler.onEvent(event);	//If handled, no other event subscribers can handle this.
		}
	}
	
}
