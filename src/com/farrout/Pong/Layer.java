package com.farrout.Pong;

import java.awt.Graphics;

import com.farrout.Pong.Events.Event;
import com.farrout.Pong.graphics.Screen;

public interface Layer {

//	public enum DrawsTo {
//		SCREEN, GRAPHICS
//	}
	
	public void onRender(Screen screen, Graphics g);
	
	public void onEvent(Event e);
	
	public void onUpdate();
	
//	public DrawsTo getRenderObject();
	
}
