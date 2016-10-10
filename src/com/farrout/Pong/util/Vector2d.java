package com.farrout.Pong.util;

public class Vector2d {
	public double x;
	public double y;
	
	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}
		
	public Vector2d add(Vector2d vector) {
		
		x += vector.x;
		y += vector.y;
		return new Vector2d(x, y);
		
	}
	
	public static Vector2d add(Vector2d v0, Vector2d v1) {
		
		return new Vector2d(v0.x + v1.x, v0.y + v1.y);
		
	}
	
	public static Vector2d clone(Vector2d vector) {
		
		return new Vector2d(vector.x, vector.y);
	}

	public void add(double xa, double ya) {
		x += xa;
		y += ya;
	}
}
