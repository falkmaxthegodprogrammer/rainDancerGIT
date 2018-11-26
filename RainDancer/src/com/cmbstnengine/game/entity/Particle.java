package com.cmbstnengine.game.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.util.TileCollision;
import com.cmbstnengine.game.util.Vector2f;

public class Particle extends Entity {

	private int life;
	private int time = 0;
	protected double xx, yy, zz;
	protected double xa, ya, za;
	protected double x, y;
	private Random random = new Random();
	private Entity whoFired;
	
	public Particle(Sprite sprite, Vector2f origin, int size, int life) {
		super(sprite, origin, size);
		
		maxSpeed = 1;

		this.xx = dx;
		this.yy = dy;
		this.life = life + (random.nextInt(20) - 10);

		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 0.5;	
	}
	
	public Particle(Sprite sprite, Vector2f origin, int size, int life, Entity e) {
		super(sprite, origin, size);
		
		maxSpeed = 1;

		this.xx = dx;
		this.yy = dy;
		this.life = life + (random.nextInt(20) - 10);

		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 0.5;	
		
		this.whoFired = e;
	}
	
	public void update() {
		time++;
		if(time >= 7400) time = 0;
		if(time > life) { 
			remove();
		}
		
		za -= 0.2;
		 
		if(zz < 0) {
			zz = 0;
			za *= -0.8;
			xa *= 0.7;
			ya *= 0.5;
		}
		move();	
	}
	
	private void move() {
		xx += xa;
		yy += ya;
		this.zz += za;	
	}
	//screen.renderSprite((int) xx + 2, (int) yy - (int) zz - 2, sprite, true);


	public void render(Graphics2D g) {
		g.setColor(Color.ORANGE);	
		//g.drawRect((int) (x + (int) xx), (int) (pos.getWorldVar().y + (int) yy - zz -2), 1, 1);
		
		//g.drawRect((int) xx + 2, (int) yy - (int) zz - 2, 5, 5);
		//g.drawRect((int) xx + 2 + (int) bounds.getXOffset(), (int) + (int) yy - (int) zz - 2 + (int) bounds.getYOffset(), size, size);
		//g.fillRect((int)pos.getWorldVar().x +(int) xx, (int) pos.getWorldVar().y + (int) yy - (int) zz - 2, size, size);
		

		g.drawImage(sprite.getSprite(0, 0), (int)pos.getWorldVar().x + (int) xx, (int) pos.getWorldVar().y + (int) yy - (int) zz - 2, size, size, null);
	}
	
}
