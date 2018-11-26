package com.cmbstnengine.game.states;

import java.awt.Graphics2D;

import com.cmbstnengine.game.events.EventListener;
import com.cmbstnengine.game.util.KeyHandler;
import com.cmbstnengine.game.util.MouseHandler;

public abstract class GameState implements EventListener {
	
	private GameStateManager gsm;
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
	}
	
	public abstract void update();
	public abstract void input(MouseHandler mouse, KeyHandler key);
	public abstract void render(Graphics2D g);
	
}
