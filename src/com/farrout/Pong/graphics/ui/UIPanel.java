package com.farrout.Pong.graphics.ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.farrout.Pong.Events.Event;
import com.farrout.Pong.Events.EventDispatcher;
import com.farrout.Pong.Events.types.FocusLostEvent;
import com.farrout.Pong.Events.types.KeyPressedEvent;
import com.farrout.Pong.Events.types.MousePressedEvent;
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
	
	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePress((MousePressedEvent) e));
		dispatcher.dispatch(Event.Type.KEY_PRESSED, (Event e) -> onKeyPress((KeyPressedEvent) e));
		dispatcher.dispatch(Event.Type.FOCUS_LOST, (Event e) -> onFocusLost((FocusLostEvent) e));
	}
	
	public boolean onFocusLost(FocusLostEvent e) {
		return false;
	}

	public boolean onKeyPress(KeyPressedEvent e) {
		
		return false;
	}

	public boolean onMousePress(MousePressedEvent e) {
		
		if (new Rectangle(position.x, position.y, size.x, size.y).contains(e.getX(), e.getY())) {
			return true;
		}
		return false;
	}
	
	public void update() {
		
	}

	public void render(Graphics g) {
		
		g.setColor(color);	//right? right! confurmed by TerCherners
		g.fillRect(position.x, position.y, size.x, size.y);
		for (int i = 0; i < components.size(); i++) {
			components.get(i).render(g);
		}
		
	}
	
}
