package com.cmbstnengine.game.states;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.cmbstnengine.game.GamePanel;
import com.cmbstnengine.game.Window;
import com.cmbstnengine.game.events.Event;
import com.cmbstnengine.game.events.EventDispatcher;
import com.cmbstnengine.game.events.types.KeyPressedEvent;
import com.cmbstnengine.game.events.types.KeyReleasedEvent;
import com.cmbstnengine.game.events.types.MousePressedEvent;
import com.cmbstnengine.game.events.types.MouseReleasedEvent;
import com.cmbstnengine.game.graphics.GFont;
import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.ui.components.GActionListener;
import com.cmbstnengine.game.ui.components.GButton;
import com.cmbstnengine.game.ui.components.GPanel;
import com.cmbstnengine.game.ui.components.GUIManager;
import com.cmbstnengine.game.util.KeyHandler;
import com.cmbstnengine.game.util.MouseHandler;
import com.cmbstnengine.game.util.Vector2f;
import com.cmbstnengine.game.util.Vector2i;
import com.cmbstnengine.game.GameLauncher;

public class PauseState extends GameState {

	public static Window window;
	
	private GUIManager gui;
	private GPanel buttonPanel;
	private GButton[] buttons;
	private Color defaultColor = new Color(66, 134, 244);		
	private GFont font;
		
	public PauseState(GameStateManager gsm) {
		super(gsm);
		font = new GFont("font/font.png", 10, 10);
		buttonPanel = new GPanel(new Vector2i(GamePanel.width / 4, GamePanel.height / 4), new Vector2i(GamePanel.width / 2, 330));
		buttonPanel.setColor(defaultColor);
		buttonPanel.showBorder(false);
		
		gui = new GUIManager();
		gui.addPanel(buttonPanel);
		
		buttons = new GButton[12];
		
		for(int i = 0; i < 9; i ++) {
			buttons[i] = new GButton(new Vector2i(GamePanel.width / 4, GamePanel.height / 4 + (i * 40)), new Vector2i(GamePanel.width / 2, 30));
			buttonPanel.addComponent(buttons[i]);
			buttons[i].setDefaultColor(defaultColor);
		}
						
		buttons[0].addActionListener(new GActionListener() {
			public void actionPerformed() {
				window.toggleFullscreen();
			}
		});
				
		buttons[8].addActionListener(new GActionListener() {
			public void actionPerformed() {
				System.exit(0);
			}
		});
		
	}

	@Override
	public void update() {		
		gui.update();
	}

	@Override
	public void input(MouseHandler mouse, KeyHandler key) {
	}

	@Override
	public void render(Graphics2D g) {
		g.setStroke(new BasicStroke(3));

		g.setColor(new Color(66, 134, 244));
		g.fillRect(0, 0, GamePanel.width, GamePanel.height);
		
		String gamePaused = "GAME PAUSED";
		Sprite.drawArray(g, font, gamePaused, new Vector2f(GamePanel.width / 2 - gamePaused.length() * 16, GamePanel.height / 32), 32, 32);
		
		String settings = "SETTINGS";
		Sprite.drawArray(g, font, settings, new Vector2f(GamePanel.width / 2 - settings.length() * 16, GamePanel.height / 6), 32, 32);

		g.setColor(Color.BLACK);	
			
		String masterVolume = "MASTER VOLUME";
		Sprite.drawArray(g, font, masterVolume, new Vector2f(GamePanel.width / 2 - masterVolume.length() * 10, GamePanel.height / 4 + 286), 20, 20);
	
		gui.render(g);
		
		String fullScreenToggle = "TOGGLE FULLSCREEN";
		Sprite.drawArray(g, font, fullScreenToggle, new Vector2f(GamePanel.width / 2 - fullScreenToggle.length() * 10, GamePanel.height / 4 + 6), 20, 20);
	
		String exit = "EXIT TO MAIN MENU";
		Sprite.drawArray(g, font, exit, new Vector2f(GamePanel.width / 2 - exit.length() * 10, GamePanel.height / 4 + 326), 20, 20);
	
	}

	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePressed((MousePressedEvent) e));
		dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> onMouseReleased((MouseReleasedEvent) e));	
		dispatcher.dispatch(Event.Type.KEY_PRESSED, (Event e) -> onKeyPressed((KeyPressedEvent) e));
		dispatcher.dispatch(Event.Type.KEY_RELEASED, (Event e) -> onKeyReleased((KeyReleasedEvent) e));
	}
	
	public boolean onMousePressed(MousePressedEvent e) {
		return false;
	}
	
	public boolean onMouseReleased(MouseReleasedEvent e) {
		return false;
	}
	
	public boolean onKeyPressed(KeyPressedEvent e) {

		return false;
	}
	
	public boolean onKeyReleased(KeyReleasedEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_P || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			GamePanel.getGsm().addAndPop(0);
			return true;
		}
		return false;
	}
	


}
