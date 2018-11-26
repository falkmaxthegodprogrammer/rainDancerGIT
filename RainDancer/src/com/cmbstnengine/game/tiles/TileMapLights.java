package com.cmbstnengine.game.tiles;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.cmbstnengine.game.entity.Player;
import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.tiles.blocks.LightBlock;
import com.cmbstnengine.game.util.Vector2f;

public class TileMapLights {

	private ArrayList<LightBlock> lightBlocks;
	public Vector2f vec;
	public Player player;
	
	public TileMapLights(String data, Sprite sprite, int width, int height, int tileWidth, int tileHeight, int tileColumns) {
		lightBlocks = new ArrayList<LightBlock>();	
		
		String[] block = data.split(",");
		for(int i = 0; i < (width * height); i++) {
			int temp = Integer.parseInt(block[i].replaceAll("\\s+",""));
			if(temp != 0) {
				LightBlock lightBlock = new LightBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns)), new Vector2f((int) (i % width) * tileWidth, (int) (i / height) * tileHeight), tileWidth, tileHeight);
				lightBlocks.add(lightBlock);
			}
		}
	}
	
	public void render(Graphics2D g) {
		for(int i = 0; i < lightBlocks.size(); i++) {
			lightBlocks.get(i).render(g);
		}
	}
	
	
	
}

	

