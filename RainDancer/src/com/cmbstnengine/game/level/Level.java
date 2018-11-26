package com.cmbstnengine.game.level;

import java.awt.Graphics2D;

import com.cmbstnengine.game.tiles.TileManager;

public class Level {
	
	private TileManager tm;
	
	private static TileManager[] levels = new TileManager[100];
	
	private static TileManager map1 = new TileManager("tile/cthulustemple.xml");
	private static TileManager map2 = new TileManager("tile/cthulustemple.xml");
	private static TileManager map3 = new TileManager("tile/cthulustemple.xml");
	private static TileManager map4 = new TileManager("tile/cthulustemple.xml");
	private static TileManager map5 = new TileManager("tile/cthulustemple.xml");

	public Level() {
		
	}
	
	public void render(Graphics2D g) {
		tm.render(g);
	}
	
	

}
