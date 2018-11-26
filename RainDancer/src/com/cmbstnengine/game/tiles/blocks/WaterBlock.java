package com.cmbstnengine.game.tiles.blocks;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import com.cmbstnengine.game.util.AABB;
import com.cmbstnengine.game.util.Vector2f;

public class WaterBlock extends Block {
		
	public WaterBlock(BufferedImage img, Vector2f pos, int w, int h) {
		super(img, pos, w, h);
	}

	@Override
	public boolean update(AABB p) {
		if(isInside(p)) {
			//System.out.println("I am in water");
		}
		return false;
	}
	
	 public boolean isInside(AABB p) {	

	       return true;
	}

	@Override
	public void render(Graphics2D g) {
		super.render(g);
	}
}

