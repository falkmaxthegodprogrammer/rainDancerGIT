package com.cmbstnengine.game.tiles;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.cmbstnengine.game.entity.Player;
import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.tiles.blocks.Block;
import com.cmbstnengine.game.tiles.blocks.NormBlock;
import com.cmbstnengine.game.util.Vector2f;

public class TileMapEntity extends TileMap {
	
	private ArrayList<Block> blocks;
	
	public TileMapEntity(String data, Sprite sprite, int width, int height, int tileWidth, int tileHeight, int tileColumns) {
		blocks = new ArrayList<Block>();
		
		String[] block = data.split(",");
		for(int i = 0; i < (width * height); i++) {
			int temp = Integer.parseInt(block[i].replaceAll("\\s+",""));
			if(temp != 0) {
				blocks.add(new NormBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns)), new Vector2f((int) (i % width) * tileWidth, (int) (i / height) * tileHeight), tileWidth, tileHeight));			
			}
		}
	}
	
	public void clear() {
		blocks.clear();
	}
	
	public void render(Graphics2D g) {
		for(int i = 0; i < blocks.size(); i++) {
				blocks.get(i).render(g);
			}
	}
	

}
