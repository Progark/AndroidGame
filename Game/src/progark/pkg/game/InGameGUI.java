package progark.pkg.game;


import android.graphics.Canvas;
import android.graphics.Color;

public class InGameGUI {

	private Board board;
	private BoardMenu boardMenu;
	private GameMechanics gm;
	
	public InGameGUI(int scrHeight, int scrWidth) {
		boardMenu = new BoardMenu(gm);
		board = new Board();
	}
	
	
	public void draw(Canvas canvas){
		board.draw(canvas);
	}
}
