package com.cmbstnengine.game.entity;

import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.states.PlayState;
import com.cmbstnengine.game.util.Vector2f;

public class ParticleSpawner extends Spawner {
	
	private int life;
	
	public ParticleSpawner(Sprite sprite, Vector2f origin, int life, int size, int amount) {
		super(sprite, origin, 10, Type.PARTICLE);
		this.life = life;
		for(int i = 0; i < amount; i++) {
			PlayState.add(new Particle(sprite, origin, size, life));		
			if(i == amount - 1) remove();
		}
	
	}	

	
}
