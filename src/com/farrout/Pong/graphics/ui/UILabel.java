package com.farrout.Pong.graphics.ui;

import java.awt.Font;
import java.awt.Graphics;

import com.farrout.Pong.util.Vector2i;

public class UILabel extends UIComponent {

	public String text;
	public Font font;
	
	public UILabel (Vector2i position, String text, Font f) {
		this.text = text;
		font = f;
		this.position = position;
	}
	
	public void update() {
		
	}

	public void render(Graphics g) {
		
		//TODO add dropshadow
		
		g.setColor(color);
		g.setFont(font);
		g.drawString(text, position.x + offset.x, position.y + offset.y);
	}

}
