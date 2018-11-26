package com.cmbstnengine.game.util;

import com.cmbstnengine.game.entity.Enemy;
import com.cmbstnengine.game.entity.Entity;
import com.cmbstnengine.game.entity.NPC;
import com.cmbstnengine.game.entity.Player;
import com.cmbstnengine.game.entity.Projectile;
import com.cmbstnengine.game.entity.entityobjects.TileObject;
import com.cmbstnengine.game.states.PlayState;
import com.cmbstnengine.game.tiles.TileMapObj;
import com.cmbstnengine.game.tiles.blocks.Block;
import com.cmbstnengine.game.tiles.blocks.HoleBlock;
import com.cmbstnengine.game.tiles.blocks.WaterBlock;

public class TileCollision {

	private Entity e;    

	public TileCollision(Entity e) {
		this.e = e;
	}

	public boolean collisionTile(float ax, float ay) {
		if(e.getNoClip()) {
			return false;
		}
		
		for(int c = 0; c < 4; c++) {

			int xt = (int) ( (e.getBounds().getPos().x + ax) + (c % 2) * e.getBounds().getWidth() + e.getBounds().getXOffset()) / 64;
			int yt = (int) ( (e.getBounds().getPos().y + ay) + (c / 2) * e.getBounds().getHeight() + e.getBounds().getYOffset()) / 64;

			if(TileMapObj.tmo_blocks.containsKey(String.valueOf(xt) + "," + String.valueOf(yt))) {
				Block block = TileMapObj.tmo_blocks.get(String.valueOf(xt) + "," + String.valueOf(yt));
				if(block instanceof HoleBlock) {
					return collisionHole(ax, ay, xt, yt, block);
				} else if(block instanceof WaterBlock) {
					return collisionWater(ax, ay, xt, yt, block);
				}
				return block.update(e.getBounds());
			}
		e.setInWater(false);
		}
		return false;
	}
	
	public boolean collisionMob(float ax, float ay, Player player) {		
		for(Enemy enemy : PlayState.enemies) {
			if(player.getHitBox().collides(enemy.getHitBox())) {
				return true;
			}
		}
		
		for(NPC npc : PlayState.npcs) {
			if(player.getHitBox().collides(npc.getHitBox())) {
				return true;
			}
		}
		return false;
	}

	public boolean projectileCollisionEnemy(float ax, float ay, Projectile p) {
		for(Enemy enemy : PlayState.enemies) {        
			if(p.getBounds().collides(enemy.getHitBox())) {
				enemy.getHit(p);
				return true;    
			}
		}
		return false;    	
	}

	public boolean projectileCollisionPlayer(float ax, float ay, Projectile p) {
		Player player = PlayState.players.get(0);
		if(!(p.getWhoFired() instanceof Player) && p.getWhoFired() instanceof Enemy) { 
			if(p.getBounds().collides(player.getHitBox())) {
				player.getHit((Enemy) p.getWhoFired(), p);
				return true;
			}        
		}
		return false;    	
	}

	public boolean collisionPlayerMoving(float ax, float ay, Projectile p) {
		for(int c = 0; c < 4; c++) {

			Player player = PlayState.players.get(0);

			int xt = (int) ( (player.getHitBox().getPos().x + ax) + (c % 2) * player.getHitBox().getWidth() + player.getHitBox().getXOffset());
			int yt = (int) ( (player.getHitBox().getPos().y + ay) + (c / 2) * player.getHitBox().getHeight() + player.getHitBox().getYOffset());

			if(!(p.getWhoFired() instanceof Player) && p.getWhoFired() instanceof Enemy) {

				if(p.getBounds().collides(PlayState.players.get(0).getHitBox())) {
					return true;
				}        
			}
		}
		return false;    	
	}



	private boolean collisionHole(float ax, float ay, float xt, float yt, Block block) {
		int nextXt = (int) ((( (e.getBounds().getPos().x + ax) + e.getBounds().getXOffset()) / 64) + e.getBounds().getWidth() / 64);
		int nextYt = (int) ((( (e.getBounds().getPos().y + ay) + e.getBounds().getYOffset()) / 64) + e.getBounds().getHeight() / 64);

		if(block.isInside(e.getBounds())) {
			e.setFallen(true);
			return false;
		}

		else if((nextXt == yt + 1) || (nextXt == xt + 1) || (nextYt == yt - 1) || (nextXt == xt - 1)) {
			if(TileMapObj.tmo_blocks.containsKey(String.valueOf(nextXt) + "," + String.valueOf(nextYt))){
				if(e.getBounds().getPos().x > block.getPos().x) {
					e.setFallen(true);
				}
				return false;
			}
		}
		e.setFallen(false);
		return false;

	}

	private boolean collisionWater(float ax, float ay, float xt, float yt, Block block) {
		if(e instanceof Player) {
		int nextXt = (int) ((( (e.getBounds().getPos().x + ax) + e.getBounds().getXOffset()) / 64) + e.getBounds().getWidth() / 64);
		int nextYt = (int) ((( (e.getBounds().getPos().y + ay) + e.getBounds().getYOffset()) / 64) + e.getBounds().getHeight() / 64);

		if(block.isInside(e.getHitBox())) {
			e.setInWater(true);
			return false;
		}

		else if((nextXt == yt + 1) || (nextXt == xt + 1) || (nextYt == yt - 1) || (nextXt == xt - 1)) {
			if(TileMapObj.tmo_blocks.containsKey(String.valueOf(nextXt) + "," + String.valueOf(nextYt))){
				if(e.getHitBox().getPos().x >= block.getPos().x || e.getHitBox().getPos().y >= block.getPos().y) {
					e.setInWater(true);
				}
				e.setInWater(false);
				return false;
			}
		}
		e.setInWater(false);
		return false;
		}
		return false;
	}


	private boolean collisionEntity(float ax, float ay, float xt, float yt, TileObject te) {
		int nextXt = (int) ((( (e.getBounds().getPos().x + ax) + e.getBounds().getXOffset()) / 64) + e.getBounds().getWidth() / 64);
		int nextYt = (int) ((( (e.getBounds().getPos().y + ay) + e.getBounds().getYOffset()) / 64) + e.getBounds().getHeight() / 64);

		if(te.isInside(e.getBounds())) {
			e.setFallen(true);
			System.out.println("jag �r inne");
		//	return te.update(e.getBounds());
			//   return false;
		}

		else if((nextXt == yt + 1) || (nextXt == xt + 1) || (nextYt == yt - 1) || (nextXt == xt - 1)) {
			if(TileMapObj.tmo_blocks.containsKey(String.valueOf(nextXt) + "," + String.valueOf(nextYt))){
				if(e.getBounds().getPos().x > te.getPos().x) {
					System.out.println("jag �r inne");
				//	return te.update(e.getBounds());
				}
				return false;
			}
		}
		e.setFallen(false);
		return false;

	}
}