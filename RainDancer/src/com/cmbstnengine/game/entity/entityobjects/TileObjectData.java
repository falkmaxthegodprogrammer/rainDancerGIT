package com.cmbstnengine.game.entity.entityobjects;

import java.awt.Graphics2D;

import com.cmbstnengine.game.entity.Entity;
import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.util.AABB;
import com.cmbstnengine.game.util.TileCollision;
import com.cmbstnengine.game.util.Vector2f;

public class TileObjectData  {
	
	public int gid;
	public int objectWidth;
	public int objectHeight;
	public int r;	
	public int tileColumns;
	public int tileWidth;
	public int tileHeight;
	public Sprite sprite;
	public Vector2f origin;
	public int size;	
	public String name;
	public String type;
	
	public TileObjectData(Sprite sprite, Vector2f origin, int size, int gridID, int tileColumns, int tileWidth, int tileHeight, int objectWidth, int objectHeight, String name, String type) {
		this.sprite = sprite;
		this.origin = origin;
		this.size = size;	
		this.gid = gridID;	
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.tileColumns = tileColumns;
		this.objectWidth = objectWidth;
		this.objectHeight = objectHeight;
		this.r = (this.objectWidth * 2);
		this.name = name;
		this.type = type;		
	}

	public int getObjectWidth() {
		return objectWidth;
	}
	
	public int getObjectHeight() {
		return objectHeight;
	}
	
	public int getRadius() {
		return r;
	}
	
	public int getGid() {
		// TODO Auto-generated method stub
		return gid;
	}

}
