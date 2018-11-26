package com.cmbstnengine.game.entity.items.weapons;

import com.cmbstnengine.game.entity.items.Item;
import com.cmbstnengine.game.graphics.Sprite;

public class Weapon extends Item {
	
	protected enum WeaponType {
		ONE_HANDED,
		TWO_HANDED
	}
	
	public Weapon(String name, Sprite sprite, WeaponType type) {
		this.name = name;
		this.sprite = sprite;
		this.stackSize = 1;
	}
	
	public void setDurability(int durability) {
		this.durability = durability;
	}
	
	public void setItemDescription(String desc) {
		this.itemDescription = desc;
	}
	
	public void setDamage(int minDmg, int maxDmg) {
		this.minDamage = minDmg;
		this.maxDamage = maxDmg;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	
	
	
	

}
