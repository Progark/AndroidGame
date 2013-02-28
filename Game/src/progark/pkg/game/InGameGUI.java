package progark.pkg.game;

import android.graphics.Canvas;

public class InGameGUI {

	private Board board;
	private BoardMenu boardMenu;
	
	public InGameGUI(int scrHeight, int scrWidth) {
		boardMenu = new BoardMenu();
		board = new Board(scrHeight - boardMenu.getMenuHeight(), scrWidth - boardMenu.getMenuWidth());
		
	}
	
	public void draw(Canvas canvas){
	}
}
