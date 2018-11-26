package com.cmbstnengine.game;

public class GameLauncher {
	
	Window wn;
	
	public GameLauncher() {
		wn = new Window();
	}
	
	public static void main(String[] args) {
		System.setProperty("sun.java2d.transaccel", "True");
		System.setProperty("sun.java2d.ddforcevram", "True");
		System.setProperty("sun.java2d.opengl", "True");	
		new GameLauncher();	
	}
	
	
}
