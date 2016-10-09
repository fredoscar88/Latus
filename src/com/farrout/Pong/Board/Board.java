package com.farrout.Pong.Board;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.farrout.Pong.Game;
import com.farrout.Pong.Layer;
import com.farrout.Pong.Events.Event;
import com.farrout.Pong.Events.EventDispatcher;
import com.farrout.Pong.Events.types.KeyPressedEvent;
import com.farrout.Pong.Events.types.KeyReleasedEvent;
import com.farrout.Pong.Events.types.MouseMovedEvent;
import com.farrout.Pong.Events.types.MousePressedEvent;
import com.farrout.Pong.graphics.Screen;
import com.farrout.Pong.graphics.ui.UIOverlay;
import com.farrout.Pong.input.Mouse;

public class Board implements Layer {

	private int points[] = new int[Game.width * Game.height];	//board width and height are uniform- for now. With the level editor I might let players change that.
	
	public Board(int backgroundColor) {
		setBackground(backgroundColor);
	}
	
	public void setPointColor(int x, int y, int col, boolean mouseCoord) {
		
		if (!mouseCoord) points[x + y * Game.width] = col;
		else points[(x / Game.scale) + y / Game.scale * Game.width] = col;
	}
	
	public void setBackground(int col) {
		for (int i = 0; i < points.length; i++) {
			points[i] = col;
		}
	}
	
	public void onRender(Screen screen) {
		
		screen.renderBackground(points);
		
	}

	public void onEvent(Event event) {
		
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePress((MousePressedEvent) e));
		dispatcher.dispatch(Event.Type.MOUSE_MOVED, (Event e) -> onMouseMove((MouseMovedEvent) e));
		dispatcher.dispatch(Event.Type.KEY_PRESSED, (Event e) -> onKeyPress((KeyPressedEvent) e));
		dispatcher.dispatch(Event.Type.KEY_RELEASED, (Event e) -> onKeyRelease((KeyReleasedEvent) e));
	}
	
	private boolean[] keysPressed = new boolean[120];
	private boolean onKeyPress(KeyPressedEvent e) {
		if (!keysPressed[e.getKeyCode()]) {
			keysPressed[e.getKeyCode()] = true;
			
			if (e.getKeyCode() == KeyEvent.VK_SPACE)
				setBackground(0);
		}
		return true;
	}

	private boolean onKeyRelease(KeyReleasedEvent e) {
		if (keysPressed[e.getKeyCode()]) {
			keysPressed[e.getKeyCode()] = false;
		}
		return true;
	}

	private boolean onMouseMove(MouseMovedEvent e) {
		if (e.getDragged()) {
			switch (Mouse.getButton()) {
			case MouseEvent.BUTTON1: setPointColor(e.getX(), e.getY(), 0x00FF00, true); break;
			case MouseEvent.BUTTON2: setPointColor(e.getX(), e.getY(), 0xFF0000, true); break;
			case MouseEvent.BUTTON3: setPointColor(e.getX(), e.getY(), 0x0000FF, true); break;
			default: setPointColor(e.getX(), e.getY(), 0x00FF00, true);
			}
			return true;
		}
		return false;
	}

	private boolean onMousePress(MousePressedEvent e) {
		
		switch (e.getButton()) {
		case MouseEvent.BUTTON1: setPointColor(e.getX(), e.getY(), 0x00FF00, true); break;
		case MouseEvent.BUTTON2: setPointColor(e.getX(), e.getY(), 0xFF0000, true); break;
		case MouseEvent.BUTTON3: setPointColor(e.getX(), e.getY(), 0x0000FF, true); break;
		default: setPointColor(e.getX(), e.getY(), 0x00FF00, true);
		}
		return true;	//Did we handle the event?
	}

	public void onUpdate() {
		// TODO Auto-generated method stub
		
		if (UIOverlay.currentOverlay == null)
			return;	//The board is not allowed to update when an overlay is open
		
	}

}
