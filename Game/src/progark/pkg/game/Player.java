package progark.pkg.game;

import java.util.ArrayList;

public class Player {
	private ArrayList<Unit> units;
	private int playerNo;
	private String playerName;
	

	public Player(/*String playerName*/){
		//setPlayerName(playerName);
		addUnits(new MeleeUnit());
		addUnits(new MeleeUnit());
		addUnits(new RangedUnit());
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
