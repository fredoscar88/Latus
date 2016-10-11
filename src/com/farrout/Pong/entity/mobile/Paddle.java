package com.farrout.Pong.entity.mobile;

import com.farrout.Pong.graphics.Screen;
import com.farrout.Pong.util.Vector2d;
import com.farrout.Pong.util.Vector2i;

public class Paddle extends Mob {

	private double nx, ny;
	private double speed = 1;
	private int width = 25;
	private int height = 25;
	
	public Paddle(int x, int y, int color) {
		this.color = color;
		position = new Vector2d(x, y);
		dimension = new Vector2i(width, height);
		heading = new Vector2d(0,0);
	}
	
	public void render(Screen screen) {
		screen.renderMob(this);
	}
	
	public void update() {
//		System.out.println(position.x + " " + dimension.x + " " + (position.x + dimension.x));
	}
	
}
