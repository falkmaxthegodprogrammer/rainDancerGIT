package com.cmbstnengine.game.ui.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import com.cmbstnengine.game.GamePanel;
import com.cmbstnengine.game.graphics.GFont;
import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.util.MouseHandler;
import com.cmbstnengine.game.util.Vector2f;
import com.cmbstnengine.game.util.Vector2i;

public class GButton extends GComponent {
	
	public GLabel label;
	protected GButtonListener buttonListener;
	protected GActionListener actionListener;
	
	protected BufferedImage image;
	private BufferedImage highlightedImage;
	protected BufferedImage renderedImage;
	
	protected boolean inside = false;
	protected boolean pressed = false;
	protected boolean ignorePressed = false;
	protected boolean ignoreAction = false;
	protected Color defaultColor;
	
	public GButton(Vector2i pos, Vector2i size) {
		super(pos,size);
		this.defaultColor = Color.DARK_GRAY;
		init();
	}
	
	public GButton(Vector2i pos, Vector2i size, GActionListener actionListener) {
		super(pos,size);
		this.actionListener = actionListener;
		this.defaultColor = Color.DARK_GRAY;
		init();
	}
	
	public GButton(Vector2i pos, Vector2i size, BufferedImage image) {
		super(pos,size);
		this.defaultColor = Color.DARK_GRAY;
		this.image = image;
		this.renderedImage = image;
		this.setHighlightedImage(Sprite.highlight(this.image));
		init();
	}
	
	public GButton(Vector2i pos, Vector2i size, BufferedImage image, GActionListener actionListener) {
		super(pos,size);
		this.actionListener = actionListener;
		this.defaultColor = Color.DARK_GRAY;
		this.image = image;
		this.renderedImage = image;
		this.setHighlightedImage(Sprite.highlight(this.image));
		init();
	}
	
	
	public void addActionListener(GActionListener actionListener) {
		this.actionListener = actionListener;
	}
	
	public void setColor(int x) {
		this.color = new Color(x);
	}
	
	public void setDefaultColor(int x) {
		this.defaultColor = new Color(x);
	}
	
	public BufferedImage highlight(BufferedImage img) {
		BufferedImage currentImg = img;
		BufferedImage imageToReturn = new BufferedImage(currentImg.getWidth(), currentImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		for(int y = 0; y < currentImg.getHeight(); y++) {
			for(int x = 0; x < currentImg.getWidth(); x++) {
				int clr = currentImg.getRGB(x, y);
					if(clr != 0x00) {
						imageToReturn.setRGB(x, y, 0x99AAAABB);
					}
			}
		}
		return imageToReturn;	
	}
	
	public void setDefaultColor(Color color) {
		this.defaultColor = color;
		this.color = color;
	}
	
	public void setColor(Color color) {
		this.color = (color);
	}
	
	public void setRenderedImage(BufferedImage img) {
		this.renderedImage = img;
	}
	
	public void setText(String text) {
		this.label = new GLabel(new Vector2i(pos.x + size.x / 2, pos.y), text);	}
	
	public void setText(String text, Color color) {
		this.label = new GLabel(new Vector2i(pos.x + size.x / 2, pos.y), text, color);
	}
	
	public void setFont(GFont font) {
		this.label.setGameFont(font);
	}
	
	private void init() {
		setColor(defaultColor);
		super.init(panel);
		buttonListener = new GButtonListener();
	}
	
	void init(GPanel panel) {
		super.init(panel);
		if(label != null) {
			panel.addComponent(label);
		}
	}
	
	public void update() {
		if(!isHidden()) {
			Rectangle bounds = new Rectangle(pos.x, pos.y, size.x, size.y);
			boolean leftMouse = MouseHandler.getButton() == MouseEvent.BUTTON1;		
			if(bounds.contains(new Point(MouseHandler.getX(), MouseHandler.getY()))) {
				if(!inside) {		
					if(leftMouse) {
						ignorePressed = true;
					} else {
						ignorePressed = false;					
					}
				buttonListener.entered(this);
				} 
				inside = true;
				if(!pressed && !ignorePressed && leftMouse) {
					buttonListener.pressed(this);
					pressed = true;
				} else if(MouseHandler.getButton() == MouseEvent.NOBUTTON) {
					if(pressed) {
						buttonListener.released(this);
							if(!ignoreAction) 
								actionListener.actionPerformed();
							 else 
								ignoreAction = false;
							 pressed = false;
						}	
						ignorePressed = false;
					}
				} else {
					if(inside) {
						buttonListener.exited(this);
						pressed = false;
					}
					inside = false;
				}
			}
		}		
		
	public void render(Graphics2D g) {
		if(image != null) {
			g.drawImage(renderedImage, (int) pos.x, (int) pos.y, (int) size.x, (int) size.y, null);
		} else {
			g.setColor(color);
			g.fillRect((int) pos.x, (int) pos.y, (int) size.x, (int) size.y); 			
		}
		if(isShowBorder()) {
			g.setColor(Color.BLACK);
			g.drawRect((int) pos.x -1, (int) pos.y -1, (int) size.x + 1, (int) size.y + 1); 
		}
		
		if(label != null) {
			label.render(g);
		}	
	}

	public void setButtonListener(GButtonListener gButtonListener) {
		this.buttonListener = gButtonListener;
	}

	public Color getDefaultColor() {
		return defaultColor;
	}

	public BufferedImage getHighlightedImage() {
		return highlightedImage;
	}

	public void setHighlightedImage(BufferedImage highlightedImage) {
		this.highlightedImage = highlightedImage;
	}
	
	
	
	
	
	
	
	

}
