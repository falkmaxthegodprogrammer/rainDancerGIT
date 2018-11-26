package com.cmbstnengine.game.ui.components;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class GUIManager {
	
	private List<GPanel> panels = new ArrayList<>();
	private List<GScrollPane> scrollPanes = new ArrayList<>();
	private boolean hidden;
	
	public GUIManager() {
		
	}
	
	public void addScrollPane(GScrollPane pane) {
		scrollPanes.add(pane);
	}
	
	public void addPanel(GPanel panel) {
		panels.add(panel);
	}
	
	public void update() {
		for(GPanel panel : panels) {
			panel.update();
		}
	}
	
	public void input() {}
	
	public List<GPanel> getPanels() {
		return panels;
	}
	
	public void render(Graphics2D g) {
		if(!hidden) {
			panels.forEach((panel) -> panel.render(g));
		}
	}

	public void toggleVisible() {
		if(hidden)
			this.setHidden(false);
		else if(!hidden)
			this.setHidden(true);
	}
	
	public void setHidden(boolean b) {
		this.hidden = b;
	}
	
	public boolean getHidden() {
		return hidden;
	}

	public void renderScrollPanes(Graphics2D g) {
		for(GScrollPane pane : scrollPanes) {
			pane.render(g);
		}
	}
	
	public List<GScrollPane> getScrollPanes() {
		// TODO Auto-generated method stub
		return scrollPanes;
	}

	public GScrollPane getScrollPane(int x, int y) {
		GScrollPane paneToReturn = null;
		for(GScrollPane pane : scrollPanes) {
			if(!pane.isHidden()) {
				Rectangle bounds = new Rectangle(pane.pos.x, pane.pos.y, pane.size.x, pane.size.y);
				if(bounds.contains(new Point(x, y))) {
					paneToReturn = pane;
				}
			}
		}
		return paneToReturn;
	}
	
	
}
