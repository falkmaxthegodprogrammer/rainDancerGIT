package com.cmbstnengine.game.states;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.cmbstnengine.game.GamePanel;
import com.cmbstnengine.game.util.KeyHandler;
import com.cmbstnengine.game.util.MouseHandler;
import com.cmbstnengine.game.util.Vector2f;

public class GameStateManager {
	
	private ArrayList<GameState> states;
	
	public static final int PLAY = 0;
	public static final int MENU = 1;
	public static final int PAUSE = 2;
	public static final int GAMEOVER = 3;
	public static final int SETTINGS = 4;
	public static final int DIALOG = 5;
	
	public static Vector2f map;
	
	PlayState playState = new PlayState(this);
		
	public GameStateManager() {
		map = new Vector2f(GamePanel.width, GamePanel.height);
		Vector2f.setWorldVar(map.x, map.y);		
		states = new ArrayList<GameState>();
		states.add(playState);
	}
	
	public void pop(int state) {
		states.remove(state);
	}
	
	public void add(int state) {
		if(state == PLAY) {
			states.add(playState);
		}
		if(state == MENU) {
			states.add(new MenuState(this)); 
		}
		if(state == PAUSE) {
			states.add(new PauseState(this)); 
		}
		if(state == GAMEOVER) {
			states.add(new GameOverState(this)); 
		}		
		if(state == DIALOG) {
			states.add(new DialogState(this));
		}
	}
	
	public void addAndPop(int state) {
		states.remove(0);
		add(state);
	}
	
	public GameState getCurrentState() {
		return states.get(0);
	}
	
	public void update() {
		Vector2f.setWorldVar(map.x, map.y);
		for(int i = 0; i < states.size(); i++) {
			states.get(i).update();
		}
	}
	
	public void input(MouseHandler mouse, KeyHandler key) {
		for(int i = 0; i < states.size(); i++) {
			states.get(i).input(mouse, key);
		}
	}
	
	public void render(Graphics2D g) {
		for(int i = 0; i < states.size(); i++) {
			states.get(i).render(g);
		}
	}
	
}
