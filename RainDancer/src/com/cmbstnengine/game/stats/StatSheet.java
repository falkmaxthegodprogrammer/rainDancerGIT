package com.cmbstnengine.game.stats;

import java.util.List;

public class StatSheet {
	
	
	/*
	 * Attributes, character or entity-specific ranging 
	 */
	
	private int strength;
	private int vitality;
	private int dexterity;
	private int intelligence;
	private int agility;
	private int knowledge;
	
	/*
	 * Values like HP, mana, stat-gain 
	 */
	
	private int health;
	private int mana;
	private int stamina;
	private int special_resource;

	/*
	 * Modifiers more specifically for mobs, enemies,
	 * npcs etc.
	 */
	
	private float speed;
	private float maxSpeed;
	private float acc;
	private float deAcc;
	
	/*
	 * Constructors for a stat-sheet depending on
	 * what type an object with a stat-sheet is
	 * A player will need to initialize all field-variables
	 * however a crate with HP will not need anything but the hp;
	 */
	
	
	/* Default constructor, setting all values
	 * to zero.
	 */
	
	public StatSheet() {
		this.strength = 0;
		this.vitality = 0;
		this.intelligence = 0;
		this.agility = 0;
		this.dexterity = 0;
		this.knowledge = 0;
		this.health = 0;
		this.mana = 0;
		this.special_resource = 0;
		this.stamina = 0;
		this.special_resource = 0;
		this.speed = 0;
		this.maxSpeed = 0;
		this.acc = 0;
		this.deAcc = 0;
	}
	
	/* 
	 * Method taking a List of statSheets that adds the values 
	 * of stats in list to the current statSheet
	 * for instance if a player has items with statsheets
	 * his "base-statsheet" would get affected by the computeSheets()
	 * 
	 */
	
	public void computeStats(List<StatSheet> sheets) {
		for(StatSheet sheet : sheets) {
			this.strength += sheet.strength;
			this.vitality += sheet.vitality;
			this.intelligence += sheet.intelligence;
			this.dexterity = sheet.dexterity;
			this.agility += sheet.agility;
			this.knowledge += sheet.knowledge;
			this.health += sheet.health;
			this.mana += sheet.mana;
			this.stamina += sheet.stamina;
			this.speed += sheet.speed;
			this.maxSpeed = sheet.speed;
			this.acc = sheet.acc;
			this.deAcc = sheet.deAcc;
		}
	}
	
	/* Compute HP, mana, stamina methods
	 * formulas subject to change
	 */
	
	public int computeMaxHP() {
		return (vitality * 10) + health;
	}
	
	public int computeMaxMana() {
		return (intelligence * 10) + mana;
	}
	
	public int computeMaxStamina() {
		return(dexterity * 5 + agility * 5) + stamina;
	}
	
	@Override
	public String toString() {
		return "Strength: " + strength + "\n" +
				"Vitality: " + vitality + "\n" +
				"Intelligence: " + intelligence + "\n" +
				"Dexterity: " + dexterity + "\n" +
				"Agility: " + agility + "\n" +
				"Knowledge: " + knowledge + "\n" +
				"Health: " + computeMaxHP() + "\n" +
				"Mana: " + computeMaxMana() + "\n" +
				"Stamina: " + computeMaxStamina() + "\n" +
				"Speed: " + speed;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
}
