package progark.pkg.game;

import java.util.ArrayList;

public abstract class Player {
	private ArrayList<Unit> units;
	private int playerNo;
	private int playerName;
	

	public Player(){
		
	}

	public ArrayList<Unit> getUnits() {
		return units;
	}

	public void setUnits(ArrayList<Unit> units) {
		this.units = units;
	}

	public int getPlayerNo() {
		return playerNo;
	}

	public void setPlayerNo(int playerNo) {
		this.playerNo = playerNo;
	}

	public int getPlayerName() {
		return playerName;
	}
	
	public void setPlayerName(int playerName) {
		this.playerName = playerName;
	}
}
