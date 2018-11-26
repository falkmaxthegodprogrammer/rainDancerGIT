package com.cmbstnengine.game.entity;

import com.cmbstnengine.game.graphics.Animation;
import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.util.Vector2f;

public abstract class Projectile extends Entity {

	protected Vector2f origin;
	protected double angle;
	protected double nx, ny;
	
	public static int FIRE_RATE;
	protected int damage;
	protected int range;
	
	protected Animation ani;
	
	protected int xOffset;
	protected int yOffset;
	
	protected int r;
	
	protected Entity whoFired;
	
	public Projectile(Sprite sprite, Vector2f origin, int size, double dir) {
		super(sprite, origin, size);
		angle = dir;		
		ani = new Animation();
		bounds.setWidth(size);
		bounds.setHeight(size);
		this.range = 1000;
	}
	
	public Projectile(Sprite sprite, Vector2f origin, int size, double dir, Entity e) {
		super(sprite, origin, size);
		angle = dir;
		ani = new Animation();
		bounds.setWidth(size);
		bounds.setHeight(size);
		this.whoFired = e;
		this.range = 1000;
	}
	
	public void update() {
		ani.update();
	}
	
	protected void move() {

	}

	protected int getDamage() {
		return damage;
	}
	
	public Entity getWhoFired() {
		return whoFired;
	}
	
	
	

}
