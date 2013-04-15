package progark.pkg.game;

import java.util.ArrayList;

import android.graphics.Canvas;

public class Player {
	private ArrayList<Unit> units;
	private int playerNo;
	private String playerName;
	

	public Player(/*String playerName*/){
		//setPlayerName(playerName);
		units = new ArrayList<Unit>();
		addUnits(new MeleeUnit(100, 100));
		addUnits(new MeleeUnit(100, 300));
		addUnits(new RangedUnit(100, 500));
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
}
