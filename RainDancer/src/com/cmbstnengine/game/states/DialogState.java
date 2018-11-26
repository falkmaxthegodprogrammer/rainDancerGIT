package com.cmbstnengine.game.states;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.cmbstnengine.game.GamePanel;
import com.cmbstnengine.game.events.Event;
import com.cmbstnengine.game.graphics.GFont;
import com.cmbstnengine.game.util.KeyHandler;
import com.cmbstnengine.game.util.MouseHandler;

public class DialogState extends GameState {

	private BufferedImage img;
	
	public DialogState(GameStateManager gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
	//	img = GamePanel.getLastFrame();
	
	}


	@Override
	public void update() {

	}

	@Override
	public void input(MouseHandler mouse, KeyHandler key) {
		if(key.up.down || key.down.down || key.left.down || key.right.down) {
			GamePanel.getGsm().addAndPop(0);
		}
		
		if(key.pause.down) {
			System.out.println("dialogstate");
		}
		
		if(mouse.getButton() == 1) {
			GamePanel.getGsm().addAndPop(0);
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
	}


	@Override
	public void onEvent(Event event) {
		// TODO Auto-generated method stub
		
	}
	


}
