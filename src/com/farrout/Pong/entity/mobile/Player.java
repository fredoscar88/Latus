package com.farrout.Pong.entity.mobile;

import com.farrout.Pong.input.KeyMap;
import com.farrout.Pong.input.KeyMap.Value;

public class Player extends Paddle {

	KeyMap keyMap;
	
	public Player(int x, int y, int color, KeyMap keyMap) {
		super(x, y, color);
		this.keyMap = keyMap;
	}
	
	private void move(int xa, int ya) {
		//I'm not implementing collision detection that will stop you if you hit a wall, not right now. TODO add collision detection for paddles on walls/other
//		if (position.y > 4 && (position.y + height) <= board.height - 4) {
//			
//		}
		if (!collide(xa, ya)) super.move(xa, ya);
		//I may need some goddamn collision tho.
	}
	
	private boolean collide(int xa, int ya) {
		boolean colliding = false;
		
		if (board.elemCollide((int) position.x + xa, (int) position.y + ya)) colliding = true;
		if (board.elemCollide((int) position.x + width + xa, (int) position.y + height + ya - 1)) colliding = true;
		return colliding;
	}
	
	public void update() {
		super.update();
		keyMap.update();
		if (keyMap.getValue() == Value.UP) move(0, -1);
		if (keyMap.getValue() == Value.DOWN) move(0, 1);
		
	}
	
}
