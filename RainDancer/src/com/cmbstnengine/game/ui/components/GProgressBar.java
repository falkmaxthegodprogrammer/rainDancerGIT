package com.cmbstnengine.game.ui.components;

import java.awt.Color;
import java.awt.Graphics2D;

import org.w3c.dom.ranges.RangeException;

import com.cmbstnengine.game.util.Vector2i;

public class GProgressBar extends GComponent {

	private double progress; // 0.0 - 1.0

	private Color foregroundColor;

	public GProgressBar(Vector2i pos, Vector2i size) {
		super(pos);
		this.size = size;

		this.color = new Color(0x4E4A4E);
		foregroundColor = new Color(0xD04648);
	}
	
	public void setColor(Color color) {
		this.foregroundColor = color;
	}

	public void setColors(Color color, Color foreground) {
		this.color = color;
		this.color = foreground;
	}
	
	public void setProgress(double progress) {
		if (progress < 0.0 || progress > 1.0) throw new RangeException(RangeException.BAD_BOUNDARYPOINTS_ERR, "Progress must be between 0.0 and 1.0!");
		this.progress = progress;
	}

	public void setForegroundColor(int color) {
		this.foregroundColor = new Color(color);
	}

	public double getProgress() {
		return progress;
	}

	public void update() {
		getProgress();
	}

	public void render(Graphics2D g) {
		g.setColor(color);
		g.fillRect(((int) (pos.x)), ((int)(pos.y)), (int) size.x, (int) size.y);
		
		g.setColor(foregroundColor);
		g.fillRect(((int)(pos.x)), ((int)(pos.y)), (int) (progress * size.x), (int) size.y);
	}

}
