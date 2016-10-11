package com.farrout.Pong.graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.farrout.Pong.Board.Board;
import com.farrout.Pong.entity.mobile.Mob;

public class Screen {

	public int pixels[];
	public int width, height;
	public int scale;
	
	public BufferedImage image;
	public int[] imgPixels;
	
	//TODO there may be offset someday- but by default none of these things will use it, as the screen camera is static. So Im not going to worry.
	
	public Screen(int width, int height, int scale) {
		
		pixels = new int[width * height];
		this.width = width;
		this.height = height;
		this.scale = scale;
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		imgPixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
	}
	
	public void clear(int col) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = col;	//Colors the screen pink wherever we aren't rendering something, as a debug tool
		}
	}

	/**
	 * Draws screen pixels to the image.
	 */
	public void pack() {
		for (int i = 0; i < imgPixels.length; i++) {
			imgPixels[i] = pixels[i];
		}
	}
	
	/**
	 * @deprecated
	 * @param points
	 */
	public void renderBackground(int[] points) {
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = points[i];
		}
		
	}

	public void fillRect(int x, int y, int width, int height, int color) {
		
		for (int yi = 0; yi < height; yi++) {
			
			int yf = yi + y;
			if (yf >= this.height || yf < 0) continue;
			for (int xi = 0; xi < width; xi++) {
				int xf = xi + x;
				if (xf >= this.width || xf < 0) continue;
				pixels[xf + yf * this.width] = color;
			}
		}
		
	}
	
	public void renderPoint(int x, int y, int color) {
		if (x < 0 || x >= this.width) return;
		if (y < 0 || y >= this.height) return;
		pixels[x + y * width] = color;
	}

	public void renderMob(Mob mob) {
		int width = mob.getWidth();
		int height = mob.getHeight();
		int color = mob.getColor();
		int xp = (int) mob.getPosition().x;
		int yp = (int) mob.getPosition().y;
		Board b = mob.board;
		
		for (int y = 0; y < height; y++) {
			
			int yd = y + yp;
			if (yd >= b.height || yd < 0) continue;
			for (int x = 0; x < width; x++) {
				int xd = x + xp;
				if (xd >= this.width || xd < 0) continue;
				pixels[xd + yd * this.width] = color;
			}
		}
		
	}
	
}
