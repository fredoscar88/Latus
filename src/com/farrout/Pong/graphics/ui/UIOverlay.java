package com.farrout.Pong.graphics.ui;

import com.farrout.Pong.Game;
import com.farrout.Pong.util.Vector2i;

public class UIOverlay extends UIPanel {

//	public static List<UIOverlay> overlays = new ArrayList<>();
	public static UIOverlay currentOverlay = null;
	
	
	
	public UIOverlay() {
		super(new Vector2i(0,0), new Vector2i(Game.gameWidth*Game.scale, Game.gameHeight*Game.scale), 0x7F222222);
		//HAS A BUTTON THAT RESUMES/CLOSES OVERLAY
		//HAS SAME BACKGROUND COLOR
		
	}
	
	
	
}
