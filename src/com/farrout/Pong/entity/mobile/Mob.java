package com.farrout.Pong.entity.mobile;

import com.farrout.Pong.entity.Entity;
import com.farrout.Pong.graphics.Screen;
import com.farrout.Pong.util.Vector2d;
import com.farrout.Pong.util.Vector2i;

public class Mob extends Entity {
	
	Vector2i dimension;
	Vector2d heading;
	protected int color;
	
	public void move(double xa, double ya) {
		
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		
		if (!collision(position.x + xa, position.y + ya)) {
			
		}
		
		position.add(xa, ya);
		
	}
	
	/**
	 * Returns true of a collideable object (entity, wall) is in the way.
	 * @param x coordinate to check
	 * @param y coordinate to check
	 * @return <code>true</code> if the coordinate the object is trying to move into is collideable
	 */
	public boolean collision(double x, double y) {
		
		return false;
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
	
}
