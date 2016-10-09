package com.farrout.Pong.graphics.ui;

import java.util.ArrayList;
import java.util.List;

public class UIOverlay extends UIPanel {

	public static List<UIOverlay> overlays = new ArrayList<>();
	public static UIOverlay currentOverlay = null;
	
	
	
	public UIOverlay() {
		super(/*FULL SCREEN PANEL*/);
		
		//last thing that happens
		overlays.add(this);
	}
	
}
