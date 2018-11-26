package com.cmbstnengine.game.inventory;

import java.util.ArrayList;
import java.util.List;

import com.cmbstnengine.game.entity.entityobjects.TileObject;
import com.cmbstnengine.game.entity.items.Item;

public class Inventory {
	
	public List<TileObject> tileObjects;
	public List<Item> items;
	
	public Inventory() {
		tileObjects = new ArrayList<>();
		items = new ArrayList<>();
	}
	
	public void add(Object o) {
		if(o instanceof TileObject) {
			tileObjects.add((TileObject) o);
		} else if(o instanceof Item) {
			items.add((Item) o);
		} else {
			return;
		}
	}
	
	public List<TileObject> getTileobjects() {
		return tileObjects;
	}
	
	public List<Item> getItems() {
		return items;
	}

	public void remove(TileObject t) {
		for(TileObject tileObj : tileObjects) {
		}
	}

	public void removeItems() {
		int index = -1;
		for(int i = 0; i < tileObjects.size(); i++) {
			if(tileObjects.get(i).getRemoveFromInventory()) {
				index = i;
			}
		}
		tileObjects.remove(index);
	}
	
}
