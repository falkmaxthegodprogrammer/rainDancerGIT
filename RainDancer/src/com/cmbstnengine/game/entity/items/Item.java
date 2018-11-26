package com.cmbstnengine.game.entity.items;

import java.awt.image.BufferedImage;

import com.cmbstnengine.game.graphics.Sprite;

public abstract class Item {
	
	/* Since items are not generally placeable in the world
	 * we have our own class-hierarchy for items of that type.
	 * This might include: armor, weapons, consumables, potions, food
	 * crafting items (ore etc).
	 */
	
	/* In comparison to TileObjects, which you pick up from the world
	 * this is not represented or handles as an entity and thus not rendered
	 * or updated in our game logic in the same way. For instance, an item 
	 * might be a Key that unlocks a door or copper ore or a dragon-slaying sword!
	 */
	
	/* Every item has a sprite, a name, a description
	 * and a stack-size (in case an item is a crafting item).
	 */
	
	protected Sprite sprite;
	protected BufferedImage icon;
	protected String name;
	protected String itemDescription;
	protected int stackSize;
	
	/* We only initialize these variables in case
	 * the item is an equippable-type
	 */
	
	protected int minDamage;
	protected int maxDamage;
	protected int durability;
	protected int armor;
	
	/* Magical effects, stat modifiers
	 */
	
	//protected Ability ability;
	
	protected enum ItemType {
		WEAPON,
		ARMOR,
		CONSUMABLE,
		RECIPE,
		INGREDIENT,
		CRAFTING_ITEM,
		QUEST_ITEM
	}
	
	
	
	
	
	

}
