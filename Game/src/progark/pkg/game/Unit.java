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
	private int posSqX, posSqY;

	public Unit(Image i, Image i2, Image i3){
		super(i);
		setShape(i.getWidth(), i.getHeight());
		float x = 100/i.getWidth();
		float y = 100/i.getWidth();
		setScale(x, y);
		float offsetX = i.getWidth()/2.0f;
		float offsetY = i.getHeight()/2.0f;
		setOffset(0, 0);
		
		images = new ArrayList<Image>();
		images.add(i);
		images.add(i2);
		images.add(i3);
	}
	
	@Override
	public void update(float dt){
		super.update(dt);
		time += dt;
		if (time >= 0.5){
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
	
	public int getSquareX(){
		return posSqX;
	}
	
	public int getSquareY(){
		return posSqY;
	}
	
	public void setSquareX(int px){
		posSqX = px;
	}
	
	public void setSquareY(int py){
		posSqY = py;
	}
}
