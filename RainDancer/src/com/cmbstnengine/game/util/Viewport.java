package com.cmbstnengine.game.util;

import com.cmbstnengine.game.entity.Player;
import com.cmbstnengine.game.states.PlayState;

public class Viewport {
	
	public static Player player = PlayState.players.get(0);
	
	public Viewport() {
		
	}
	
	public static int minTileX() {
		int minTileX = (int) ((player.getPos().x / 64) - 16);
		if(minTileX > 0) {
			return minTileX;
		} else {
			return 0;
		}
	}
	
	public static int maxTileX() {
		int maxTileX = (int) ((player.getPos().x / 64) + 16);
		return maxTileX;
		
	}
	

}
