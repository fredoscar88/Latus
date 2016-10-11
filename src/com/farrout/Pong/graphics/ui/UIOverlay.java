package com.farrout.Pong.graphics.ui;

import java.awt.event.KeyEvent;

import com.farrout.Pong.Game;
import com.farrout.Pong.Events.Event;
import com.farrout.Pong.Events.EventDispatcher;
import com.farrout.Pong.Events.types.FocusLostEvent;
import com.farrout.Pong.Events.types.KeyPressedEvent;
import com.farrout.Pong.Events.types.MousePressedEvent;
import com.farrout.Pong.util.Vector2i;

public class UIOverlay extends UIPanel {

//	public static List<UIOverlay> overlays = new ArrayList<>();
	public static UIOverlay currentOverlay = null;
	
	
	public UIOverlay() {
		super(new Vector2i(0,0), new Vector2i(Game.gameWidth, Game.gameHeight), 0xAF222222);
		//HAS A BUTTON THAT RESUMES/CLOSES OVERLAY
		//HAS SAME BACKGROUND COLOR
		
	}
	public UIOverlay(int color) {
		super(new Vector2i(0,0), new Vector2i(Game.gameWidth, Game.gameHeight), color);
		//HAS A BUTTON THAT RESUMES/CLOSES OVERLAY
		//HAS SAME BACKGROUND COLOR
		
	}
	
	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		
		dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePress((MousePressedEvent) e));
		dispatcher.dispatch(Event.Type.KEY_PRESSED, (Event e) -> onKeyPress((KeyPressedEvent) e));
		dispatcher.dispatch(Event.Type.FOCUS_LOST, (Event e) -> onFocusLost((FocusLostEvent) e));
	}
	
	public boolean onFocusLost(FocusLostEvent e) {
		return true;
	}
	
	public boolean onKeyPress(KeyPressedEvent e) {
//		if (e.getKeyCode() == KeyEvent.VK_P) {
//			layer.ownerLayerStack.remove(layer);
//		}
		return true;
	}
	
	public void update() {
		layer.blockUpdates = true;	//Overlays always block updates to the rest of the layers, as they demand the users attention
	}
	
}
