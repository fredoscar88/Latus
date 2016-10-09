package com.farrout.Pong;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.farrout.Pong.Board.Board;
import com.farrout.Pong.Events.Event;
import com.farrout.Pong.Events.EventListener;
import com.farrout.Pong.graphics.Screen;
import com.farrout.Pong.input.Keyboard;
import com.farrout.Pong.input.Mouse;

public class Game extends Canvas implements Runnable, EventListener {

	private static final long serialVersionUID = 1L;

	public static int width = 300;
	public static int height = 168;
	public static int scale = 3;
	
	private Thread thread;
	private JFrame frame;
	private final static String title = "Pong";
	private boolean running = false;
	
	private Screen screen;
	public List<Layer> layerStack = new ArrayList<>();
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	private Player player1;
	private Player player2;
	private Keyboard key;
	
	public Game() {

		Dimension size = new Dimension(width * scale, height * scale);
		
		screen = new Screen(width, height);
		
		/*TEMP*/
		Board board = new Board(0x000000);
		addLayer(board);
		
		setPreferredSize(size);
		frame = new JFrame();
		
		key = new Keyboard(this);
		addKeyListener(key);
		
		Mouse mouse = new Mouse(this);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);

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
				frame.setTitle("FPS: " + frames + ", UPS: " + updates);
				updates = 0;
				frames = 0;
			}
			
		}
		stop();
	}
	
	public void addLayer(Layer l) {
		layerStack.add(l);
		
	}
	
	public void onEvent(Event event) {
		//events go down the layer stack in reverse order
		for (int i = layerStack.size() - 1; i >= 0; i--) {
			layerStack.get(i).onEvent(event);
		}
	}
	
	private void update() {
		//Updates from top layer to bottom. actually doesn't matter, and Im going to make it from bottom up.
//		for (int i = layerStack.size() - 1; i >= 0; i--) {
//			layerStack.get(i).onUpdate();
//		}
		for (int i = 0; i < layerStack.size(); i++) {
			layerStack.get(i).onUpdate();
		}
	}
	
	private void render() {
		
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;	//quit out for this iteration of rendering
		}
		Graphics g = bs.getDrawGraphics();
		
		screen.clear();
		
		for (int i = 0; i < layerStack.size(); i++) {
			layerStack.get(i).onRender(screen);
		}
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		g.setColor(new Color(0xFF00FF));
		g.fillRect(0, 0, width*scale, height*scale);
		g.drawImage(image, 0, 0, width * scale, height * scale, null);
		
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
