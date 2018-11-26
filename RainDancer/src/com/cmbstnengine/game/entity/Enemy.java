package com.cmbstnengine.game.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Random;

import com.cmbstnengine.game.GamePanel;
import com.cmbstnengine.game.audio.AudioClip;
import com.cmbstnengine.game.audio.AudioPlayer;
import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.states.PlayState;
import com.cmbstnengine.game.util.AABB;
import com.cmbstnengine.game.util.Vector2f;

public class Enemy extends Mob {
	
	private AABB sense;
	private int r;
	private boolean chasing;
	
	private AABB hitBox;
	public static final Sprite FIREBALL_PARTICLE = new Sprite("entity/fireball_particle.png", 8, 8);
	public static final Sprite DEATH_PARTICLE_1 = new Sprite("entity/ghost_particle.png", 8, 8);
	private Random random;
	
	private HashMap<String, AudioClip> sfx;
	private boolean flashWhite = false;
	
	public int timerCounter;

	
	public Enemy(Sprite sprite, Vector2f origin, int size) {
		super(sprite, origin, size);
		
		random = new Random(100);
		
		acc = 1f;
		maxSpeed = 2f;
		this.r = 400;
		
		this.size = size;
		
		bounds.setWidth(super.size / 3);
		bounds.setHeight(super.size / 8);
		bounds.setXOffset(super.size / 3 - (super.size >> 6));
		bounds.setYOffset(super.size - super.size / 10);
		
		sense = new AABB(new Vector2f(origin.x - r / 2 + size * 2, origin.y - r / 2 + size * 2), r, this);
		
		hitBox = new AABB(origin, this.size, this.size);
		hitBox.setWidth(super.size / 3);
        hitBox.setXOffset(this.size);
		hitBox.setHeight(super.size / 2);
		hitBox.setYOffset(this.size);
		
		setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 10);
		this.max_hp = 30;
		this.health_points = max_hp;
		
		this.timerCounter = 0;
	
		AudioClip hurtSound = new AudioClip("res/SFX/RPG Sound Pack/NPC/giant/giant3.wav");
			
		sfx = new HashMap<String, AudioClip>();		
		sfx.put("ogre_grunt_00", new AudioClip("res/SFX/RPG Sound Pack/NPC/giant/giant3.wav"));
		sfx.put("death_sound_00", new AudioClip("res/SFX/explosions/explosion07.wav"));			
	}
	
	
	public void update(Player player) {
		
		super.update();
	
		timerCounter++;
		
		if(!isRemoved) {
			if(cast_speed > 0) cast_speed--;
			if(sense.colCircleBox(player.getBounds())) {
				chasing = true;
			}
		}	
	
		if(health_points <= 0) {
			die();
			remove();
		}
		
		if(chasing) {
			if(cast_speed <= 0) {
				updateShooting(player.getPos().y - pos.y, player.getPos().x - pos.x);
			}
			getDirection(player.getBounds());
			move();
			
			if(!tc.collisionTile(dx, 0)) {
				pos.x += dx;
		//		pos.x += random.nextInt(2);
			}
			
			if(!tc.collisionTile(0, dy)) {
				pos.y += dy;
		//		pos.y += random.nextInt(2);
			}
		}

		if(timerCounter > 5) {
			timerCounter = 0;
		}
		
		if(timerCounter == 5) flashWhite = false;
		
		beenHit = false;		
	}
		
	private void getDirection(AABB player) {
		if(player.getPos().x > pos.x) right = true; else right = false;
		if(player.getPos().x < pos.x) left = true; else left = false;	
		if(player.getPos().y > pos.y) {
			down = true; 
			up = false; 
		} else {
			down = false;
		}
	
		if(player.getPos().y < pos.y) {
			up = true; 
			down = false; 
		} else {
			up = false;
		}
	}
	
	private void move() {
		if(up) {
			dy -= acc  + random.nextInt(4);
			if(dy < -maxSpeed) {
				dy = -maxSpeed + random.nextInt(4);
			}
		} else {
			if(dy < 0) {
				dy += deacc + random.nextInt(4);
				if(dy > 0) {
					dy = 0;
				}
			}
		}
		if(down) {
			dy += acc + random.nextInt(4);
			if(dy > maxSpeed) {
				dy = maxSpeed;
			}
		} else {
			if(dy > 0) {
				dy -= deacc + random.nextInt(4);
				if(dy < 0) {
					dy = 0;
				}
			}
		}
		if(left) {
			dx -= acc;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		} else {
			if(dx < 0) {
				dx += deacc;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		if(right) {
			dx += acc;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		} else {
			if(dx > 0) {
				dx -= deacc;
				if(dx < 0) {
					dx = 0;
				}
			}
		}
	}
	
	public void flashWhiteTimer() {
		
		
		
	}
	
	public void getHit(Projectile p) {
		if(p.getWhoFired() instanceof Player) {
			beenHit = true;
			flashWhite = true;
		}
		
		calcDamage(p);
		if(health_points % 6 == 0) {
			AudioPlayer.playSound(sfx.get("ogre_grunt_00"), 0.50);
		}
	}
	
	public void flashWhite() {
		ParticleSpawner effect = new ParticleSpawner(DEATH_PARTICLE_1, new Vector2f(pos.x, pos.y), 1, size, 1);
		PlayState.add(effect);
	}

	
	public void calcDamage(Projectile p) {
		health_points = health_points - p.getDamage();
	}

	public AABB getHitBox() { return hitBox; }
	
	public void die() {
		AudioPlayer.playSound(sfx.get("death_sound_00"), 0.52);
    	ParticleSpawner p = new ParticleSpawner(DEATH_PARTICLE_1, new Vector2f(pos.x + size / 2, pos.y + size / 2), 3, size / 4, 4);
	}
	
	public void shoot(double dir) {
		Fireball f = new Fireball(Fireball.FIREBALL_SPRITE2, new Vector2f(pos.x + (size / 4), pos.y + (size / 8)), 32, dir, this);
		PlayState.add(f);
	}

	public void updateShooting(float dy, float dx) {
		double dir = Math.atan2(dy, dx);
		shoot(dir);
		shoot(dir + 50);
		cast_speed = Fireball.FIRE_RATE;	
	}
	
	public float calcRedHpBarPercentage() {
		float percentage = ((float) health_points / max_hp);
		return percentage;
	}
	
	public void drawHealthBar(Graphics2D g) {			
		g.setColor(Color.GREEN);
		g.fillRect((int) (pos.getWorldVar().x + size / 4), (int) (pos.getWorldVar().y + size), size / 2, 2);
	}
	
	public void drawMissingHealthBar(Graphics2D g) {
		g.setColor(Color.RED);
		
		float originalSize = size / 2;
		float missingHpBarWidth = originalSize - originalSize * calcRedHpBarPercentage();

		g.fillRect((int) ((pos.getWorldVar().x + size / 4) + (originalSize - missingHpBarWidth + 1)), (int) (pos.getWorldVar().y + size),(int) missingHpBarWidth + 1, 2);
		
	}
	
	@Override
	public void render(Graphics2D g) {
		if(!isRemoved) {
				
			if(!flashWhite) {
				g.drawImage(ani.getImage(), (int) pos.getWorldVar().x, (int) pos.getWorldVar().y, size, size, null);
			}
			
			if(flashWhite) {
				g.drawImage(flashWhiteHit(), (int) pos.getWorldVar().x, (int) pos.getWorldVar().y, size, size, null);
			}
			
			drawHealthBar(g);
			if(health_points < max_hp) {
				drawMissingHealthBar(g);
			}

		}
	}

}
