package com.cmbstnengine.game.events.types;

import com.cmbstnengine.game.events.Event;

public class MouseWheelMovedEvent extends Event {

	protected int rotation;
	protected boolean up;
	
	public MouseWheelMovedEvent(int rotation) {
		super(Event.Type.MOUSEWHEEL);
		this.rotation = rotation;
		determineDirection();
	}
	
	public void determineDirection() {
		if(this.rotation < 0) {
			this.up = true;
		} else {
			this.up = false;
		}
	}
	
	public boolean movedUp() {
		return up;
	}

	
	
	
}
