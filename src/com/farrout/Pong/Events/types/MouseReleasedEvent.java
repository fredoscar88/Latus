package com.farrout.Pong.Events.types;

import com.farrout.Pong.Events.Event;

public class MouseReleasedEvent extends MouseButtonEvent {

	public MouseReleasedEvent(int button, int x, int y) {
		super(button, x, y, Event.Type.MOUSE_RELEASED);
		
	}

	
}
