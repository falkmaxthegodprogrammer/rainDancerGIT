package com.cmbstnengine.game.ui.components;

public class GButtonListener {

	public void entered(GButton button) {
		if(button.image != null) {
			button.setRenderedImage(button.getHighlightedImage());
			return;
		}
		button.setColor(0xcacaca);
	}
	
	public void exited(GButton button) {
		if(button.image != null) {
			button.setRenderedImage(button.image);
			return;
		}
		button.setColor(button.getDefaultColor());
	}
	
	public void pressed(GButton button) {
		if(button.image != null) {
			button.setRenderedImage(button.image);
			return;
		}
		button.setColor(0xcc2222);
	}
	
	public void released(GButton button) {
		if(button.image != null) {
			button.setRenderedImage(button.getHighlightedImage());
			return;
		}
		button.setColor(0xcacaca);
	}

}
