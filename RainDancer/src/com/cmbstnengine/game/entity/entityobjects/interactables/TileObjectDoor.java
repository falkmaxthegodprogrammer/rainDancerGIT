package com.cmbstnengine.game.entity.entityobjects.interactables;

import java.awt.Graphics2D;
import java.util.regex.Pattern;

import com.cmbstnengine.game.audio.AudioClip;
import com.cmbstnengine.game.audio.AudioPlayer;
import com.cmbstnengine.game.entity.Player;
import com.cmbstnengine.game.entity.entityobjects.TileObject;
import com.cmbstnengine.game.entity.entityobjects.ObjectType.ObjType;
import com.cmbstnengine.game.graphics.Sprite;
import com.cmbstnengine.game.util.AABB;
import com.cmbstnengine.game.util.TileCollision;
import com.cmbstnengine.game.util.Vector2f;
import com.cmbstnengine.game.world.WorldMap;

public class TileObjectDoor extends TileObject {
	
	public boolean opened = false;
	public boolean gateway;
	public String leadsTo;
	
	private int xPos;
	private int yPos;
	
	private String[] data;
	
	private AudioClip open_door_1 = new AudioClip("res/SFX/RPG Sound Pack/world/door.wav");

	public TileObjectDoor(Sprite sprite, Vector2f origin, int size, int gridID, int tileColumns, int tileWidth,
			int tileHeight, int objectWidth, int objectHeight, String type, String name) {
		super(sprite, origin, size, gridID, tileColumns, tileWidth, tileHeight, objectWidth, objectHeight, type, name);
		
		bounds = new AABB(origin, this.objectWidth, this.objectHeight);
	    hitBounds = new AABB(origin, this.objectWidth, this.objectHeight);		
	    tc = new TileCollision(this);
	    sense = new AABB(new Vector2f(origin.x - r / 4, origin.y - r / 4), r, this);	    
	    data = name.split(",");
	    leadsTo = data[0];
	    
	    this.objectType = ObjType.INTERACTABLE;	    
	}
	
	public boolean update(AABB p) {
		return false;
	}
	
	public Vector2f getPos() { return pos; }
	
	@Override
	public void interact(Player player) {
		WorldMap.loadTileMap(leadsTo, Integer.parseInt(data[1]), Integer.parseInt(data[2]));
		AudioPlayer.playSound(open_door_1, 0.49);	
		System.out.println("Loading " + leadsTo);
		System.out.println(name);
		return;
	}
	
	public int getGid() {
		return gid;
	}

	public AABB getSense() {
		return sense;
	}

	public void setSense(AABB sense) {
		this.sense = sense;
	}
	
	public String toString() {
		return "I am a chest";
	}
	
	@Override
	public void render(Graphics2D g) {
		if(!hide && Player.isWithinBounds((int) pos.x, (int) pos.y, (int) pos.x, (int) pos.y)) {
			super.render(g);
		}
	}

}
