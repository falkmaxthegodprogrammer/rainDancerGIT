package com.cmbstnengine.game.tiles;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.tiles.blocks.Block;
import com.cmbstnengine.game.tiles.blocks.ObjBlock;
import com.cmbstnengine.game.tiles.blocks.WaterBlock;
import com.cmbstnengine.game.util.Vector2f;


public class TileMapObj extends TileMap {

    public static HashMap<String, Block> tmo_blocks = new HashMap<String, Block>();    
    public static ArrayList<Block> tmoBlocksList = new ArrayList<Block>();
    
    public TileMapObj(String data, Sprite sprite, int width, int height, int tileWidth, int tileHeight, int tileColumns) {
        Block tempBlock;
        String[] block = data.split(",");
        for(int i = 0; i < (width * height); i++) {
            int temp = Integer.parseInt(block[i].replaceAll("\\s+",""));
             if(temp != 0) {
                 if(temp == 2) {
                     tempBlock = new WaterBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns) ),new Vector2f((int) (i % width) * tileWidth, (int) (i / height) * tileHeight), tileWidth, tileHeight);
                 } else {
                     tempBlock = new ObjBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns) ),new Vector2f((int) (i % width) * tileWidth, (int) (i / height) * tileHeight), tileWidth, tileHeight);
                 }
                 tmo_blocks.put(String.valueOf((int) (i % width)) + "," + String.valueOf((int) (i / height)), tempBlock);
                 tmoBlocksList.add(tempBlock);
                 
             }
        }
    }

    public void render(Graphics2D g) { 
        for(int i = 0; i < tmoBlocksList.size(); i++) {
			tmoBlocksList.get(i).render(g);
        }
    }
    
	public void clear() {
		tmo_blocks.clear();
		tmoBlocksList.clear();
	}
	
}