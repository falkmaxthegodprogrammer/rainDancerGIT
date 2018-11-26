package com.cmbstnengine.game.util;

public class Vector2i {

	public int x;
	public int y;
	
	public static int worldX;
	public static int worldY;
	
	public Vector2i() {
		x = 0;
		y = 0;	
	}
	
	public Vector2i(Vector2i vec) {
		new Vector2i(vec.x, vec.y);
	}
	
	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void addX(int x) {
		x += x; 
	}
	
	public void addY(int y) {
		y += y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setVector(Vector2i vec) {
		this.x = vec.x;
		this.y = vec.y;
	}
	
	public void setVector(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public static void setWorldVar(int x, int y) {
		worldX = x;
		worldY = y;
	}
	
	public Vector2i add(Vector2i vector) {
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}
	
	public Vector2i add(int value) {
		this.x += value;
		this.y += value;
		return this;
	}
	
	public Vector2i getWorldVar() {
		return new Vector2i(x - worldX, y - worldY);
	}
	
	@Override
	public String toString() {
		return x + " " + y;
	}
	
}
