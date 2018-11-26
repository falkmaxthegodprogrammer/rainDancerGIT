package com.cmbstnengine.game.entity.entityobjects;

import com.cmbstnengine.game.entity.Player;
import com.cmbstnengine.game.entity.entityobjects.ObjectType.ObjType;
import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.util.Vector2f;

public class Flower extends TileObject {
	
	public static Sprite roseSprite;
	public static Sprite maskrosSprite;
	
	public Flower(Sprite sprite, Vector2f origin, int size, int gridID, int tileColumns, int tileWidth, int tileHeight,
			int objectWidth, int objectHeight, String type, String name) {
		super(sprite, origin, size, gridID, tileColumns, tileWidth, tileHeight, objectWidth, objectHeight, type, name);
		// TODO Auto-generated constructor stub
		this.objectType = ObjType.PLACABLE;
	}
	
	public void add() {
		
	}
	
	@Override
	public void interact(Player player) {
		super.interact(player);
	}
	

}
