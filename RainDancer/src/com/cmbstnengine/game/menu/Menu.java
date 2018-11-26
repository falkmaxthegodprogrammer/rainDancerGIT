package com.cmbstnengine.game.menu;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cmbstnengine.game.GamePanel;
import com.cmbstnengine.game.util.KeyHandler;
import com.cmbstnengine.game.util.MouseHandler;

public class Menu {
	
	int x, y, size;
	
	public boolean inventory = false;
	
	public Menu(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;		
	}
	
	public void input(KeyHandler key, MouseHandler mouse) {
		if(key.menu.down) {
			this.inventory = true;
		} else {
			this.inventory = false;
		}
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.GRAY);
		if(inventory) {
			g.fillRect(x, y, size, 720);
		}
		g.drawRect((int) (GamePanel.width / 4), GamePanel.height - 100, (int) ((int) GamePanel.width * 0.50), 60);
	}

}
