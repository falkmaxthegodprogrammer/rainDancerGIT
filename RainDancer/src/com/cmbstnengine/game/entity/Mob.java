package com.cmbstnengine.game.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.cmbstnengine.game.GamePanel;
import com.cmbstnengine.game.graphics.Animation;
import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.util.AABB;
import com.cmbstnengine.game.util.MouseHandler;
import com.cmbstnengine.game.util.TileCollision;
import com.cmbstnengine.game.util.Vector2f;

public class Mob extends Entity {
	
	protected int FALLEN = 4;
	protected int UP = 0;
	protected int LEFT = 1;
	protected int RIGHT = 3;
	protected int DOWN = 2;

	protected int SHOOTING_UP = 5;
	protected int SHOOTING_LEFT = 6;
	protected int SHOOTING_DOWN = 7;
	protected int SHOOTING_RIGHT = 8;
	
	protected int SWIMMING_UP = 10;
	protected int SWIMMING_LEFT = 11;
	protected int SWIMMING_RIGHT = 13;
	protected int SWIMMING_DOWN = 12;
	
	protected boolean up;
	protected boolean down;
	protected boolean right;
	protected boolean left;
	protected boolean attack;
	protected boolean isMoving;
	protected boolean isShooting;
	protected boolean isUsingSkill;
	protected boolean shield;
	
	public boolean beenHit = false;
	
	public boolean shootingUp;
	public boolean shootingDown;
	public boolean shootingLeft;
	public boolean shootingRight;

	protected Animation ani;
	
	public static int animIndex = 0;
	
	private static int walkSpeed = 3;
	
	protected int health_points;
	protected int base_armor;
	protected int base_damage;
	protected int base_resistance;
	protected int cast_speed;
	protected int max_hp;
	protected int max_mana;
	protected int mana_points;
	
	protected int animationDelayDefault;
	
	protected AABB hitBox;
	
	public Mob(Sprite sprite, Vector2f origin, int size) {
		super(sprite, origin, size);
		this.sprite = sprite;
		pos = origin;
			
		ani = new Animation();
		tc = new TileCollision(this);
		
		this.max_hp = 100;
		this.health_points = max_hp;
		this.base_armor = 1;
		this.base_damage = 5;
		this.base_resistance = 10;
		this.animationDelayDefault = 7;
		
	}
	
	public Animation getAnimation() {
		return ani;
	}
	
	
	  public void animate() {
		
		  if(inWater) {
			  animIndex = 9;
			  walkSpeed = 1;
		  } else {
			  animIndex = 0;
			  walkSpeed = 4;
		  }
		  
		  if(isShooting && !fallen) {
			  if(shootingDown) {
				  if(currentAnimation != SHOOTING_DOWN || ani.getDelay() == -1) {
					  setAnimation(SHOOTING_DOWN, sprite.getSpriteArray(SHOOTING_DOWN), animationDelayDefault);
				  }
			  } else if(shootingUp) {
				  if(currentAnimation != SHOOTING_UP || ani.getDelay() == -1) {
					  setAnimation(SHOOTING_UP, sprite.getSpriteArray(SHOOTING_UP), animationDelayDefault);
				  }
			  } else if(shootingLeft) {
				  if(currentAnimation != SHOOTING_LEFT || ani.getDelay() == -1) {
					  setAnimation(SHOOTING_LEFT, sprite.getSpriteArray(SHOOTING_LEFT), animationDelayDefault);
				  }
			  } else if(shootingRight) {
				  if(currentAnimation != SHOOTING_RIGHT || ani.getDelay() == -1) {
					  setAnimation(SHOOTING_RIGHT, sprite.getSpriteArray(SHOOTING_RIGHT), animationDelayDefault);
				  }
			  }
		  }  
		  
		  if(up) {
	            if(currentAnimation != UP || currentAnimation != (UP + animIndex) || ani.getDelay() == -1) {
	            	if(!isShooting) {
		            	setAnimation(UP + animIndex, sprite.getSpriteArray(UP + animIndex), animationDelayDefault);
		            }
	            }	
	        } else if(down) {
	            if(currentAnimation != DOWN || currentAnimation != (DOWN + animIndex) || ani.getDelay() == -1) {
	            	if(!isShooting) {
	            	setAnimation(DOWN + animIndex, sprite.getSpriteArray(DOWN + animIndex), animationDelayDefault);
	            	}	
	            }
	        } else if(left) {
	            if(currentAnimation != LEFT || currentAnimation != (LEFT + animIndex) || ani.getDelay() == -1) {
	            	if(!isShooting) {
	            		setAnimation(LEFT + animIndex, sprite.getSpriteArray(LEFT + animIndex), animationDelayDefault);
	            	}
	            }	
	        } else if(right) {
	            if(currentAnimation != RIGHT || currentAnimation != (RIGHT + animIndex) || ani.getDelay() == -1) {
	            	if(!isShooting) {
	            		setAnimation(RIGHT + animIndex, sprite.getSpriteArray(RIGHT + animIndex), animationDelayDefault);
	            	}
	            }	
	        } else if(fallen) {
	            if(currentAnimation != FALLEN || ani.getDelay() == -1) {
	                setAnimation(FALLEN, sprite.getSpriteArray(FALLEN), animationDelayDefault);
	            	} 
	        	}
	         else {
	            setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);
	        }
	  }
	  
	  
	public void setAnimation(int i, BufferedImage[] frames, int delay) {
		currentAnimation = i;
		ani.setFrames(frames);
		ani.setDelay(delay);
	}
	  
	private void setHitBoxDirection() {
		if(up) {
			hitBounds.setYOffset(-size / 2);
			hitBounds.setXOffset(-size / 2);
		}
		else if(down) {
			hitBounds.setYOffset(size / 2);
			hitBounds.setXOffset(-size / 2);
		}
		else if(left) {
			hitBounds.setYOffset(-size);
			hitBounds.setXOffset(0);
		}
		else if(right) {
			hitBounds.setYOffset(0);
			hitBounds.setXOffset(0);
		}
	}
	
	public int getMana() {
		return mana_points;
	}
	
	public int getMaxMana() {
		return max_mana;
	}
	
	public int getMaxHP() {
		return max_hp;
	}
	
	public BufferedImage flashWhiteHit() {
		BufferedImage currentFrame = ani.getImage();	
		BufferedImage newImage = new BufferedImage(currentFrame.getWidth(), currentFrame.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < currentFrame.getHeight(); y++) {
			for (int x = 0; x < currentFrame.getWidth(); x++) {
				int  clr   = currentFrame.getRGB(x, y); 

				if(clr != 0x00) {

					newImage.setRGB(x, y, 0xFFFFFFFF);

				} 
			}
		}

		return newImage;
	}


	public void update() {
		animate();
		setHitBoxDirection();
		ani.update();
	}
	
	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

}
