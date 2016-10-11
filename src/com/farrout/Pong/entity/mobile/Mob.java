package com.farrout.Pong.entity.mobile;

import com.farrout.Pong.entity.Entity;
import com.farrout.Pong.graphics.Screen;
import com.farrout.Pong.util.MathUtils;
import com.farrout.Pong.util.Vector2d;
import com.farrout.Pong.util.Vector2i;

public class Mob extends Entity {
	
	Vector2i dimension;
	Vector2d heading;
	protected int color;
	
	public enum BounceType {
		LEFT,
		RIGHT,
		UP,
		DOWN
	}
	
	public void move(double xa, double ya) {
		
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		
		position.add(xa, ya);
		
	}
	
	public double bounceAngle(double dim, BounceType type) {
		
		double xhalf = dimension.x/2;
		double yhalf = dimension.y/2;
		switch (type) {
			case LEFT: {
				System.out.println("LEFT");
				dim -= position.y;
				dim = Math.abs(yhalf - dim);	//should be between 0 and 1, inclusive
				return (dim * (MathUtils.RAD75 - -MathUtils.RAD75) + -MathUtils.RAD75) + Math.PI;
			}
			case RIGHT: {
				System.out.println("RIGHT");
				dim -= position.y;
				dim = Math.abs(yhalf - dim);	//should be between 0 and 1, inclusive
				return (dim * (MathUtils.RAD75 - -MathUtils.RAD75) + -MathUtils.RAD75);
			}
			case UP: {
				System.out.println("UP");
				dim -= position.x;
				dim = Math.abs(xhalf - dim);	//should be between 0 and 1, inclusive
				return (dim * (-MathUtils.RAD15 + Math.PI - MathUtils.RAD15) + MathUtils.RAD285);
			}
			case DOWN: {
				System.out.println("DOWN");
				dim -= position.x;
				dim = Math.abs(xhalf - dim);	//should be between 0 and 1, inclusive
				return (dim * (-MathUtils.RAD15 + Math.PI - MathUtils.RAD15) + MathUtils.RAD285) + Math.PI;
			}
		}
		return 0;
	}
	
	public int getWidth() {
		return dimension.x;
	}
	
	public int getHeight() {
		return dimension.y;
	}
	
	public int getColor() {
		return color;
	}
	
	public Vector2d nextPosition() {
		return Vector2d.add(position, heading);
	}
	
	public void render(Screen screen) {
		
	}

	public boolean contains(double x, double y) {
		/*System.out.println((x >= position.x && x < (position.x + dimension.x) && y >= position.y && y < (position.y + dimension.y)) + "\n" + 
				"x " + x + ", position.x " + position.x + " " + (position.x + dimension.x) + ", y " + y + ", position.y " + position.y + " " + (position.y + dimension.y));*/
		return (x >= position.x && x < (position.x + dimension.x) && y >= position.y && y < (position.y + dimension.y));
		//((x0 <= x) && (x < x1)) && ((y0 <= y) && (y < y1));
	}
	
}
