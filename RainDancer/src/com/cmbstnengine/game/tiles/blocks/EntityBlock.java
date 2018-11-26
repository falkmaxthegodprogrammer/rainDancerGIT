package com.cmbstnengine.game.tiles.blocks;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.cmbstnengine.game.util.AABB;
import com.cmbstnengine.game.util.Vector2f;

public class EntityBlock extends Block {

	public EntityBlock(BufferedImage img, Vector2f pos, int w, int h) {
		super(img, pos, w, h);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean update(AABB p) {
		// TODO Auto-generated method stub
		return true;
	}
	
    public boolean isInside(AABB p) {
    	return false;
    }
	 
	public void render(Graphics2D g) {
		super.render(g);
	}

}
