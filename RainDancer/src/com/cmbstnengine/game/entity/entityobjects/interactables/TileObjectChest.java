package com.cmbstnengine.game.entity.entityobjects.interactables;

import java.awt.Color;
import java.awt.Graphics2D;
import com.cmbstnengine.game.entity.Player;
import com.cmbstnengine.game.entity.entityobjects.ObjectType.ObjType;
import com.cmbstnengine.game.entity.entityobjects.TileObject;
import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.util.AABB;
import com.cmbstnengine.game.util.TileCollision;
import com.cmbstnengine.game.util.Vector2f;

public class TileObjectChest extends TileObject {
	
	public boolean opened = false;

	public TileObjectChest(Sprite sprite, Vector2f origin, int size, int gridID, int tileColumns, int tileWidth,
			int tileHeight, int objectWidth, int objectHeight, String type, String name) {
		super(sprite, origin, size, gridID, tileColumns, tileWidth, tileHeight, objectWidth, objectHeight, type, name);
		// TODO Auto-generated constructor stub
		
		bounds = new AABB(origin, this.objectWidth, this.objectHeight);
	    hitBounds = new AABB(origin, this.objectWidth, this.objectHeight);		
	    tc = new TileCollision(this);
	    sense = new AABB(new Vector2f(origin.x - r / 4, origin.y - r / 4), r, this);
	   
	    this.objectType = ObjType.INTERACTABLE;
	}
	
	public boolean update(AABB p) {
		return false;
	}
	
	 public boolean isInsideSense(AABB p) {
	       if(p.getPos().x + p.getXOffset() < pos.x - 64) return false;
	       if(p.getPos().y + p.getYOffset() < pos.y - 64) return false;
	       if(objectWidth + 64 + pos.x < p.getWidth() + (p.getPos().x + p.getXOffset())) return false;
	       if(objectHeight + 64 + pos.y < p.getHeight() + (p.getPos().y + p.getYOffset())) return false;   
	       return true;
	}
	
	 public boolean isInside(AABB p) {
	       if(p.getPos().x + p.getXOffset() < pos.x) return false;
	       if(p.getPos().y + p.getYOffset() < pos.y) return false;
	       if(objectWidth + pos.x < p.getWidth() + (p.getPos().x + p.getXOffset())) return false;
	       if(objectHeight + pos.y < p.getHeight() + (p.getPos().y + p.getYOffset())) return false;   
	       return true;
	}
	 
	 
	public Vector2f getPos() { return pos; }
	

	@Override
	public void interact(Player player) {
		System.out.println("I am dropping a lot of loot!");
		// animate opening of chest
		// play open chest sound
		opened = true;		
	//	player.showLootTable(this);
		if(opened) {
			
		}
		
	}
	
	public int getGid() {
		return gid;
	}

	public AABB getSense() {
		return sense;
	}

	public void setSense(AABB sense) {
		this.sense = sense;
	}
	
	public String toString() {
		return "I am a chest";
	}
	
	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		if(!hide && Player.isWithinBounds((int) pos.x, (int) pos.y, (int) pos.x, (int) pos.y)) {
			super.render(g);
		//	g.drawRect((int) pos.getWorldVar().x, (int) pos.getWorldVar().y, (int) getBounds().getWidth(), (int) getBounds().getHeight());
		//	g.drawOval((int) (getSense().getPos().getWorldVar().x), (int) (getSense().getPos().getWorldVar().y), r, r);

		}
	}

}
