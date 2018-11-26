package com.cmbstnengine.game.util;

public class Vector2f {

	public float x;
	public float y;
	
	public static float worldX;
	public static float worldY;
	
	public Vector2f() {
		x = 0;
		y = 0;	
	}
	
	public Vector2f(Vector2f vec) {
		new Vector2f(vec.x, vec.y);
	}
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void addX(float x) {
		x += x; 
	}
	
	public void addY(float y) {
		y += y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setVector(Vector2f vec) {
		this.x = vec.x;
		this.y = vec.y;
	}
	
	public void setVector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public static void setWorldVar(float x, float y) {
		worldX = x;
		worldY = y;
	}
	
	public Vector2f add(Vector2f vector) {
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}
	
	public Vector2f add(int value) {
		this.x += value;
		this.y += value;
		return this;
	}
	
	public Vector2f getWorldVar() {
		return new Vector2f(x - worldX, y - worldY);
	}
	
	@Override
	public String toString() {
		return x + " " + y;
	}
	
}
