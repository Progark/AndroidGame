package progark.pkg.game;

/**
 * Object to give the GameMechanics the correct 
 * configurations chosen by the player 
 * 
 * @author Bjodol
 */
public class GameInitObject {

	Player p1;
	Player p2;
	
	/**
	 * Constructor
	 */
	public GameInitObject() {
		p1 = new Player(Globals.PLAYER_ONE, "H1");
		p2 = new Player(Globals.PLAYER_TWO, "H2");
	}
	
	public Player getP1() {
		return p1;
	}

	public void setP1(Player p1) {
		this.p1 = p1;
	}

	public Player getP2() {
		return p2;
	}

	public void setP2(Player p2) {
		this.p2 = p2;
	}
}
