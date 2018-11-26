package com.cmbstnengine.game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.cmbstnengine.game.util.Light;
import com.cmbstnengine.game.util.Vector2f;

public class LightPanel extends JPanel {

	/**
	 * 
	 */
	
	private ArrayList<Light> lights;
	private BufferedImage lightMap = new BufferedImage(GamePanel.width, GamePanel.height, BufferedImage.TYPE_INT_ARGB);

	
	private static final long serialVersionUID = 1L;

	public LightPanel() {
		lights = new ArrayList<Light>();
		
		
		lights.add(new Light(new Vector2f(0, 0), 1200, 1.9f));	

		lights.add(new Light(new Vector2f(0, 400), 200, 1.2f));	
		lights.add(new Light(new Vector2f(300, 100), 30, 1.0f));	
		lights.add(new Light(new Vector2f(500, 100), 10, 0.9f));	

		makeLightMap(lightMap);
		
	}
	
	public BufferedImage getLightMap() {
		return lightMap;
	}
	
	
	public void makeLightMap(BufferedImage lightMap) {
		   Graphics2D gl = lightMap.createGraphics();
		   gl.setColor(new Color(0, 0, 0, 255));
		   gl.fillRect(0, 0, GamePanel.width, GamePanel.height);
		   Composite oldComp = gl.getComposite();
		   gl.setComposite(AlphaComposite.DstOut);

		   for(Light light : lights) light.render(gl);

		   gl.setComposite(oldComp);
		   gl.dispose();
		}
	
}