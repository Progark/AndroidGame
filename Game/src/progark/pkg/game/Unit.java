package progark.pkg.game;

import java.util.ArrayList;

import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.graphics.SpriteView;

public abstract class Unit extends Sprite{
	private String name;
	private int health, strength, skill, defence, range;
	private ArrayList<Image> images;
	private float time = 0;
	private int frameCount = 0;
	private boolean isSelected = false;
	private int posSqX, posSqY;	//Dette skal være posisjonen i de store firkantene

	public Unit(Image i, Image i2, Image i3){
		super(i);
		setShape(i.getWidth(), i.getHeight());
		images = new ArrayList<Image>();
		images.add(i);
		images.add(i2);
		images.add(i3);
	}
	
	@Override
	public void update(float dt){
		super.update(dt);
		time += dt;
		if (time >= 0.1){
			frameCount ++;
			if (frameCount > 2)
				frameCount = 0;
			setView((SpriteView)images.get(frameCount));
		}
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
	
	public int getRange() {
		return range;
	}
	
	public void setRange(int range){
		this.range = range;
	}
	
	public void setSelected(){
		if (isSelected)
			isSelected = false;
		else
			isSelected = true;
	}
	
	public boolean isSelected(){
		return isSelected;
	}
	
	public int getPosX(){
		return posSqX;
	}
	
	public int getPosY(){
		return posSqY;
	}
	
	public void setPosSqX(int px){
		posSqX = px;
	}
	
	public void setPosSqY(int py){
		posSqY = py;
	}
	
	/*
	 * Disse trengs vel egentlig ikke? Siden vi bruker image er det vel like greit å gjøre det på samme måte som Alf Inge har gjort i
	 * eksempelet sitt. Da må man kalle på super(image) i konstruktøren til spriten 
	 */
//	public Image getUnitImage() {
//		return unitImage;
//	}
//	
//	public void setUnitImage(Image unitImage) {
//		this.unitImage = unitImage;
//	}
}
