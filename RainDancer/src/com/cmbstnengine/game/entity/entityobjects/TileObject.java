package com.cmbstnengine.game.entity.entityobjects;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.cmbstnengine.game.entity.Entity;
import com.cmbstnengine.game.entity.Player;
import com.cmbstnengine.game.entity.entityobjects.ObjectType.ObjType;
import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.util.AABB;
import com.cmbstnengine.game.util.TileCollision;
import com.cmbstnengine.game.util.Vector2f;


public class TileObject extends Entity implements Comparable<TileObject> {
	
	protected int gid;
	protected int objectWidth;
	protected int objectHeight;
	protected boolean hide = false;
	
	protected int r;
	
	protected AABB sense;
	
	protected boolean isAutoPickupable;
	protected boolean isInteractable;
	protected int tileColumns;
	protected boolean removeFromInventory = false;
	
	protected String type;
	protected String name;
	public ObjType objectType;
	protected BufferedImage img;
	protected BufferedImage highlightedImage;
	protected String objName;
	
	public boolean highlighted;
	
	public TileObject(Sprite sprite, Vector2f origin, int size, int gridID, int tileColumns, int tileWidth, int tileHeight, int objectWidth, int objectHeight, 
			String type, String name) {
		super(sprite, origin, size);
		this.gid = gridID;	
		this.objectWidth = objectWidth;
		this.objectHeight = objectHeight;
		this.r = (this.objectWidth * 2);
		this.tileColumns = tileColumns;
		this.img = sprite.getSprite((int) ((gid - 1) % tileColumns), (int) ((gid - 1) / tileColumns));
		this.highlightedImage = Sprite.highlight(img);
		
				
		bounds = new AABB(origin, this.objectWidth, this.objectHeight);
	    hitBounds = new AABB(origin, this.objectWidth, this.objectHeight);		
	    tc = new TileCollision(this);
	    sense = new AABB(new Vector2f(origin.x - r / 4, origin.y - r / 4), r, this);
	    this.type = type;
	    this.name = name;
	    this.objName = name;
	}
	
	public boolean getRemoveFromInventory() {
		return removeFromInventory;
	}
	
	public void setRemoveFromInventory(boolean b) {
		this.removeFromInventory = b;
	}
	
	public boolean update(AABB p) {
		return false;
	}
	
	 public boolean isInsideSense(AABB p) {
	     if(p.getPos().x + p.getXOffset() < pos.x - 16) return false;
	     if(p.getPos().y + p.getYOffset() < pos.y - 16) return false;
	     if(objectWidth + 16 + pos.x < p.getWidth() + (p.getPos().x + p.getXOffset())) return false;
	     if(objectHeight + 16 + pos.y < p.getHeight() + (p.getPos().y + p.getYOffset())) return false;   
	     return true;
	 }
	 
	 public boolean isInsideMouse(float x, float y) {
		 return x >= pos.x && y >= pos.y && x <= pos.x + 32 && y <= pos.y + 32;
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
	public void render(Graphics2D g) {
		//g.setColor(Color.WHITE);
		if(!hide && Player.isWithinBounds((int) pos.x, (int) pos.y, (int) pos.x, (int) pos.y)) {
			g.drawImage(img, (int) pos.getWorldVar().x, (int) pos.getWorldVar().y, size, size, null);
		}
		
		if(highlighted) {
			g.drawImage(highlightedImage, (int) pos.getWorldVar().x, (int) pos.getWorldVar().y, size, size, null);
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
	
	public String getName() {
		return name;
	}
	

	@Override
	public String toString() {
		return name + " " + gid + " x: " + pos.x + " y: " + pos.y;
	}

	@Override
	public int compareTo(TileObject otherT) {
		return gid > otherT.getGid() ? 1 : gid < otherT.getGid() ? -1 : 0;
	}

	public void interact(Player player) {
		if(objectType == ObjType.CLICKABLE) {
			this.remove();
		}
		
		if(objectType == ObjType.PLACABLE) {
		}
	}

	public void clear() {
		// TODO Auto-generated method stub
		
	}

	public boolean containsPoint(float x, float y) {
		return isInsideMouse(x, y);
	}

	public void setPos(Vector2f v2f) {
		this.pos = v2f;	
		this.bounds = new AABB(v2f, this.objectWidth, this.objectHeight);
		this.hitBounds = new AABB(v2f, this.objectWidth, this.objectHeight);		
		this.tc = new TileCollision(this);
		this.sense = new AABB(new Vector2f(v2f.x - r / 4, v2f.y - r / 4), r, this);
	}

	public BufferedImage getSprite() {
		return img;
	}

	public String getObjName() {
		return objName;
	}
	

}
