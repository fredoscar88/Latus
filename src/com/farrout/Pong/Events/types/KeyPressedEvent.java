package com.farrout.Pong.Events.types;

import com.farrout.Pong.Events.Event;

//Note that we could have done this in the style of the Mice, i.e have a KeyAction event and KeyPress and KeyRelease just extend that. In fact..
public class KeyPressedEvent extends KeyActionEvent {

	public KeyPressedEvent(int keyCode) {
		super(Event.Type.KEY_PRESSED, keyCode);

	}

	
	
}
