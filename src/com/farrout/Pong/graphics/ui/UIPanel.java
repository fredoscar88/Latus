package com.farrout.Pong.graphics.ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.farrout.Pong.util.Vector2i;

public class UIPanel extends UIComponent {

	UILayer layer;
	List<UIComponent> components = new ArrayList<>();

	public UIPanel(Vector2i position, Vector2i size) {
		this(position, size, 0);
	}
	
	public UIPanel(Vector2i position, Vector2i size, int color) {
		this.position = position;
		this.size = size;
		this.setColor(color);
	}
	
	public void addComponent(UIComponent c) {
		components.add(c);
		c.setOffset(position);
		c.init(this);
	}
	
	/**
	 * Adds a panel to top level UI Layer.
	 * Special note: UIPanels can be initialized with the general UIComponent.init(UIPanel panel), if we add the panel to another panel.
	 * @param layer
	 */
	public void init(UILayer layer) {
		this.layer = layer;
	}
	
	public void update() {
		System.out.println("Panel updated " + this + " " + Integer.toHexString(color.getRGB()));
	}

	public void render(Graphics g) {
		g.setColor(color);	//right? right! confurmed by TerCherners
		g.fillRect(position.x, position.y, size.x, size.y);
		
	}
	
}
