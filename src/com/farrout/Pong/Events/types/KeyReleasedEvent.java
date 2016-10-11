package com.farrout.Pong.Events.types;

import com.farrout.Pong.Events.Event;

public class KeyReleasedEvent extends KeyActionEvent {

	public KeyReleasedEvent(int keyCode) {
		super(Event.Type.KEY_RELEASED, keyCode);
	}

}
