package progark.pkg.game;

import java.util.ArrayList;

public abstract class Player {
	private ArrayList<Unit> units;
	int playerNo;
	
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
}
