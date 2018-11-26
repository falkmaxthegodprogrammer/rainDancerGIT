package com.cmbstnengine.game.ui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.cmbstnengine.game.graphics.GFont;
import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.util.Vector2f;
import com.cmbstnengine.game.util.Vector2i;

public class GLabel extends GComponent {

	public String text;
	private static Font font = new Font("Helvetica", Font.PLAIN, 14);
	private GFont gameFont;
	public boolean dropShadow = false;
	public int dropShadowOffset = 2;
	private int gameFontSize;
	
	public GLabel(Vector2i pos, String text) {
		super(new Vector2i(pos.x - (text.length() / 2 * font.getSize() / 2), pos.y + font.getSize()));
		font = new Font("Helvetica", Font.PLAIN, 10);
		this.text = text;
		color = new Color(0x000000);
	}

	public GLabel(Vector2i pos, String text, Color color) {
		super(new Vector2i(pos.x - (text.length() / 2 * font.getSize() / 2), pos.y + font.getSize()));
		this.text = text;
		this.color = color;
	}
	
	public GLabel(Vector2i pos, String text, GFont gfont) {
		super(pos);
		this.gameFont = gfont;
		this.text = text;
		color = new Color(0x000000);
	}
	
	public GLabel(Vector2i pos, String text, GFont gfont, int size) {
		super(pos);
		this.gameFont = gfont;
		this.text = text;
		this.gameFontSize = size;
		color = new Color(0x000000);
	}
	
	public GLabel setFontReturnNewLabel(Font font) {
		this.font = font;
		return this;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	public void setGameFont(GFont font) {
		this.gameFont = font;
	}
	
	public void setGameFontSize(int fontSize) {
		this.gameFontSize = fontSize;
	}
	
	public void setPos(int x, int y) {
		pos.x = x; 
		pos.y = y;
	}

	public void render(Graphics2D g) {
		if(gameFont != null) {
			Sprite.drawArray(g, text, new Vector2f((int) pos.x, (int)pos.y), gameFontSize);
			return;
		} else {
		if (dropShadow) {
			g.setFont(font);
			g.setColor(Color.BLACK);
			g.drawString(text, pos.x + offset.x + dropShadowOffset, pos.y + offset.y + dropShadowOffset);
		}
		g.setColor(color);
		g.setFont(font);
		g.drawString(text, pos.x, pos.y);
		}
	}
	

}