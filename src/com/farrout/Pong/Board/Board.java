package com.farrout.Pong.Board;

import java.util.ArrayList;
import java.util.List;

import com.farrout.Pong.Layer;
import com.farrout.Pong.Board.BoardElements.Element;
import com.farrout.Pong.Events.Event;
import com.farrout.Pong.Events.EventDispatcher;
import com.farrout.Pong.Events.types.KeyPressedEvent;
import com.farrout.Pong.Events.types.KeyReleasedEvent;
import com.farrout.Pong.Events.types.MouseMovedEvent;
import com.farrout.Pong.Events.types.MousePressedEvent;
import com.farrout.Pong.entity.Entity;
import com.farrout.Pong.graphics.Screen;
import com.farrout.Pong.graphics.ui.UIOverlay;

public class Board implements Layer {

	public int width, height;
	private int backgroundColor;
	private List<Entity> entities = new ArrayList<>();
	private List<Element> elements = new ArrayList<>();
	
	private boolean[] keysPressed = new boolean[120];
	
	//Board doesnt have to be the size of the screen, note. It likely won't be larger but it can definitely be smaller.
	public Board(int width, int height, int backgroundColor) {
		this.width = width;
		this.height = height;
		this.backgroundColor = backgroundColor;
		
	}
	public Board(int width, int height) {
		this(width, height, 0);
	}
	
	public boolean elemCollide(int x, int y) {
		
		for (Element e : elements) {
			if (e.contains(x, y)) return true;
		}
		return false;
		
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
		e.init(this);
	}
	
	public void addElement(Element e) {
		elements.add(e);
		e.init(this);
	}
	
	public void removeEntities() {
		for (int i = 0; i < entities.size(); i++) {
			
			if (entities.get(i).isRemoved()) {
				entities.remove(i);
			}
		}
	}
	
	public void onUpdate() {
		if (UIOverlay.currentOverlay != null)
			return;	//The board is not allowed to update when an overlay is open
		

		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (!e.isRemoved()) {
				e.update();
			}
		}
		
		for (int i = 0; i < elements.size(); i++) {
			elements.get(i).update();
		}
		
	}
	
	public void onRender(Screen screen) {
		
		screen.fillRect(0,0,width,height,backgroundColor);
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
		
		for (int i = 0; i < elements.size(); i++) {
			elements.get(i).render(screen);
		}
		
	}

	public void onEvent(Event event) {
		
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePress((MousePressedEvent) e));
		dispatcher.dispatch(Event.Type.MOUSE_MOVED, (Event e) -> onMouseMove((MouseMovedEvent) e));
		dispatcher.dispatch(Event.Type.KEY_PRESSED, (Event e) -> onKeyPress((KeyPressedEvent) e));
		dispatcher.dispatch(Event.Type.KEY_RELEASED, (Event e) -> onKeyRelease((KeyReleasedEvent) e));
	}
	
	private boolean onKeyPress(KeyPressedEvent e) {
		
		return false;
	}

	private boolean onKeyRelease(KeyReleasedEvent e) {
		
		return false;
	}

	private boolean onMouseMove(MouseMovedEvent e) {
		
		return false;
	}

	private boolean onMousePress(MousePressedEvent e) {
		
		return false;	//Did we handle the event?
	}

}
