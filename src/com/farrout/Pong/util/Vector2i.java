package com.farrout.Pong.util;

public class Vector2i {

	public int x;
	public int y;
	
	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public static Vector2i add(Vector2i v0, Vector2i v1) {
		
		return new Vector2i(v0.x + v1.x, v0.y + v1.y);
		
	}
	
	public Vector2i add(Vector2i v2i) {
		
		return new Vector2i(x + v2i.x, y + v2i.y);
		
	}
	
	public static Vector2i clone(Vector2i vector) {
		
		return new Vector2i(vector.x, vector.y);
	}
	
}
