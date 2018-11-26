package com.cmbstnengine.game.entity.entityobjects;

public abstract class ObjectType {
	
	public static ObjType type;
	
	public enum ObjType {
		INTERACTABLE,
		LOOTABLE,
		STATSMODIFIER,
		DESTRUCTABLE,
		CLICKABLE,
		PLACABLE
	}
	
	public ObjectType(ObjType type) {
		this.type = type;
	}

}
