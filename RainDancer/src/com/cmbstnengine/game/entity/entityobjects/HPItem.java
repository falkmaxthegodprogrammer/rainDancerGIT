package com.cmbstnengine.game.entity.entityobjects;

import com.cmbstnengine.game.entity.Player;
import com.cmbstnengine.game.entity.entityobjects.ObjectType.ObjType;
import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.util.Vector2f;

public class HPItem extends TileObject {

	protected int amount;
	
	public HPItem(Sprite sprite, Vector2f origin, int size, int gridID, int tileColumns, int tileWidth, int tileHeight,
			int objectWidth, int objectHeight, String type, String name) {
		super(sprite, origin, size, gridID, tileColumns, tileWidth, tileHeight, objectWidth, objectHeight, type, name);
		// TODO Auto-generated constructor stub
		this.objectType = ObjType.CLICKABLE;
		this.amount = 40;
		this.objName = name;
	}
	
	@Override
	public void interact(Player player) {
		super.interact(player);
		player.heal(amount);
	}
	
	
	
	
	

}
