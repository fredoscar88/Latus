package com.farrout.Pong.Board;

import java.awt.Font;
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
import com.farrout.Pong.Events.types.FocusLostEvent;
import com.farrout.Pong.Events.types.KeyPressedEvent;
import com.farrout.Pong.Events.types.KeyReleasedEvent;
import com.farrout.Pong.Events.types.MouseMovedEvent;
import com.farrout.Pong.Events.types.MousePressedEvent;
import com.farrout.Pong.entity.Entity;
import com.farrout.Pong.entity.mobile.Ball;
import com.farrout.Pong.entity.mobile.Mob;
import com.farrout.Pong.entity.mobile.Paddle;
import com.farrout.Pong.entity.mobile.Player;
import com.farrout.Pong.graphics.Screen;
import com.farrout.Pong.graphics.ui.UILabel;
import com.farrout.Pong.graphics.ui.UILayer;
import com.farrout.Pong.graphics.ui.UIPanel;
import com.farrout.Pong.input.KeyMap;
import com.farrout.Pong.util.Vector2i;

public class Board implements Layer {

	public int x, y;
	public int width, height;
	public int scale;
	private int backgroundColor;
	public Screen screen;
	
	private List<Entity> entities = new ArrayList<>();
	private List<Element> elements = new ArrayList<>();
	private List<Board> boards;
	
	@Deprecated
	private boolean paused = false;
	boolean blockUpdates;
	Random random = new Random();
	
	Player p1;
	Player p2;
	KeyMap k1 = new KeyMap(KeyEvent.VK_W, KeyEvent.VK_S);
	KeyMap k2 = new KeyMap(KeyEvent.VK_UP, KeyEvent.VK_DOWN);
	
	private boolean[] keysPressed = new boolean[120];
	private List<Layer> ownerLayerStack;
	
	//Board doesnt have to be the size of the screen, note. It likely won't be larger but it can definitely be smaller.
	public Board(int x, int y, int width, int height, int scale, int backgroundColor, List<Layer> layerStack) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.backgroundColor = backgroundColor;
		
		init(layerStack);
		screen = new Screen(width, height, scale);
	}
	
	public void loadBoards() {
		//Load boards from file TODO
	}
	
	public void addLayer(Layer l) {
		ownerLayerStack.add(l);
		l.init(ownerLayerStack);
	}
	
	public void addUI() {
		UILayer menu = new UILayer();
		menu.addPanel(new UIPanel(new Vector2i(0,0), new Vector2i(Game.gameWidth, 60)) {
			
			public boolean onFocusLost(FocusLostEvent e) {
				
				addLayer(Game.pauseLayer);
				return true;
			}
			
			public boolean onKeyPress(KeyPressedEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_P) {
					addLayer(Game.pauseLayer);
					return true;
				}
				return false;
			}
			
		});
		addLayer(menu);
		menu.panels.get(0).addComponent(new UILabel(new Vector2i(10,40), "Pong", new Font("Helvetica",Font.BOLD, 32)).setColor(0xFFFFFF));
		
		//TODO change the addUI method. A board component shouldnt be managing UI that isnt part of the board! It's part of the Game as a whole, yes, but not the board.
		addLayer(Game.startLayer);
		
	}
	
	/**
	 * returns a new angle of travel based on which side of an entity you collided on
	 * @param x
	 * @param y
	 * @return
	 * @throws Exception 
	 */
	public double entCollide(double x0, double y0, double theta, Mob mm) {
		
		
		for (Entity e : entities) {
			if (e instanceof Mob) {
				Mob m = (Mob) e;
				if (m == mm) {
					continue;
				}
				//if (m.contains(x, y)) {
//					System.out.println("x current " + x0 + ", y current " + y0 + ",");
					if (m.contains(x0, y0)) System.out.println("BROKEN");
					if (m.contains(x0 + 1, y0)) return m.bounceAngle(x0+1, Mob.BounceType.LEFT);
					if (m.contains(x0 + 1, y0)) return m.bounceAngle(x0-1, Mob.BounceType.RIGHT);
					if (m.contains(x0, y0 + 1)) return m.bounceAngle(y0+1, Mob.BounceType.UP);
					if (m.contains(x0, y0 + 1)) return m.bounceAngle(y0-1, Mob.BounceType.DOWN);
					
				//}
			}
		}
		return theta;
	}
	
	public boolean entCollide(int x, int y, Mob mob) {
		
		for (Entity m : entities) {
			if (m instanceof Paddle && m != mob) {
				if (((Mob) m).contains(x, y)) {
					
					return true;
				}
			}
		}
		return false;
	}
	public boolean entCollide(int x, int y) {
		
		for (Entity m : entities) {
//			System.out.println(x + ", " + y);
//			System.out.println(m + ", x: " + m.getPosition().x + ", y: " + m.getPosition().y );
			if (((Mob) m).contains(x, y)) {
				
				return true;
			}
		}
		return false;
	}
	
	public Paddle paddleAt(int x, int y) {
		
		for (Entity m : entities) {
//			System.out.println(x + ", " + y);
//			System.out.println(m + ", x: " + m.getPosition().x + ", y: " + m.getPosition().y );
			if (m instanceof Paddle && ((Mob) m).contains(x, y)) {
				
				return (Paddle) m;
			}
		}
		return null;
	}
	
	public boolean elemCollide(int x, int y) {
		
		for (Element e : elements) {
			if (e.contains(x, y) && e.isSolid()) {
				return true;
			}
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
	
	public void removeAllBalls() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i) instanceof Ball) {
				entities.remove(i);
				i--;
			}
		}
	}
	
	public boolean onUpdate() {

		if (p1 == null) {
			p1 = new Player(6, 50, Paddle.DEF_COLOR, k1);
			this.addEntity(p1);
		}
		if (p2 == null) {
			p2 = new Player(300 - 11, 50, Paddle.DEF_COLOR, k2);
			this.addEntity(p2);
		}
		
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (!e.isRemoved()) {
				e.update();
			}
		}
		for (int i = 0; i < elements.size(); i++) {
			elements.get(i).update();
		}
		
		//Even while paused, we can clean up some entities
		removeEntities();
		return blockUpdates;	//shouldnt ever be true, board is the last layer to even receive updates.
	}
	
	public void onRender(Graphics g) {
		
		screen.clear(backgroundColor);
		screen.fillRect(100 * 3, 100 * 3, 5*3, 5*3, 0xFFFFFF);
		
		for (int i = 0; i < elements.size(); i++) {
			elements.get(i).render(screen);
		}
	
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (!e.isRemoved()) 
				e.render(screen);
		}
		
		screen.pack();
		g.drawImage(screen.image, x, y, width * scale, height * scale, null);
				
	}

	public void onEvent(Event event) {
		
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePress((MousePressedEvent) e));
		dispatcher.dispatch(Event.Type.MOUSE_MOVED, (Event e) -> onMouseMove((MouseMovedEvent) e));
		dispatcher.dispatch(Event.Type.KEY_PRESSED, (Event e) -> onKeyPress((KeyPressedEvent) e));
		dispatcher.dispatch(Event.Type.KEY_RELEASED, (Event e) -> onKeyRelease((KeyReleasedEvent) e));
	}
	
	private boolean onKeyPress(KeyPressedEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_R) {
			removeAllBalls();
			return true;
		}
		if (e.getKeyCode() == KeyEvent.VK_T) {
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
//			addEntity(new Ball(e.getX() / scale, e.getY() / scale - y/scale, random.nextInt(0xFFFFFF), random.nextDouble() * 2*Math.PI));		
//			addEntity(new Ball(e.getX() / 3, e.getY() / 3 - y/3, random.nextInt(0xFFFFFF), 3*Math.PI / 2, Ball.maxSpeed));		
			int mousex = e.getX() / 3;
			int mousey = e.getY() / 3 - 20;
			
			System.out.println("Paddle? " + entCollide(e.getX() / 3, e.getY() / 3 - 20));
			Paddle p = paddleAt(mousex, mousey);
			
			if (p != null) System.out.println("Angle of bounce: " + Math.toDegrees(p.bounceOffAngle(mousex, mousey)));
			return true;	//Did we handle the event?
		}
		if (e.getButton() == MouseEvent.BUTTON2) {
			addEntity(new Ball(e.getX() / 3, e.getY() / 3 - y/3, random.nextInt(0xFFFFFF), 3 * Math.PI / 2, Ball.maxSpeed));
			return true;
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			addEntity(new Paddle(e.getX() / scale, e.getY() / scale - y/scale, 0xFFFFFF));		
//			addEntity(new Ball(e.getX() / 3, e.getY() / 3 - Game.screenY/3, random.nextInt(0xFFFFFF), Math.PI / 4));		
			return true;	//Did we handle the event?
		}
		return false;
	}

	public void init(List<Layer> l) {
		this.ownerLayerStack = l;
	}
	
}
