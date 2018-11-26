package com.cmbstnengine.game.events.types;

import com.cmbstnengine.game.events.Event;

public class MousePressedEvent extends MouseButtonEvent {
	
	public MousePressedEvent(int button, int x, int y) {
		super(button, x, y, Event.Type.MOUSE_PRESSED);
	}
	
	
	

}
