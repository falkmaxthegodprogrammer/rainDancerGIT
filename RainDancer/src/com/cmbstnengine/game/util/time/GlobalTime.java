package com.cmbstnengine.game.util.time;

public class GlobalTime {
	
	private static long curTime;
	private static long lastTime;
	
	public static long getTime() {
		return System.nanoTime();
	}
	
	public static long getDelta() {
		return curTime - lastTime;
	}
	
	public static void update() {
		lastTime = curTime;
		curTime = getTime();
	}
	
	public static void init() {
		curTime = getTime();
		lastTime = getTime();
	}
		
}
