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
	public UILabel (Vector2i position, String text) {
		this.text = text;
		font = new Font("Times New Roman", Font.PLAIN, 30);
		this.position = position;
	}
	
	public void setText(String text) {
		this.text = text;
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
