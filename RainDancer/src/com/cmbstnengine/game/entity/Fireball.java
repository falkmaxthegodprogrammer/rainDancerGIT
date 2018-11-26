package com.cmbstnengine.game.entity;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.cmbstnengine.game.audio.AudioClip;
import com.cmbstnengine.game.audio.AudioPlayer;
import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.states.PlayState;
import com.cmbstnengine.game.util.Vector2f;

public class Fireball extends Projectile {
	
	public static final Sprite FIREBALL_SPRITE = new Sprite("entity/fireball_anim_2.png", 32, 32);
	public static final Sprite FIREBALL_SPRITE2 = new Sprite("entity/fireball_anim.png", 32, 32);
	public static final Sprite FIREBALL_PARTICLE = new Sprite("entity/fireball_particle.png", 8, 8);
	public static final Sprite ARROW_SPRITE = new Sprite("entity/magic_purple_arrow.png", 32, 32);

	public double startx, starty;
	
	private AudioClip explode_sound_00 = new AudioClip("res/SFX/Fantasy Sound Library/Wav/Footsteps/Footstep_Dirt_00.wav");
	
	public Fireball(Sprite sprite, Vector2f origin, int size, double dir) {
		super(sprite, origin, size, dir);
		
		startx = origin.x;
		starty = origin.y;
		
		maxSpeed = 24;
		acc = 10;
		deacc = 1;
		
		Fireball.FIRE_RATE = 15;
		range = 1000;
		damage = 10;
		
		nx = maxSpeed * Math.cos(angle);
		ny = maxSpeed * Math.sin(angle);	
			
		setAnimation(0, sprite.getSpriteArray(0), 1);
		r = 16;
	}
	
	public Fireball(Sprite sprite, Vector2f origin, int size, double dir, Entity e) {
		super(sprite, origin, size, dir, e);
		
		startx = origin.x;
		starty = origin.y;
		
		maxSpeed = 24;
		acc = 10;
		deacc = 1;
		
		FIRE_RATE = 15;
		range = 1000;
		damage = 10;
		
		nx = maxSpeed * Math.cos(angle);
		ny = maxSpeed * Math.sin(angle);	
			
		setAnimation(0, sprite.getSpriteArray(0), 1);
		r = 16;
	}
	  
	public void update() {
				animate();
				ani.update();
	            //move();
								
	            if(!tc.collisionTile(dx, 0)) {
	                pos.x += nx;
	            }
	            if(!tc.collisionTile(0, dy)) {
	                pos.y += ny;
	            } 
	                        
	            
            	if(getDistance() > range) {
            		remove();
            	}
	            
	            if(tc.collisionTile(dx,  dy)) {
	            	remove();
	            	ParticleSpawner p = new ParticleSpawner(FIREBALL_PARTICLE, new Vector2f(pos.x, pos.y), 2, 4, 32);
	            	PlayState.add(p);
	            }
	          
	            if(whoFired instanceof Player) {
		            if(tc.projectileCollisionEnemy(dx, dy, this)) {

		            		remove();	


			            	ParticleSpawner p = new ParticleSpawner(FIREBALL_PARTICLE, new Vector2f(pos.x, pos.y), 2, 4, 32);
			            	PlayState.add(p);

		 
		            }	         
	            }
	            
	            if(whoFired instanceof Enemy) {
	            	
		            if(tc.projectileCollisionPlayer(dx, dy, this)) {

		            		remove();	
			            	ParticleSpawner p = new ParticleSpawner(FIREBALL_PARTICLE, new Vector2f(pos.x, pos.y), 2, 4, 32);
			            	PlayState.add(p);

		            }	      
	            }	            		            	
	}
	                        	      	
	public void animate() {

	}
	
	
	public void setAnimation(int i, BufferedImage[] frames, int delay) {
		currentAnimation = i;
		ani.setFrames(frames);
		ani.setDelay(delay);
	}
	
	protected void move() {
            dy -= ny;
            if(dy < -maxSpeed) {
             dy = -maxSpeed;
            }
            dx -= nx;
            if(dx < -maxSpeed) {
            	dx = -maxSpeed;
            }
	}
	
	
	private double getDistance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((startx - pos.x) * (startx - pos.x) + (starty - pos.y) * (starty - pos.y)));
		return dist;
	}

	public void render(Graphics2D g) {

		g.setColor(Color.CYAN);
		//g.fillOval((int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), r, r);
		//g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int)bounds.getWidth(), (int)bounds.getHeight());
		if(Player.isWithinBounds((int) pos.x, (int)pos.y,(int) pos.x,(int) pos.y)) {
			g.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);

		}
	
	}
	
	

}
