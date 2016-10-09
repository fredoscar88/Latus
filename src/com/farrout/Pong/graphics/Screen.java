package com.farrout.Pong.graphics;

public class Screen {

	public int pixels[];
	public int width, height;
	
	public Screen(int width, int height) {
		
		pixels = new int[width * height];
		this.width = width;
		this.height = height;
		
	}
	
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void renderBackground(int[] points) {
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = points[i];
		}
		
	}

	
}
