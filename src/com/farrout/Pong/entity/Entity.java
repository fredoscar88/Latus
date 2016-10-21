package com.farrout.Pong.entity;

import com.farrout.Pong.Board.Board;
import com.farrout.Pong.graphics.Screen;
import com.farrout.Pong.graphics.ui.UIPanel;
import com.farrout.Pong.util.Vector2d;

public class Entity {

	//Visibility subject to change
	protected Vector2d position;
	protected Vector2d size;
	public Board board;
	protected boolean removed = false;
	protected UIPanel topPanel;
	
	//It may be that we want position to essentially be public, but I do not think so.
	public Vector2d getPosition() {
		return Vector2d.clone(position);
	}
	
	
	
	public void remove() {
		removed = true;
	}

	public void update() {
	}
	
	public void render(Screen screen) {
	}
	
	public void init(Board b, UIPanel panel) {
		board = b;
		this.topPanel = panel;
	}

	public boolean isRemoved() {
		return removed;
	}

}
