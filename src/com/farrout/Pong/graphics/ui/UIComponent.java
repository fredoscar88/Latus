package com.farrout.Pong.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;

import com.farrout.Pong.Events.types.FocusLostEvent;
import com.farrout.Pong.util.Vector2i;

public abstract class UIComponent {

	UIPanel panel;	//Panel that contains this component, can be null

	Color color;
	Vector2i position, size;	//Position (relative to panel) and size
	Vector2i offset;	//Offset from the panel
	
	public UIComponent setColor(Color c) {
		color = c;
		return this;
	}
	
	public UIComponent setColor(int c) {
		if ((c & 0xFF000000) == 0)	//Check if we have alpha
			color = new Color(c, false);	//does not have alpha
		else 
			color = new Color(c, true);		//does have alpha
		return this;
	}
	
	public void init(UIPanel panel) {
		this.panel = panel;
		setOffset(panel.position);
	}
	
	public void setOffset(Vector2i offset) {
		this.offset = offset;
	}
	
	public Vector2i getAbsolutePosition() {
		return Vector2i.add(position, offset);
	}
	
	public void setPosition(Vector2i newPosition) {
		position = newPosition.add(offset);
	}
	
	public abstract void update();
	
	public abstract void render(Graphics g);
	
}
