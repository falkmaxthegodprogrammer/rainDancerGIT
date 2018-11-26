package com.cmbstnengine.game.util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.cmbstnengine.game.GamePanel;
import com.cmbstnengine.game.events.EventListener;
import com.cmbstnengine.game.events.types.MouseMovedEvent;
import com.cmbstnengine.game.events.types.MousePressedEvent;
import com.cmbstnengine.game.events.types.MouseReleasedEvent;
import com.cmbstnengine.game.events.types.MouseWheelMovedEvent;


public class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener {
	
	private EventListener eventListener;
	
	public static int mouseX = -1;
	public static int mouseY = -1;
	private static int mouseB;	

	public MouseHandler(GamePanel game) {
		game.addMouseListener(this);
		game.addMouseMotionListener(this);
		game.addMouseWheelListener(this);
	}
	
	public MouseHandler(GamePanel game, EventListener eventListener) {
		game.addMouseListener(this);
		game.addMouseMotionListener(this);
		game.addMouseWheelListener(this);
		this.eventListener = eventListener;
	}
	
	public static int getX() {
		return mouseX;
	}
	
	public static int getY() {
		return mouseY;
	}

	public static int getButton() {
		return mouseB;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();	
		if(GamePanel.fullscreen) {
			mouseX = (int) (e.getX() / GamePanel.scaleWidth);
			mouseY = (int) (e.getY() / GamePanel.scaleHeight);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		mouseB = e.getButton(); //1 left, 2, middle, 3, right, 4, 5 etc sside buttons
		MousePressedEvent event = new MousePressedEvent(e.getButton(), getX(), getY());
		eventListener.onEvent(event);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();	
		if(GamePanel.fullscreen) {
			mouseX = (int) (e.getX() / GamePanel.scaleWidth);
			mouseY = (int) (e.getY() / GamePanel.scaleHeight);
			System.out.println(mouseX + " Y: " + mouseY);

		}

		MouseMovedEvent event = new MouseMovedEvent(getX(), getY(), false);
		eventListener.onEvent(event);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseB = -1;
		mouseB = MouseEvent.NOBUTTON;
		
		MouseReleasedEvent event = new MouseReleasedEvent(e.getButton(), getX(), getY());
		eventListener.onEvent(event);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		MouseWheelMovedEvent event = new MouseWheelMovedEvent(e.getWheelRotation());
		eventListener.onEvent(event);
		
	}

}
