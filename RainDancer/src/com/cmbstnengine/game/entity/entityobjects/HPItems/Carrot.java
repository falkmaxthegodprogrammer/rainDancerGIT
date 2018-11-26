package com.cmbstnengine.game.entity.entityobjects.HPItems;

import java.awt.image.BufferedImage;

import com.cmbstnengine.game.entity.entityobjects.HPItem;
import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.util.Vector2f;

public class Carrot extends HPItem {

	private BufferedImage imageIcon;
	
	public Carrot(Sprite sprite, Vector2f origin, int size, int gridID, int tileColumns, int tileWidth, int tileHeight,
			int objectWidth, int objectHeight, String type, String name, String objectName) {
		super(sprite, origin, size, gridID, tileColumns, tileWidth, tileHeight, objectWidth, objectHeight, type, name);
		// TODO Auto-generated constructor stub
		this.name = name;
		this.amount = 10;
		this.objName = "nice carrot";
	}
	
	@Override
	public String toString() {
		return "nice carrot";
	}
	

}
