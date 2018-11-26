package com.cmbstnengine.game.events;

public abstract class Event {
	
	boolean handled;
	
	public enum Type {
		MOUSE_PRESSED,
		MOUSE_RELEASED,
		MOUSE_MOVED,
		KEY_PRESSED,
		KEY_RELEASED,
		MOUSEWHEEL,
		INTERACT
	}
	
	protected Event(Type type) {
		this.type = type;
	}
	
	private Type type;
	
	public Type getType() {
		return type;
	}
	
	

}
