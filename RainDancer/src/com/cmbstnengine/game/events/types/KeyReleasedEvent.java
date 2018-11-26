package com.cmbstnengine.game.events.types;

import com.cmbstnengine.game.events.Event;
import com.cmbstnengine.game.util.KeyHandler.Key;

public class KeyReleasedEvent extends KeyEvent {
	
	public KeyReleasedEvent(int keyCode) {
		super(keyCode, Event.Type.KEY_RELEASED);
	}
	
}
	
