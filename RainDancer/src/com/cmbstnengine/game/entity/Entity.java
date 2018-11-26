package com.cmbstnengine.game.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.cmbstnengine.game.GamePanel;
import com.cmbstnengine.game.graphics.Animation;
import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.states.PlayState;
import com.cmbstnengine.game.util.AABB;
import com.cmbstnengine.game.util.MouseHandler;
import com.cmbstnengine.game.util.TileCollision;
import com.cmbstnengine.game.util.Vector2f;

public abstract class Entity {

	protected final int PROJECTILE = 5;
	
	protected int currentAnimation;
	
	protected Sprite sprite;
	protected Vector2f pos;
	protected AABB hitBounds;
	protected AABB bounds;
	protected AABB hitBox;
	protected int size;
	
	protected float dx;
	protected float dy;
	
	protected float maxSpeed = 3f;
	protected float acc = 3f;
	protected float deacc = 0.7f;
	
	protected boolean isRemoved;
	protected boolean fallen;
	protected boolean inWater;
	
	protected boolean noClip;
	
	protected TileCollision tc;
	
	public Entity(Sprite sprite, Vector2f origin, int size) {
		this.sprite = sprite;
		pos = origin;
		this.size = size;
		
        bounds = new AABB(origin, this.size, this.size);
        hitBounds = new AABB(origin, this.size, this.size);
        hitBounds.setXOffset(this.size / 2);		
		tc = new TileCollision(this);
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void setFallen(boolean b) { fallen = b; }
	public void setSize(int i) { size = i; }
	public void setMaxSpeed(float f) { maxSpeed = f; }
	public void setAcc(float f) { this.acc = f; }
	public void setDeAcc(float f) { deacc = f; }
	
	public AABB getBounds() { return bounds; }
	
	public int getSize() { return size; }


	public void update() {
	}
	
	public void unRemove() {
		this.isRemoved = false;
	}
	
	public void remove() {
		this.isRemoved = true;
	}
	
	public boolean isRemoved() {
		return isRemoved;
	}
	
	public void setInWater(boolean b) {
		this.inWater = b;
	}
	
	public boolean getNoClip() {
		return noClip;
	}
	
	public AABB getHitBox() { return hitBox; }
	
	public void setNoClip(boolean b) {
		this.noClip = b;
	}
	
	public abstract void render(Graphics2D g);
	

}