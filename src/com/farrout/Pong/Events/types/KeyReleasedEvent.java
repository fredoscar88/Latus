package com.farrout.Pong.Events.types;

import com.farrout.Pong.Events.Event;

public class KeyReleasedEvent extends KeyAction {

	public KeyReleasedEvent(int keyCode) {
		super(Event.Type.KEY_RELEASED, keyCode);
	}

}
