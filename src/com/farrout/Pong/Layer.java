package com.farrout.Pong;

import java.awt.Graphics;
import java.util.List;

import com.farrout.Pong.Events.Event;

public interface Layer {

//	public enum DrawsTo {
//		SCREEN, GRAPHICS
//	}
	
	public void onRender(Graphics g);
	
	public void onEvent(Event e);

	public boolean onUpdate();
	
	public void init(List<Layer> l);
	
//	public DrawsTo getRenderObject();
	
}
