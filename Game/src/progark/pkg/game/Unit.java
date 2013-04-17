package progark.pkg.game;

import java.util.ArrayList;

import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.graphics.SpriteView;

public abstract class Unit extends Sprite{
	private String name;
	private int health, strength, skill, defence, range;
	private ArrayList<Image> images;
	private float timeWalk = 0, timeAttack = 0;
	private int frameCountWalk = 1, frameCountAttack = 4;
	private int posSqX, posSqY;
	private boolean isFlipped = false;
	private float scaleX, scaleY;
	private String typeOfAnimation = "S";		//W: walk, S: standingStill, A: attack 

	public Unit(Image i0, Image i1, Image i2, Image i3, Image i4, Image i5){
		super(i0);
		setShape(i0.getWidth(), i0.getHeight());
		scaleX = Globals.TILE_SIZE/i0.getWidth();
		scaleY = Globals.TILE_SIZE/i0.getWidth();
		setScale(scaleX, scaleY);	
		setOffset(0, 0);

		images = new ArrayList<Image>();
		images.add(i0);		//Står stille
		images.add(i1);		//Bevegelse 1
		images.add(i2);		//Bevegelse 2
		images.add(i3);		//Bevegelse 3
		images.add(i4);		//Angrep 1
		images.add(i5);		//Angrep 2

	}

	@Override
	public void update(float dt){
		super.update(dt);
		if (typeOfAnimation.equals("S"))
			setView((SpriteView)images.get(0));
		else if (typeOfAnimation.equals("W"))
			walkAnimation(dt);
		else if (typeOfAnimation.equals("A"))
			attackAnimation(dt);
	}
	
	public void attackAnimation(float dt){
		timeAttack += dt;
		if (timeAttack > 0.1){
			frameCountAttack ++;
			timeAttack = 0;
			if (frameCountAttack > 5)
				frameCountAttack = 4;
			setView((SpriteView)images.get(frameCountAttack));
		}
	}
	
	public void walkAnimation(float dt){
		timeWalk += dt;
		if (timeWalk > 0.1){
			frameCountWalk ++;
			timeWalk = 0;
			if (frameCountWalk > 3)
				frameCountWalk = 1;
			setView((SpriteView)images.get(frameCountWalk));
		}
	}
	
	public String getTypeOfAnimatino(){
		return typeOfAnimation;
	}
	
	public void setAnimation(String s){
		typeOfAnimation = s;
	}
	/**
	 * Denne funksjonen flipper bildene om y-aksen slik at de blir speilvendt
	 */
	public void flip(){
		if (isFlipped){
			setScale(scaleX, scaleY);
			setOffset(0,0);
		} else {
			setScale(-scaleX, scaleY);
			setOffset(-Globals.TILE_SIZE,0);
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
