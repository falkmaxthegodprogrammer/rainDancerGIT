package com.cmbstnengine.game.world;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;

import com.cmbstnengine.game.GamePanel;
import com.cmbstnengine.game.entity.Player;
import com.cmbstnengine.game.states.PlayState;
import com.cmbstnengine.game.tiles.TileManager;
import com.cmbstnengine.game.util.Vector2f;

public class WorldMap {
	
	public static TileManager currentTileMap;
	public static Collection<TileManager> loadedChunks = new ArrayList<>();;
	
	public WorldMap() {
		currentTileMap = new TileManager("maps/arcademap_newtilesheet.xml");
	}
	
	public WorldMap(String path) {
		currentTileMap = new TileManager(path);
	}
	
	public void loadMapIntoMemory(String path) {
		currentTileMap.addTileMap(path, 64, 64);
	}
	
	public void update(Player player) {
		currentTileMap.update(player);	
	}
	
	public static void loadTileMap(String path) {	
		PlayState.tileObjects.clear();
		PlayState.entities.clear();
		currentTileMap.clearDataStructures();
		currentTileMap = new TileManager(path);		
	}
	
	public static void loadTileMap(String path, int x, int y) {	
		long lastTime = System.currentTimeMillis();
		PlayState.loading = true;
		PlayState.tileObjects.clear();
		PlayState.entities.clear();
		PlayState.enemies.clear();
		currentTileMap.clearDataStructures();
		currentTileMap = new TileManager(path);
		PlayState.setWorldPos(((x * 64) + 48) - GamePanel.width / 2, ((y * 64) + 48) - GamePanel.height / 2);	
		PlayState.loading = false;
		long now = System.currentTimeMillis();
		System.out.println("Loading complete... Loading took: " + (now - lastTime) + "ms");
	}
	
	public void renderBottomLayers(Graphics2D g) {
		currentTileMap.render(g);
	}
	
	public void renderTopLayers(Graphics2D g) {
		currentTileMap.renderUpperLayers(g);
	}
	
	public static void renderAll(Graphics2D g) {
		currentTileMap.render(g);
		currentTileMap.renderUpperLayers(g);
	}
	
	
	
}
