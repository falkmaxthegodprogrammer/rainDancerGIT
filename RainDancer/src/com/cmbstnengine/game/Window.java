package com.cmbstnengine.game;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.cmbstnengine.game.entity.Player;
import com.cmbstnengine.game.states.PauseState;
import com.cmbstnengine.game.tiles.LightMap;

public class Window extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GamePanel game;
	private int screenWidth;
	private int screenHeight;
	public static boolean fullscreen = false;
	private static final int DEFAULT_WIDTH = 1024;
	private static final int DEFAULT_HEIGHT = 576;
	
	private static int DEFAULT_WINDOW_WIDTH;
	private static int DEFAULT_WINDOW_HEIGHT;
	
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Image cursorImage = toolkit.getImage("res/cursors/cursor_1/Grey.png");
	
	public Window() {
		game = new GamePanel(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setTitle("Rainmaker");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		add(game);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);	
		//setFullscreen(true);
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		this.screenWidth = gd.getDisplayMode().getWidth();
		this.screenHeight = gd.getDisplayMode().getHeight();
		
		PauseState.window = this;
		
		Point cursorHotSpot = new Point(0, 0);
		Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
		setCursor(customCursor);
		
		DEFAULT_WINDOW_WIDTH = this.getWidth();
		DEFAULT_WINDOW_HEIGHT = this.getHeight();
		
		addWindowListener(new WindowAdapter() {
			public void windowStateChanged(WindowEvent e) {

			}
		});
	}
		
	public void setFullscreen(boolean f) {
		game.setFullscreen(true, screenWidth, screenHeight);
		fullscreen = true;
		if(f) {
			dispose();
			setUndecorated(true);
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			setVisible(true);
		} if(!f) unFullscreen();		
	}
	
	public void unFullscreen() {
		fullscreen = false;
			dispose();
			setUndecorated(false);
			setExtendedState(JFrame.NORMAL);
			setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
			setLocationRelativeTo(null);
			setVisible(true);	
			Player.playerBounds.width = GamePanel.screenWidth;
			Player.playerBounds.height = GamePanel.screenHeight;
			Player.playerBoundsRight.width = GamePanel.screenWidth;
			Player.playerBoundsRight.height = GamePanel.screenHeight;
		game.setFullscreen(false, GamePanel.width, GamePanel.height);
	}

	public void toggleFullscreen() {
		if(fullscreen) {
			Player.playerBounds.width = GamePanel.width;
			Player.playerBounds.height = GamePanel.height;
			Player.playerBoundsRight.width = GamePanel.width;
			Player.playerBoundsRight.height = GamePanel.screenHeight;
			setFullscreen(false);
		} else if(!fullscreen) {
			setFullscreen(true);
		}
	}
	
	
}
