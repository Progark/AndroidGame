package progark.pkg.game;

import java.util.ArrayList;

import android.graphics.Canvas;

public class Player {
	private ArrayList<Unit> units;
	private int playerNo;
	private String playerName;
	private float maxTotalHealth = 0.0f;
	

	public Player(int p){
		//setPlayerName(playerName);
		
		
		if (p == 1){
			units = new ArrayList<Unit>();
			addUnits(new MeleeUnit(0,2*Globals.TILE_SIZE));
			addUnits(new MeleeUnit(0, 4*Globals.TILE_SIZE));
			addUnits(new RangedUnit(0, 6*Globals.TILE_SIZE));
		} else {
			units = new ArrayList<Unit>();
			addUnits(new MeleeUnit(6*Globals.TILE_SIZE,2*Globals.TILE_SIZE));
			addUnits(new MeleeUnit(6*Globals.TILE_SIZE, 4*Globals.TILE_SIZE));
			addUnits(new RangedUnit(6*Globals.TILE_SIZE, 6*Globals.TILE_SIZE));
			for (Unit unit : units) {
				unit.flip();
			}
			
		}
		for (Unit u : units) {
			maxTotalHealth += u.getHealth();
		}
	}
	
	public void draw(Canvas canvas){
		for (Unit u : units) {
			u.draw(canvas);
		}
	}
	
	public void update(float dt){
		for (Unit u : units) {
			u.update(dt);
		}
	}
	public ArrayList<Unit> getUnits() {
		return units;
	}

	public void setUnits(ArrayList<Unit> units) {
		this.units = units;
	}
	
	public void addUnits(Unit unit){
		this.units.add(unit);
	}

	public int getHealth(){
		int health = 0;
		for (Unit unit : units) {
			health += unit.getHealth();
		}
		return health;
	}
	
	public int getPlayerNo() {
		return playerNo;
	}

	public void setPlayerNo(int playerNo) {
		this.playerNo = playerNo;
	}

	public String getPlayerName() {
		return playerName;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public float getMaxTotalHealth(){
		return maxTotalHealth;
	}
}
