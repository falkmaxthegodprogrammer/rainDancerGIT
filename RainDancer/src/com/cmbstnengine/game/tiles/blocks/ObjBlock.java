package com.cmbstnengine.game.tiles.blocks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import com.cmbstnengine.game.entity.Player;
import com.cmbstnengine.game.util.AABB;
import com.cmbstnengine.game.util.Vector2f;

public class ObjBlock extends Block {

	public AABB betterBounds;

	
	public ObjBlock(BufferedImage img, Vector2f pos, int w, int h) {
		super(img, pos, w, h);
		// TODO Auto-generated constructor stub

	}


	@Override
	public boolean update(AABB p) {
		if(Player.isWithinBounds((int) pos.x, (int) pos.y, (int) pos.x, (int) pos.y)) {
			return true;
		}
		return false;
	}
    
	public void render(Graphics2D g) {
		super.render(g);
        g.setColor(Color.white);
   //     g.drawRect((int) pos.getWorldVar().x, (int) pos.getWorldVar().y, w, h);
        
     //   g.setColor(Color.red);
    //    g.drawRect((int) betterBounds.getPos().getWorldVar().x, (int) betterBounds.getPos().getWorldVar().y, (int) betterBounds.getWidth(), (int) betterBounds.getHeight());

	}


	@Override
	public boolean isInside(AABB p) {
		// TODO Auto-generated method stub
		return false;
	}


}
