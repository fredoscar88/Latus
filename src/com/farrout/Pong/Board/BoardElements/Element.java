package com.farrout.Pong.Board.BoardElements;

import com.farrout.Pong.Board.Board;
import com.farrout.Pong.graphics.Screen;

public abstract class Element {

	Board board;
	int color;
	
	public void init(Board b) {
		board = b;
	}
	
	public abstract boolean isSolid();
	
	public abstract boolean contains(int x, int y);
	
	public abstract void update();
	
	public abstract void render(Screen screen);
}
