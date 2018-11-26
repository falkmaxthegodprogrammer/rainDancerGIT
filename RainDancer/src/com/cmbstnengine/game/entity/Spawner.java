package com.cmbstnengine.game.entity;

import java.awt.Graphics2D;

import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.util.Vector2f;

public abstract class Spawner extends Entity {

	public enum Type {
		MOB, 
		PARTICLE,
		ENEMY,
		NPC,
		CRITTERS,
		TILEOBJECTS;
	}
	
	private Type type;
	
	public Spawner(Sprite sprite, Vector2f origin, int size, Type type) {
		super(sprite, origin, size);
		// TODO Auto-generated constructor stub
		this.type = type;
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

}
