package com.farrout.Pong;

import com.farrout.Pong.Events.Event;
import com.farrout.Pong.graphics.Screen;

public interface Layer {

	public void onRender(Screen screen);
	
	public void onEvent(Event e);
	
	public void onUpdate();
	
}
