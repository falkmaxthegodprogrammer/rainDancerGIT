package com.cmbstnengine.game.ui.components;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.cmbstnengine.game.util.Vector2f;
import com.cmbstnengine.game.util.Vector2i;

public class GPanel extends GComponent {

	private List<GComponent> components = new ArrayList<>();
	private List<GPanel> panels = new ArrayList<>();

	public Vector2i size;
	private Color borderColor = new Color(0x000000);
	private int borderThickness = 1;
	private BufferedImage bgImage;
	private boolean showBorder;
	private boolean renderMe = true;
	
	public GPanel(Vector2i pos, Vector2i size) {
		super(pos);
		this.size = size;
		this.showBorder = true;
		// TODO Auto-generated constructor stub
	}
	
	public GPanel(Vector2i pos, Vector2i size, BufferedImage bgImage) {
		super(pos);
		this.size = size;
		this.bgImage = bgImage;
	}
	
	public void setBGImage(BufferedImage bgImage) {
		this.bgImage = bgImage;
	}
	
	public BufferedImage getBGImage() {
		return bgImage;
	}
	
	public void showBorder(boolean b) {
		this.showBorder = b;
	}
	
	public void update() {
		for(GPanel panel : panels) {
			panel.update();
		}
		for(GComponent component : components) {
			component.update();
		}
	}
	
	public void setBorder(int x, Color color) {
		this.borderColor = color;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) size.x, (int) size.y);
	}
	
	public Vector2i getPos() {
		return pos;
	}
	
	public void renderSubPanels(Graphics2D g) {
		for(GPanel p : panels) {
			p.render(g);
		}
	}
	
	public void render(Graphics2D g) {		
		if(!hidden) {		
			
			if(renderMe) {
				if(bgImage == null) {
					g.setColor(color);
					g.fillRect((int) pos.x, (int) pos.y, (int) size.x, (int) size.y); 	
				}	
				
					if(bgImage != null) {
						g.drawImage(bgImage, (int) pos.x, (int) pos.y, (int) size.x, (int) size.y, null);
					}
				}
								
			for(GComponent component : components) {
				component.render(g);
			}
			
			renderSubPanels(g);
			
			if(showBorder) {
				g.setColor(borderColor);
				g.drawRect((int) pos.x - borderThickness, (int) pos.y - borderThickness, (int) size.x + borderThickness, (int) size.y + borderThickness); 
			}
			
		}
	}
	
	@Override 
	public void setHidden(boolean b) {
		this.hidden = b;
		for(GComponent c : components) {
			c.setHidden(b);
		}
	}

	public void addComponent(GComponent component) {
		component.init(this);
		components.add(component);
		if(component instanceof GLabel) {
			GLabel label = (GLabel) component;
			label.setPos(pos.x, pos.y);
		}
	}
	
	public void setPanelsHidden(boolean hidden) {
		this.hidden = hidden;
		for(GPanel p : panels) {
			p.setHidden(hidden);
		}
	}

	public void addPanel(GPanel panel) {
		panel.init(this);
		panels.add(panel);
		panel.setHidden(hidden);
	}
	
	public List<GPanel> getPanels() {
		return panels;
	}
	
	public List<GComponent> getComponents() {
		return components;
	}

	public boolean isRenderMe() {
		return renderMe;
	}

	public void setRenderMe(boolean renderMe) {
		this.renderMe = renderMe;
	}

	
	
	
	

}
