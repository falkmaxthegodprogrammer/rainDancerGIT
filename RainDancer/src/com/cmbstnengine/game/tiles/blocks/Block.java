package com.cmbstnengine.game.tiles.blocks;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;import com.cmbstnengine.game.entity.Player;
import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.util.AABB;
import com.cmbstnengine.game.util.Vector2f;

public abstract class Block {
	
	protected int w;
	protected int h;
	protected BufferedImage img;
	protected Vector2f pos;
	protected static Sprite sprite;

	public Block(BufferedImage img, Vector2f pos, int w, int h) {
		this.img = img;
		this.pos = pos;
		this.w = w;
		this.h= h;
	}
	
    public abstract boolean isInside(AABB p);
    public Vector2f getPos() { return pos; }
	public abstract boolean update(AABB p);
	
	public void render(Graphics2D g) {
		if(Player.isWithinBounds((int) pos.x, (int) pos.y, (int) pos.x, (int) pos.y)) {
			g.drawImage(img, (int) pos.getWorldVar().x, (int) pos.getWorldVar().y, w, h, null);
		}
	}


}
