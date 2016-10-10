package com.farrout.Pong.Board;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.farrout.Pong.Game;
import com.farrout.Pong.Layer;
import com.farrout.Pong.Board.BoardElements.Element;
import com.farrout.Pong.Events.Event;
import com.farrout.Pong.Events.EventDispatcher;
import com.farrout.Pong.Events.types.KeyPressedEvent;
import com.farrout.Pong.Events.types.KeyReleasedEvent;
import com.farrout.Pong.Events.types.MouseMovedEvent;
import com.farrout.Pong.Events.types.MousePressedEvent;
import com.farrout.Pong.entity.Entity;
import com.farrout.Pong.entity.mobile.Ball;
import com.farrout.Pong.graphics.Screen;

public class Board implements Layer {

	public int width, height;
	private int backgroundColor;
	private List<Entity> entities = new ArrayList<>();
	private List<Element> elements = new ArrayList<>();
	private List<Board> boards;
	
	private boolean paused = false;
	Random random = new Random();
	
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
	
	/*public DrawsTo getRenderObject() {
		return DrawsTo.SCREEN;
	}*/
	
	public void loadBoards() {
		//Load boards from file TODO
	}
	
	public void pause() {
		paused = true;
		//TODO Open paused overlay, add it to the stack
	}
	public void unpause() {
		paused = false;
		//TODO Close paused overlay (this may happen in the overlay itself)
	}
	
	public boolean elemCollide(int x, int y) {
		
		for (Element e : elements) {
			if (e.contains(x, y) && e.isSolid()) return true;
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
				i--;	//Not strictly necessary to decrement i, but I think it's more efficient (if we run out the size of the list some entities have to wauit till the next cycle)
			}
		}
	}
	
	public void removeAllEntities() {
		for (int i = 0; i < entities.size(); i++) {
			entities.remove(i);
			i--;	//Not strictly necessary to decrement i, but I think it's more efficient (if we run out the size of the list some entities have to wauit till the next cycle)
		}
	}
	
	public void onUpdate() {
//		if (UIOverlay.currentOverlay != null)
//			paused = true;	//The board is not allowed to update when an overlay is open
		
		if (!paused) {
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
		//Even while paused, we can clean up some entities
		removeEntities();
	}
	
	public void onRender(Screen screen, Graphics g) {
		
		screen.fillRect(0,0,width,height,backgroundColor);
		
		for (int i = 0; i < elements.size(); i++) {
			elements.get(i).render(screen);
		}

		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (!e.isRemoved()) 
				e.render(screen);
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
		
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			paused = !paused;
			return true;
		}
		if (e.getKeyCode() == KeyEvent.VK_R) {
			removeAllEntities();
			return true;
		}
		return false;
	}

	private boolean onKeyRelease(KeyReleasedEvent e) {
		
		return false;
	}

	private boolean onMouseMove(MouseMovedEvent e) {
		
		return false;
	}

	private boolean onMousePress(MousePressedEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			addEntity(new Ball(e.getX() / 3, e.getY() / 3 - Game.screenY/3, random.nextInt(0xFFFFFF), random.nextDouble() * 2*Math.PI));		
//			addEntity(new Ball(e.getX() / 3, e.getY() / 3 - Game.screenY/3, random.nextInt(0xFFFFFF), Math.PI / 4));		
			return true;	//Did we handle the event?
		}
		return false;
	}
	
}
