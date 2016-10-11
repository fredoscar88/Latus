package com.farrout.Pong.Events.types;

import com.farrout.Pong.Events.Event;

public class KeyActionEvent extends Event {

	private int keyCode;
	
	public KeyActionEvent(Event.Type type, int keyCode) {
		super(type);
		this.keyCode = keyCode;
		
	}
	
	public int getKeyCode() {
		return keyCode;
	}
	
}
