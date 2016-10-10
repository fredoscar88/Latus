package com.farrout.Pong.Board.BoardElements;

import com.farrout.Pong.graphics.Screen;

//The Trump class
public class Wall extends Element {

	int x0, y0, x1, y1;
	int width, height;
	int color = 0x777777;
	
	public Wall(int x, int y, int width, int height) {
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
	
}
