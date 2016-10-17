package com.farrout.Pong.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.farrout.Pong.Events.Event;
import com.farrout.Pong.Events.EventListener;
import com.farrout.Pong.Events.types.KeyPressedEvent;
import com.farrout.Pong.Events.types.KeyReleasedEvent;

public class Keyboard implements KeyListener {

	//May remove this. Plus it MIGHT need to be static.
	public static boolean[] keys = new boolean[120];	//good all around number I guess
	
	EventListener eventListener;
	
	public Keyboard(EventListener listener) {
		eventListener = listener;
	}
	
	
	public static boolean[] getKeysPressed() {
		boolean[] result = new boolean[keys.length];
		for (int i = 0; i < keys.length; i++) {
			result[i] = keys[i];
		}
		return result;
	}
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		Event event = new KeyPressedEvent(e.getKeyCode());
		eventListener.onEvent(event);
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		Event event = new KeyReleasedEvent(e.getKeyCode());
		eventListener.onEvent(event);
	}

	/**
	 * Unused
	 */
	public void keyTyped(KeyEvent e) {
		
	}

}
