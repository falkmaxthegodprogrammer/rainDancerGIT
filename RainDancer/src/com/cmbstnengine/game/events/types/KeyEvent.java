package com.cmbstnengine.game.events.types;

import com.cmbstnengine.game.events.Event;
import com.cmbstnengine.game.util.KeyHandler.Key;

public class KeyEvent extends Event {
	
	protected int keyCode;
	
	protected KeyEvent(int keyCode, Type type) {
		super(type);
		this.keyCode = keyCode;
	}
	
	public int getKeyCode() {
		return keyCode;
	}
	
}
	
