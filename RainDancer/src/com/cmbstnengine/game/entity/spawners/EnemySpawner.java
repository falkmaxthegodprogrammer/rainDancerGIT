package com.cmbstnengine.game.entity.spawners;

import com.cmbstnengine.game.entity.Enemy;
import com.cmbstnengine.game.entity.Spawner;
import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.util.Vector2f;

public class EnemySpawner extends Spawner {

	//public Enemy enemy;
	
	
	public EnemySpawner(Sprite sprite, Vector2f origin, int size, Type type) {
		super(sprite, origin, size, Type.ENEMY);
		// TODO Auto-generated constructor stub
	}

	public void spawnEnemies(int amount) {
		for(int i = 0; i < amount; i++) {
		//	Enemy e = new Enemy()
		}
	}
	
	
	
}
