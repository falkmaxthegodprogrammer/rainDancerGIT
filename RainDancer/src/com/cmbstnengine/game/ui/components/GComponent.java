package com.cmbstnengine.game.ui.components;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cmbstnengine.game.util.Vector2f;
import com.cmbstnengine.game.util.Vector2i;

public class GComponent {
	
	public Vector2i pos, size;
	protected Vector2i offset;
	public Color color;
	protected GPanel panel;
	protected boolean hidden = false;
	private boolean showBorder;
	
	public GComponent(Vector2i pos) {
		this.pos = pos;
		offset = new Vector2i();
	}
	
	public GComponent(Vector2i pos, Vector2i size) {
		this.pos = pos;
		this.size = size;
		color = new Color(0, 0, 0, 125);	
		offset = new Vector2i();
	}
	
	public GComponent(Vector2i pos, Vector2i size, Color color) {
		this.pos = pos;
		this.size = size;
		this.color = color;
		offset = new Vector2i();

	}
	
	public void update() {}
	
	public void render(Graphics2D g) {}
	
	public Vector2i getAbsolutePosition() {
		return new Vector2i(pos).add(offset);
	}
	
	void setOffset(Vector2i offset) {
		this.offset = offset;
	}
	
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	public boolean isHidden() {
		return hidden;
	}
	
	public void setColor(int color) {
		this.color = new Color(color);
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	void init(GPanel gPanel) {
		this.panel = gPanel;
	}

	public boolean isShowBorder() {
		return showBorder;
	}

	public void setShowBorder(boolean showBorder) {
		this.showBorder = showBorder;
	}
	

}
	
	