package com.cmbstnengine.game.tiles;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.cmbstnengine.game.GamePanel;
import com.cmbstnengine.game.util.Light;

public class LightMap {

	private ArrayList<Light> lights;
	
	public LightMap() {
		

	}
	
	
	
	public void render(Graphics2D g) {
		g.setComposite(AlphaComposite.DstOut);
		g.setColor(new Color(0, 0, 0, 255));
		g.fillRect(0, 0, GamePanel.width, GamePanel.height);	
	}
	
}
