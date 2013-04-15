package progark.pkg.game;


import android.graphics.Canvas;
import android.graphics.Color;

public class InGameGUI {

	private Board board;
	private BoardMenu boardMenu;
	
	public InGameGUI(int scrHeight, int scrWidth) {
		boardMenu = new BoardMenu();
		board = new Board(scrHeight - boardMenu.getMenuHeight(), scrWidth - boardMenu.getMenuWidth());
	}
	
	
	public void draw(Canvas canvas){
		board.draw(canvas);
	}
}
