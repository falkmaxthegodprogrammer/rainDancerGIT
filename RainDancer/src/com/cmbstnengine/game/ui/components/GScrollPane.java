package com.cmbstnengine.game.ui.components;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cmbstnengine.game.util.Vector2i;

public class GScrollPane extends GComponent {

	private GPanel panel;
	private int yOffset;
	
	public GScrollPane(Vector2i pos, Vector2i size) {
		super(pos, size);
		// TODO Auto-generated constructor stub
	}
	
	public void add(GPanel panel) {
		this.panel = panel;
	}
	
	public void update() {
	
	}
	
	public void setOffset(int x, int y) {
		if(y < 0) y = 0;
		if(y > panel.size.y) y = panel.size.y;
		this.yOffset = y;
	}
	
	public void updatePanel(boolean up) {
		if(up) {
			panel.pos.y -= yOffset;
		} else {
			panel.pos.y += yOffset;
		}
	}
	
	public void determineViewport() {
		
	}
	
	public void render(Graphics2D g) {
		panel.render(g);

		g.setColor(new Color(0, 0, 0, 255));
		g.fillRect(pos.x, pos.y, size.x, size.y);

	}

	public void movePanelUp(int i) {
		panel.pos.y -= i;
	}
	
	public void movePanelDown(int i) {
		panel.pos.y += i;
	}
	
}
