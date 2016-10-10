package com.farrout.Pong.graphics.ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.farrout.Pong.Layer;
import com.farrout.Pong.Events.Event;
import com.farrout.Pong.graphics.Screen;

public class UILayer implements Layer {

	private List<UIPanel> panels = new ArrayList<>();
	
	public void addPanel(UIPanel c) {
		panels.add(c);
		c.init(this);
	}
	
	public void onRender(Screen screen, Graphics g) {
		for (UIPanel p : panels) {
			p.render(g);
		}
	}

	public void onEvent(Event e) {
		
	}

	public void onUpdate() {
		for (UIPanel p : panels) {
			p.update();
		}
	}

	/*public DrawsTo getRenderObject() {
		return DrawsTo.GRAPHICS;
	}*/

}
