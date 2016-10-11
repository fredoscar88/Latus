package com.farrout.Pong.input;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import com.farrout.Pong.Events.EventListener;
import com.farrout.Pong.Events.types.FocusLostEvent;

public class Focus implements FocusListener {

	EventListener listener;
	
	public Focus(EventListener eventListener) {
		listener = eventListener;
	}
	
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void focusLost(FocusEvent e) {
		//nothing to put in the FocusLost constructor
		FocusLostEvent fe = new FocusLostEvent();
		listener.onEvent(fe);
	}

	
	
}
