package com.cmbstnengine.game.ui.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.cmbstnengine.game.GamePanel;
import com.cmbstnengine.game.entity.entityobjects.TileObject;
import com.cmbstnengine.game.ui.UI;
import com.cmbstnengine.game.util.MouseHandler;
import com.cmbstnengine.game.util.Vector2f;
import com.cmbstnengine.game.util.Vector2i;

public class GScrollButton extends GButton {
	
	private boolean selected;
	private boolean isOccupied = false;	
	
	private BufferedImage imageIcon = null;;
	
	private ArrayList<TileObject> referencedObjects = new ArrayList<TileObject>();
	
	private Color border_color = Color.red;

	public GScrollButton(Vector2i pos, Vector2i size) {
		super(pos, size);
	}
	
	public GScrollButton(Vector2i pos, Vector2i size, BufferedImage image) {
		super(pos, size, image);
	}
	
	public GScrollButton(Vector2i pos, Vector2i size, GActionListener actionListener) {
		super(pos, size, actionListener);
	}
	
	public GScrollButton(Vector2i pos, Vector2i size, BufferedImage image,
			GActionListener gActionListener) {
		super(pos, size, image, gActionListener);
	}

	public void toggleSelected() {
		if(selected) selected = false;
		if(!selected) selected = true;
	}
	
	public boolean addItemToSlot(TileObject to) {
		if(referencedObjects.size() > 0) {
			if(!referencedObjects.get(0).getObjName().equals(to.getObjName())) {
				return false;
			}
		}
			referencedObjects.add(to);
			imageIcon = to.getSprite();
			return true;
	}

	public void removeTopItem() {
		if(referencedObjects.size() >= 1) {
			referencedObjects.remove(referencedObjects.size() - 1);
		}
	}
	
	public void setItem(Object o) {
	}

	
	public void setActionListenerMinecraft() {
		this.actionListener = new GActionListener() {
			@Override
			public void actionPerformed() {	
				if(!UI.draggingIcon && referencedObjects.size() == 0) return;
				if(UI.draggingIcon && isOccupied() && !UI.tempObjects.get(0).getName().equals(referencedObjects.get(0).getName())) {
					return;
				}	
				if(referencedObjects.size() > 0 && UI.tempObjects.size() == 0) {
					System.out.println("removed stack");
					UI.toggleDragging();
					UI.setDragItems(imageIcon, removeStack());
				} else if(!isOccupied()) {
					referencedObjects.addAll(UI.tempObjects);
					imageIcon = referencedObjects.get(0).getSprite();
					UI.toggleDragging();
					UI.tempObjects.clear();
				} else if(isOccupied() && UI.tempObjects.get(0).getObjName().equals(referencedObjects.get(0).getObjName())) {
					referencedObjects.addAll(UI.tempObjects);
					UI.tempObjects.clear();
					UI.toggleDragging();
				}	
			}
			

		};
	}
		
	public boolean isOccupied() {
		return referencedObjects.size() > 0;
	}		
	
	@Override
	public void update() {
		if(referencedObjects.size() == 0) imageIcon = null;
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
						if(!ignoreAction) {
							actionListener.actionPerformed();
							UI.unselectButtons();
							setSelected(true);
						}
						 else { 
							 ignoreAction = false;
						 }
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
	
	@Override
	public void render(Graphics2D g) {
		super.render(g);
		
		if(selected) {
			if(getHighlightedImage() == null) {
				g.setColor(border_color);
				g.drawRect((int) pos.x,(int) pos.y ,(int) size.x - 2,(int) size.y - 1);
			} else {
				g.drawImage(getHighlightedImage(), (int) pos.x, (int) pos.y, (int) size.x, (int) size.y, null);
			}
				
		}
		
		g.setColor(Color.WHITE);
		g.drawString(String.valueOf(referencedObjects.size()), (int) pos.x + 8, (int) pos.y + 18);
			
		if(imageIcon != null) {
			g.drawImage(imageIcon, ((int) pos.x + (int) (size.x / 4)), ((int) (pos.y + size.y / 4)), (int) size.x / 2, (int) size.y / 2, null);
		}
		
	/*	if(getItem() != null) {
		String itemName = getItem().getObjName();
			if(itemName != null) {
				g.drawString(itemName, pos.x, pos.y + size.y - 5);
			}
		}
		*/
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean b) {
		this.selected = b;
	}

	public TileObject getItem() {
		if(referencedObjects.size() == 0) {
			return null;
		}
		
		if(referencedObjects.size() >= 1) {
			return referencedObjects.get(referencedObjects.size() - 1);
		}
	return null;
	}

	public ArrayList<TileObject> removeStack() {
		ArrayList<TileObject> temp = new ArrayList<>();
		for(TileObject t: referencedObjects) {
			temp.add(t);
			imageIcon = null;
		}
		referencedObjects.clear();
		return temp;
	}


	
	
	

}
