package com.farrout.Pong.Board.BoardElements;

import com.farrout.Pong.graphics.Screen;

public class Line extends Element {
	int x0, y0, x1, y1;
	int width, height;
	int color = 0x222222;
	
	public Line(int x, int y, int width, int height) {
		x0 = x;
		y0 = y;
		x1 = x + width;
		y1 = y + height;
		this.width = width;
		this.height = height;
		
	}
	
	public boolean contains(int x, int y) {
		return ((x0 <= x) && (x < x1)) && ((y0 <= y) && (y < y1));
	}
	
	public void update() {
	}
	
	public void render(Screen screen) {
		screen.fillRect(x0, y0, width, height, color);
	}
	
	public boolean isSolid() {
		return false;
	}
}
