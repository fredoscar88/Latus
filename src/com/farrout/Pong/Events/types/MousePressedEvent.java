package com.farrout.Pong.Events.types;

import com.farrout.Pong.Events.Event;

public class MousePressedEvent extends MouseButtonEvent {

	public MousePressedEvent(int button, int x, int y) {
		super(button, x, y, Event.Type.MOUSE_PRESSED);
		
	}

}
