package com.farrout.Pong.graphics.ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.farrout.Pong.Layer;
import com.farrout.Pong.Events.Event;

public class UILayer implements Layer {

	public List<UIPanel> panels = new ArrayList<>();
	boolean blockUpdates = false;
	public List<Layer> ownerLayerStack;
	
	public void addPanel(UIPanel c) {
		panels.add(c);
		c.init(this);
	}
	
	public void onRender(Graphics g) {
		for (UIPanel p : panels) {
			p.render(g);
		}
	}

	public void onEvent(Event e) {
		for (UIPanel p: panels) {
			p.onEvent(e);
		}
	}

	public boolean onUpdate() {
		for (UIPanel p : panels) {
			p.update();
		}
		return blockUpdates;
	}

	public void init(List<Layer> l) {
		this.ownerLayerStack = l;
	}

	/*public DrawsTo getRenderObject() {
		return DrawsTo.GRAPHICS;
	}*/

}
