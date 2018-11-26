package com.cmbstnengine.game.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import com.cmbstnengine.game.audio.AudioClip;
import com.cmbstnengine.game.audio.AudioPlayer;
import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.states.PlayState;
import com.cmbstnengine.game.util.Vector2f;

public class Arrow extends Projectile {

	public static final Sprite ARROW_SPRITE = new Sprite("entity/magic_purple_arrow.png", 32, 32);
	public static final Sprite ARROW_PARTICLE = new Sprite("entity/arrow_particle.png", 8, 8);
	
	public double startx, starty;	
	private AudioClip explode_sound_00 = new AudioClip("res/SFX/Fantasy Sound Library/Wav/Footsteps/Footstep_Dirt_00.wav");
	
	public Arrow(Sprite sprite, Vector2f origin, int size, double dir, Entity e) {
		super(sprite, origin, size, dir, e);
		
		startx = origin.x;
		starty = origin.y;
		
		maxSpeed = 32;
		acc = 1;
		deacc = 1;
		
		FIRE_RATE = 15;
		range = 1000;
		damage = 10;
		
		nx = maxSpeed * Math.cos(angle);
		ny = maxSpeed * Math.sin(angle);	
		
		bounds.setWidth(size / 3);
		bounds.setHeight(size / 2);
		bounds.setXOffset(size / 3);
		bounds.setYOffset(-48);
		
		Player p = (Player) e; 
		if(p.shootingUp) {
			bounds.setXOffset(-32);
			bounds.setYOffset(-32);
		} else if(p.shootingRight) {
			bounds.setYOffset(-38);
 		}  else if(p.shootingLeft) {
 			bounds.setYOffset(6);
 			bounds.setXOffset(-48);
 		} else if(p.shootingDown) {
 			bounds.setYOffset(-32);
 		}
		
		ani.setDelay(-1);
		setAnimation(0, sprite.getSpriteArray(0), -1);
		r = 16;
	}
	  
	public void update() {
		
				animate();
				ani.update();
			
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
	            	ParticleSpawner p = new ParticleSpawner(ARROW_PARTICLE, new Vector2f(pos.x, pos.y), 2, 4, 16);
	            	PlayState.add(p);
	            	remove();
	            	AudioPlayer.playSound(explode_sound_00, 0.40);
	            }
	          
	            if(whoFired instanceof Player) {
		            if(tc.projectileCollisionEnemy(dx, dy, this)) {
		            		ParticleSpawner p = new ParticleSpawner(ARROW_PARTICLE, new Vector2f(pos.x, pos.y), 2, 4, 16);
		            		PlayState.add(p);
		            		remove();	
			            	AudioPlayer.playSound(explode_sound_00, 0.54);
		            }	         
	            }
	            
	            if(whoFired instanceof Enemy) {            	
		            if(tc.projectileCollisionPlayer(dx, dy, this)) {
			            	ParticleSpawner p = new ParticleSpawner(ARROW_PARTICLE, new Vector2f(pos.x, pos.y), 2, 4, 16);
			            	PlayState.add(p);
		            		remove();	
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

	public void rotateBounds(double dir) {
		AffineTransform trans = new AffineTransform();
		trans.rotate(dir, (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y));		
		Shape s = trans.createTransformedShape(new Rectangle((int) pos.x, (int) pos.y, (int) getBounds().getWidth(), (int) getBounds().getHeight()));
	}
		
	public void render(Graphics2D g) {
		double dir = angle - Math.PI / 2;
		AffineTransform backup = g.getTransform();
		AffineTransform trans = new AffineTransform();
		trans.rotate(dir, (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y));
		g.transform(trans);
		g.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
		g.setTransform(backup);		
	//	g.setColor(Color.RED);
	//	g.drawRect((int) bounds.getPos().getWorldVar().x + (int) bounds.getXOffset(), (int) bounds.getPos().getWorldVar().y + (int) bounds.getYOffset(), (int) bounds.getWidth(), (int) bounds.getHeight());
	}
	
}
