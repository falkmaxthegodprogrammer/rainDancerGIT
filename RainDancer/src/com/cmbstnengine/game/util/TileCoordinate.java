package com.cmbstnengine.game.util;

public class TileCoordinate {
	
	public int x;
	public int y;
	
	public TileCoordinate() {
		
	}

	public static Vector2i getCurrentTile(Vector2f pos) {
		return new Vector2i((int) pos.x / 64, (int) pos.y / 64);
	}
	
	public static Vector2f getTileCoordinate(int x, int y) {
		return new Vector2f(x * 64, y * 64);
	}
	
	public static Vector2f getTileCoordinate(Vector2f pos) {
		return new Vector2f(pos.x * 64, pos.y * 64);
	}
	
}
