package com.cmbstnengine.game.entity.entityobjects;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.cmbstnengine.game.entity.Player;
import com.cmbstnengine.game.entity.entityobjects.HPItems.Carrot;
import com.cmbstnengine.game.entity.entityobjects.interactables.TileObjectChest;
import com.cmbstnengine.game.entity.entityobjects.interactables.TileObjectDoor;
import com.cmbstnengine.game.states.PlayState;
import com.cmbstnengine.game.util.Vector2f;

public class TileObjectManager {
	
	public static Map<String, TileObject> objectsMap = new TreeMap<>();
	
	public static void add(TileObjectData t) {
		
		TileObject tileObj = null;
			
		if(t.type.equalsIgnoreCase("chest")) {
			tileObj = new TileObjectChest(t.sprite, t.origin, t.size, t.gid, t.tileColumns, t.tileWidth, t.tileHeight, t.objectWidth, t.objectHeight, t.type, t.name);
		} else if(t.type.equalsIgnoreCase("door")) {
			tileObj = new TileObjectDoor(t.sprite, t.origin, t.size, t.gid, t.tileColumns, t.tileWidth, t.tileHeight, t.objectWidth, t.objectHeight, t.type, t.name);
		}  else if(t.type.equalsIgnoreCase("HPItem")) {
			if(t.name.equalsIgnoreCase("Carrot")) {
				tileObj = new Carrot(t.sprite, t.origin, t.size, t.gid, t.tileColumns, t.tileWidth, t.tileHeight, t.objectWidth, t.objectHeight, t.type, t.name, "Ripe Carrot");
			} else {
				tileObj = new HPItem(t.sprite, t.origin, t.size, t.gid, t.tileColumns, t.tileWidth, t.tileHeight, t.objectWidth, t.objectHeight, t.type, t.name);
			}
		} else {
			tileObj = new TileObject(t.sprite, t.origin, t.size, t.gid, t.tileColumns, t.tileWidth, t.tileHeight, t.objectWidth, t.objectHeight, t.type, t.name);
		}

		if(Player.isWithinBounds((int) t.origin.x,(int) t.origin.y,(int) t.origin.x + t.objectWidth,(int) t.origin.y + t.objectHeight)) {
			PlayState.add(tileObj);
		}
		PlayState.add(tileObj);
		objectsMap.put(String.valueOf(t.origin.x + "," + t.origin.y), tileObj);
	}
	
	
	public static void addObjectsInViewPort(TileObject t) {
		if(Player.isWithinBounds((int) t.getPos().x, (int) t.getPos().y, (int) t.getPos().x + t.objectWidth, (int) t.getPos().y + t.objectHeight)) {
			PlayState.add(t);
		}
	}
	
	public static TileObject getTileObjFromMapByPos(Vector2f pos) {
		TileObject t = null;
		if(objectsMap.containsKey(String.valueOf(pos.x + "," + pos.y))) {
			t = objectsMap.get(String.valueOf(pos.x + "," + pos.y));
		}
		return t;
	}
	
	public static TileObject getClosestObjectInList(Vector2f pos, Player player) {
		TileObject tileObject = null;
			for(TileObject to : getTileObjectsInArea(pos)) {
				tileObject = to;
			}
		return tileObject;
	}
	
	public static TileObject getClosestObjectInList(Vector2f pos, int offset) {
		TileObject tileObject = null;
			for(TileObject to : getTileObjectsInArea(pos, offset)) {
				tileObject = to;
			}
		return tileObject;
	}
	
	public static void addObject(TileObject tileObject) {
		objectsMap.put(String.valueOf(tileObject.getPos().x) + "," + String.valueOf(tileObject.getPos().y), tileObject); 
		PlayState.add(tileObject);
	}
	
	public static void removeObject(TileObject tileObject) {
		objectsMap.remove(String.valueOf(tileObject.getPos().x) + "," + String.valueOf(tileObject.getPos().y)); 
	}
	
	public static ArrayList<TileObject> getTileObjectsInArea(Vector2f pos) {
		ArrayList<TileObject> temp = new ArrayList<TileObject>();
		for(float i = (int) pos.x - 64; i < (int) pos.x + 64; i++) {
			for(float j = (int) pos.y - 64; j < (int) pos.y + 128; j++) {
				if(tileMapObjectExistsAt(new Vector2f(i, j))) {
					temp.add(objectsMap.get(String.valueOf(i) + "," + String.valueOf(j)));
				}
			}
		}
		return temp;
	}
	
	public static ArrayList<TileObject> getTileObjectsInArea(Vector2f pos, int offset) {
		ArrayList<TileObject> temp = new ArrayList<TileObject>();
		for(float i = (int) pos.x - offset; i < (int) pos.x + offset; i++) {
			for(float j = (int) pos.y - offset; j < (int) pos.y + offset; j++) {
				if(tileMapObjectExistsAt(new Vector2f(i, j))) {
					temp.add(objectsMap.get(String.valueOf(i) + "," + String.valueOf(j)));
				}
			}
		}
		return temp;
	}
	
	public static boolean tileMapObjectExistsAt(Vector2f pos) {
		return objectsMap.containsKey(String.valueOf(pos.x + "," + pos.y));
	}
	
	

}
