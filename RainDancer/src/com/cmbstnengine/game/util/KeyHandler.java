package com.cmbstnengine.game.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import com.cmbstnengine.game.GamePanel;
import com.cmbstnengine.game.events.EventListener;
import com.cmbstnengine.game.events.types.KeyPressedEvent;
import com.cmbstnengine.game.events.types.KeyReleasedEvent;
import com.cmbstnengine.game.events.types.MousePressedEvent;


public class KeyHandler implements KeyListener {
	
	public static List<Key> keys = new ArrayList<Key>();  
	private EventListener eventListener;
	
	public class Key {
		public int presses; 
		public int absorbs = 0;
		public boolean down, clicked;
		
		public Key() {
			keys.add(this);
		}
		
		public void toggle(boolean pressed) {
			if(pressed != down) {
				down = pressed;
			}
			if(pressed) {
				presses++;
			}
		}
			
		public void tick() {
			if(absorbs < presses) {
				absorbs++;
				clicked = true;
			} else {
				clicked = false;
			}
		}

	}	
	
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key attack = new Key();
	public Key menu = new Key();
	public Key enter = new Key();
	public Key escape = new Key();
	public Key shield = new Key();
	public Key pause = new Key();
	public Key interact = new Key();
	public Key spawnEnemies = new Key();
	public Key dialog = new Key();

	public KeyHandler(GamePanel game, EventListener eventListener) {
		game.addKeyListener(this);
		this.eventListener = eventListener;
	}
	
	public void toggle(KeyEvent e, boolean pressed) {
		if(e.getKeyCode() == KeyEvent.VK_W) up.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_UP) up.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_S) down.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_DOWN) down.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_A) left.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_LEFT) left.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_D) right.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) right.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_SPACE) attack.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_E) menu.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_ENTER) enter.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) pause.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_TAB) menu.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_Q) shield.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_E) interact.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_L) spawnEnemies.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_J) dialog.toggle(pressed);
	}

	public void releaseAll() {
		for(int i = 0; i < keys.size(); i++) {
			keys.get(i).down = false;
		}
	}
	
	public void tick() {
		for(int i = 0; i < keys.size(); i++) {
			keys.get(i).tick();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		KeyPressedEvent event = new KeyPressedEvent(e.getKeyCode());
		eventListener.onEvent(event);
		toggle(e, true);	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		KeyReleasedEvent event = new KeyReleasedEvent(e.getKeyCode());
		eventListener.onEvent(event);
		toggle(e, false);	
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing
		
	}
	
}
