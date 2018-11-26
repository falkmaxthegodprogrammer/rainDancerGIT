package com.cmbstnengine.game.events.types;

import com.cmbstnengine.game.GamePanel;
import com.cmbstnengine.game.events.Event;

public class MouseMovedEvent extends Event {
	
	private int x, y;
	private boolean dragged;
	
	public MouseMovedEvent(int x, int y, boolean dragged) {
		super(Event.Type.MOUSE_MOVED);
		this.x = x;
		this.y = y;
		this.dragged = dragged;
	}
	
	
	public int getX() {
		if(GamePanel.fullscreen) {
			return x;
		}
		return x;
	}
	
	public int getY() {
		if(GamePanel.fullscreen) {
			return y;
		}
		return y;
	}
	
	public boolean getDragged() {
		return dragged;
	}
	

}
