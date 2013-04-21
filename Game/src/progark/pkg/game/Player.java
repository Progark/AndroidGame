package progark.pkg.game;

import java.util.ArrayList;

import android.graphics.Canvas;

public class Player {
	private ArrayList<Unit> units;
	private int playerNo;
	private String playerName;
	private float maxTotalHealth = 0.0f;
	private ArrayList<HealthBar> individualHealthBars;

	
	public Player(int t, String s){
		playerName = s;
		playerNo = 1;
		if (t == 1){
			if (s.equals("Hero1")){
				units = new ArrayList<Unit>();
				individualHealthBars = new ArrayList<HealthBar>();
				addUnits(new MeleeUnit(0, 2*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(0)));
				addUnits(new MagicUnit(0, 4*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(1)));
				addUnits(new RangedUnit(0, 6*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(2)));
			} else if (s.equals("Hero2")){
				units = new ArrayList<Unit>();
				individualHealthBars = new ArrayList<HealthBar>();
				addUnits(new MeleeUnit(0, 2*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(0)));
				addUnits(new RangedUnit(0, 4*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(1)));
				addUnits(new RangedUnit(0, 6*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(2)));
			} else if (s.equals("Viking")){
				units = new ArrayList<Unit>();
				individualHealthBars = new ArrayList<HealthBar>();
				addUnits(new MeleeUnit(0, 2*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(0)));
				addUnits(new MeleeUnit(0, 4*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(1)));
				addUnits(new MeleeUnit(0, 6*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(2)));
				addUnits(new MeleeUnit(0, 8*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(3)));
			} else {
				units = new ArrayList<Unit>();
				individualHealthBars = new ArrayList<HealthBar>();
				addUnits(new RangedUnit(0, 2*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(0)));
				addUnits(new MagicUnit(0, 4*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(1)));
				addUnits(new RangedUnit(0, 6*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(2)));
			}
		} else {
			if (s.equals("Hero1")){
				units = new ArrayList<Unit>();
				individualHealthBars = new ArrayList<HealthBar>();
				addUnits(new MeleeUnit((Globals.BOARD_WIDTH - 1)*Globals.calculatedTileSize, 2*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(0)));
				addUnits(new MagicUnit((Globals.BOARD_WIDTH - 1)*Globals.calculatedTileSize, 4*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(1)));
				addUnits(new RangedUnit((Globals.BOARD_WIDTH - 1)*Globals.calculatedTileSize, 6*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(2)));
			} else if (s.equals("Hero2")){
				units = new ArrayList<Unit>();
				individualHealthBars = new ArrayList<HealthBar>();
				addUnits(new MeleeUnit((Globals.BOARD_WIDTH - 1)*Globals.calculatedTileSize, 2*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(0)));
				addUnits(new RangedUnit((Globals.BOARD_WIDTH - 1)*Globals.calculatedTileSize, 4*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(1)));
				addUnits(new RangedUnit((Globals.BOARD_WIDTH - 1)*Globals.calculatedTileSize, 6*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(2)));
			} else if (s.equals("Viking")){
				units = new ArrayList<Unit>();
				individualHealthBars = new ArrayList<HealthBar>();
				addUnits(new MeleeUnit((Globals.BOARD_WIDTH - 1)*Globals.calculatedTileSize, 2*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(0)));
				addUnits(new MeleeUnit((Globals.BOARD_WIDTH - 1)*Globals.calculatedTileSize, 4*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(1)));
				addUnits(new MeleeUnit((Globals.BOARD_WIDTH - 1)*Globals.calculatedTileSize, 6*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(2)));
				addUnits(new MeleeUnit((Globals.BOARD_WIDTH - 1)*Globals.calculatedTileSize, 8*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(3)));
			} else {
				units = new ArrayList<Unit>();
				individualHealthBars = new ArrayList<HealthBar>();
				addUnits(new RangedUnit((Globals.BOARD_WIDTH - 1)*Globals.calculatedTileSize, 2*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(0)));
				addUnits(new MagicUnit((Globals.BOARD_WIDTH - 1)*Globals.calculatedTileSize, 4*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(1)));
				addUnits(new RangedUnit((Globals.BOARD_WIDTH - 1)*Globals.calculatedTileSize, 6*Globals.calculatedTileSize));
				individualHealthBars.add(new HealthBar(units.get(2)));
			}
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
		for (HealthBar hb : individualHealthBars) {
			hb.draw(canvas);
		}
	}
	
	public void update(float dt){
		for (Unit u : units) {
			u.update(dt);
		}
		for (HealthBar hb : individualHealthBars) {
			hb.update(dt);
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
	
	public ArrayList<HealthBar> getIndividualHealthBars(){
		return individualHealthBars;
	}
}