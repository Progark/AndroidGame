package progark.pkg.game;

import sheep.graphics.Image;

public abstract class Unit {
	private String name;
	private int health, strength, skill, defence;
	private Image unitImage;


	public Unit(String name, int h, int s, int skill){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getSkill() {
		return skill;
	}

	public void setSkill(int skill) {
		this.skill = skill;
	}
	
	public int getDefence() {
		return defence;
	}
	
	public void setDefence(int defence) {
		this.defence = defence;
	}
	
	public Image getUnitImage() {
		return unitImage;
	}
	
	public void setUnitImage(Image unitImage) {
		this.unitImage = unitImage;
	}
}
