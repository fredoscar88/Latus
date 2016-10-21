package com.farrout.Pong;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.farrout.Pong.Board.Board;
import com.farrout.Pong.Board.BoardElements.Line;
import com.farrout.Pong.Board.BoardElements.Wall;
import com.farrout.Pong.Events.Event;
import com.farrout.Pong.Events.EventListener;
import com.farrout.Pong.Events.types.KeyPressedEvent;
import com.farrout.Pong.entity.mobile.Ball;
import com.farrout.Pong.graphics.ui.UILabel;
import com.farrout.Pong.graphics.ui.UILayer;
import com.farrout.Pong.graphics.ui.UIOverlay;
import com.farrout.Pong.input.Focus;
import com.farrout.Pong.input.Keyboard;
import com.farrout.Pong.input.Mouse;
import com.farrout.Pong.util.MathUtils;
import com.farrout.Pong.util.Vector2i;

//TODO http://libgdx.badlogicgames.com/

public class Game extends Canvas implements Runnable, EventListener {

	private static final long serialVersionUID = 1L;

	//Our own display port width/height
	public static int gameWidth = 300 * 3;
	public static int gameHeight = 188 * 3;
//	public static int scale = 3;
//	public static int width = gameWidth - screenX/scale;	//Screen width, height
//	public static int height = gameHeight - screenY/scale;
	
	private Thread thread;
	private JFrame frame;
	private final static String title = "Latus- Made by Henry Farr";
	private boolean running = false;
	private boolean debug = true;
	private boolean debugUpdate = false;
	
	public List<Layer> layerStack = new ArrayList<>();
	
	private Keyboard key;
	
	Random random = new Random();
	
	public static UILayer pauseLayer;
	public static UILayer startLayer;
	
	public Game() {

		Dimension size = new Dimension(gameWidth, gameHeight);
		
		//Screen implementation here is really poor, Im treating it like a static object when it doesnt make sense TODO learn this lesson for next project
//		screen = new Screen(width, height);
		
		pauseLayer = new UILayer();
		UIOverlay pauseOverlay = new UIOverlay() {
			public boolean onKeyPress(KeyPressedEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_P) {
					pauseLayer.ownerLayerStack.remove(pauseLayer);
				}
				return true;
			}
		};
		pauseLayer.addPanel(pauseOverlay);
		pauseOverlay.addComponent(new UILabel(new Vector2i(10,100), "PAUSED", new Font("Helvetica",Font.BOLD, 32)).setColor(0xFF0000));
		pauseOverlay.init(pauseLayer);
		
		/*TEMP*/
		//These constants will be based on game width and height. Frankly we don't need the scale in this Game class
		Board board = new Board(0, 60, 300, 168, 3, 0x0F0F00, layerStack);
		addLayer(board);
		
		startLayer = new UILayer();
		UIOverlay startOverlay = new UIOverlay(0x7F000000) {
			public boolean onKeyPress(KeyPressedEvent e) {
				startLayer.ownerLayerStack.remove(startLayer);
				random.nextInt(15);
				board.addEntity(new Ball(board.width/2 - Ball.size / 2,board.height/2 - Ball.size / 2, 0x88CC88, (MathUtils.randDouble(Math.PI / 4, -Math.PI / 4) - (random.nextBoolean() ? Math.PI : 0))));
//				board.addEntity(new Paddle(board.width - 150, board.height - 120, 0xCC8888));
				return true;
			}
		};
		startLayer.addPanel(startOverlay);
		startOverlay.addComponent(new UILabel(new Vector2i(10,100), "Press any key to start", new Font("Helvetica",Font.BOLD, 32)).setColor(0x00FF00));
		startOverlay.init(startLayer);	//TODO implement LayerManager
		
		//Probably not the best way to add UI, from an OO perspective
		board.addUI();
		
		board.addElement(new Wall(0, 0, board.width, 3));
		board.addElement(new Wall(0, board.height-3, board.width, 3));
		board.addElement(new Wall(0, 3, 3, board.height - 3));
		board.addElement(new Wall(board.width - 3, 3, 3, board.height - 3));
		board.addElement(new Line(board.width / 2 - 2, 3, 4, board.height-6));
//		board.addElement(new Wall(5, 5, 3, 3));
		
		setPreferredSize(size);
		frame = new JFrame();
		
		key = new Keyboard(this);
		addKeyListener(key);
		
		Mouse mouse = new Mouse(this);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
		addFocusListener(new Focus(this));

	}
	
	public synchronized void start() {
		running = true;
		
		thread = new Thread(this, "game");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
	}
	
	@Override
	public void run() {
		
		final double ups = 60.0;
		final double upsRatio = 1000000000/ups;
		long lastTime = System.nanoTime();
		long currentTime;
		double delta = 0;
		
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		
		requestFocus();
		
		while (running) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / upsRatio;	//every 1/ups of a second delta >= 1
			lastTime = currentTime;
			while(delta >= 1) {
				update();
				updates++;
				delta--;
			}
			
			
			render();
			frames++;
			
			while (System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				frame.setTitle(title + " DEBUG: FPS: " + frames + ", UPS: " + updates);
				updates = 0;
				frames = 0;
			}
			
		}
		stop();
	}
	
	public void addLayer(Layer l) {
		layerStack.add(l);
		l.init(layerStack);
		
	}
	public void removeLayer(Layer l) {
		layerStack.remove(l);
	}
	
	public void onEvent(Event event) {

		if (event.getType() == Event.Type.KEY_PRESSED) {
			if (((KeyPressedEvent) event).getKeyCode() == KeyEvent.VK_D) {
				debugUpdate = true;
				return;
			}
		}
		if (event.getType() == Event.Type.KEY_PRESSED) {
			if (((KeyPressedEvent) event).getKeyCode() == KeyEvent.VK_C) {
				debug = !debug;
				return;
			}
		}
		
		//events go down the layer stack in reverse order
		for (int i = layerStack.size() - 1; i >= 0; i--) {
			layerStack.get(i).onEvent(event);
		}
	}
	
	private void update() {
		
		if (debug && !debugUpdate) {
			return;
		}
		debugUpdate = false;
		
		//Updates from top layer to bottom. actually doesn't matter, and Im going to make it from bottom up.
		//TODO make update use events, i.e call an update event and send it to layers, so layers can pause other layers if they want
		//TODO figure out if that's what we really want to do.
		for (int i = layerStack.size() - 1; i >= 0; i--) {
			if (layerStack.get(i).onUpdate() == true) break;
		}
		
		//onEvent(new Event(Event.Type.UPDATE));
	}
	
	private void render() {
		
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;	//quit out for this iteration of rendering
		}
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(new Color(0xFF00FF));
		g.fillRect(0, 0, gameWidth, gameHeight);

		//This a bad way to handle screens, since it's not really a static thing. This screen represents our board, so shouldn't board own screen?
		//	likewise, we can have multiple screens. TODO reorganize the implementation of screen.
		//	I think ViewArea would be a better name, too. Think about it, we should be able to draw up multiple "screens".
//		screen.clear();
		
		for (int i = 0; i < layerStack.size(); i++) {
			//LAYERS ONLY USE ONE OF SCREEN, OR G. NEVER BOTH. This tells me they are separate layers- meaning I should split the interface up into two different
			//	render types. Alternatively, I structure this so that screen extends graphics... which would still let it draw stuff. BUt for now this oughta work
			layerStack.get(i).onRender(g);
			
		}
		
//		for (int i = 0; i < pixels.length; i++) {
//			pixels[i] = screen.pixels[i];
//		}
		
//		g.drawImage(image, screenX, screenY, width * scale, height * scale, null);	//Draw our screen view port
		
//		g.setFont(new Font("Helvetica", Font.BOLD, 72));
//		g.drawString("Pong", screen.width*3/2 - 90, screen.height*3/2 + 80);
		
		bs.show();
		g.dispose();
		
	}
	
	public static void main(String[] args) {

		Game game = new Game();
		
		game.frame.add(game);
		game.frame.setTitle(title);
		game.frame.setResizable(false);
		game.frame.pack();
		game.frame.setVisible(true);
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		
		game.start();
		
	}

}
