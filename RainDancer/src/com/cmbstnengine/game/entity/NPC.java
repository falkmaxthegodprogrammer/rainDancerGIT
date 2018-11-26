package com.cmbstnengine.game.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Random;

import com.cmbstnengine.game.audio.AudioClip;
import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.ui.UI;
import com.cmbstnengine.game.util.AABB;
import com.cmbstnengine.game.util.Vector2f;

public class NPC extends Mob {
	
	private AABB sense;
	private int r;
	private AABB hitBox;
	private Random random;
	private HashMap<String, AudioClip> sfx;
	private String name;
	private boolean isShopkeeper;
	private String[] dialogReplies;
	private String[] dialogLines;
	
	public NPC(Sprite sprite, Vector2f origin, int size, String name, boolean isShopkeeper) {
		super(sprite, origin, size);
		
		setDialogLines(new String[]{"Hej vad heter du?", "Okej, jag heter Link", "GG", "blabla"});
		setDialogReplies(new String[]{"1. Mitt namn är Max! ", "2", "Buy", "Exit", "5"});
		
		random = new Random(100);
		this.name = name;
		this.isShopkeeper = isShopkeeper;
		
		acc = 1f;
		maxSpeed = 2f;
		this.r = size - (size / 8);
		
		this.size = size;
		
		bounds.setWidth(super.size / 3);
		bounds.setHeight(super.size / 8);
		bounds.setXOffset(super.size / 3 - (super.size >> 6));
		bounds.setYOffset(super.size - super.size / 10);
		
		sense = new AABB(new Vector2f(origin.x - r / 2, origin.y - r / 2), r * 2, this);
		
		hitBox = new AABB(origin, this.size, this.size);
		hitBox.setWidth(super.size / 3);
        hitBox.setXOffset(this.size);
		hitBox.setHeight(super.size / 2);
		hitBox.setYOffset(this.size);
		
		setAnimation(DOWN, sprite.getSpriteArray(DOWN), 10);
	}
	
	public void update(Player player) {
		super.update();	
		if(sense.colCircleBox2(player.getBounds())) {
			Player.lastInteractableNPC = this;
		} 
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
	
	public AABB getHitBox() { 
		return hitBox; 
	}
	
	 public boolean isInsideSense(AABB p) {
	       if(p.getPos().x + p.getXOffset() < pos.x - size) return false;
	       if(p.getPos().y + p.getYOffset() < pos.y - size) return false;
	       if(size + size + pos.x < p.getWidth() + (p.getPos().x + p.getXOffset())) return false;
	       if(size + size + pos.y < p.getHeight() + (p.getPos().y + p.getYOffset())) return false;   
	       return true;
	}
	
	@Override
	public void render(Graphics2D g) {
		if(!isRemoved) {
			g.drawImage(ani.getImage(), (int) pos.getWorldVar().x, (int) pos.getWorldVar().y, size, size, null);
		//	g.setColor(Color.WHITE);
		//	g.drawOval((int) sense.getPos().getWorldVar().x,(int)  sense.getPos().getWorldVar().y,(int)  sense.getRadius(),(int)  sense.getRadius());
			g.setColor(Color.WHITE);
		//	g.setFont(new Font("Verdana", Font.PLAIN, 16));
			g.drawString(name, (int) pos.getWorldVar().x + size / 2 - name.length(), (int) pos.getWorldVar().y);
		}
	}

	public void interact(Player player) {
		String condition = null;
		if(isShopkeeper) {
			condition = "something";
		} else {
			condition = "nothing";
		}
		UI.showDialog(this);
	}

	public String[] getDialogReplies() {
		return dialogReplies;
	}

	public void setDialogReplies(String[] dialogOptions) {
		this.dialogReplies = dialogOptions;
	}

	public String[] getDialogLines() {
		return dialogLines;
	}

	public void setDialogLines(String[] dialogLines) {
		this.dialogLines = dialogLines;
	}
	
	
	

}
	
	
	


