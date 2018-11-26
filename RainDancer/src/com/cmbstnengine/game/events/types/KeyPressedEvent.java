package com.cmbstnengine.game.events.types;

import com.cmbstnengine.game.events.Event;
import com.cmbstnengine.game.util.KeyHandler.Key;

public class KeyPressedEvent extends KeyEvent {
	
	public KeyPressedEvent(int keyCode) {
		super(keyCode, Event.Type.KEY_PRESSED);
	}
	
}
	
	
	
