package progark.pkg.game;

import sheep.graphics.Image;
import android.graphics.Canvas;

public class BoardView {
	private Image boardTileImage;
	private BoardTile[][] boardArray;
	private float sx, sy;
	private static BoardView instance = null;

	private BoardView() {
		boardTileImage = new Image(R.drawable.tile);
		
		sx = Globals.calculatedTileSize/boardTileImage.getWidth();
		sy = Globals.calculatedTileSize/boardTileImage.getHeight();
		
		
		boardArray = new BoardTile[Globals.BOARD_HEIGHT][Globals.BOARD_WIDTH];
		for (int i = 0; i < Globals.BOARD_HEIGHT; i++) {
			for (int j = 0; j < Globals.BOARD_WIDTH; j++) {
				BoardTile tempSprite = new BoardTile(new Image(R.drawable.tile));
				tempSprite.setOffset(0, 0);
				tempSprite.setScale(sx, sy);
				tempSprite.setPosition(j*Globals.calculatedTileSize, i*Globals.calculatedTileSize);
				boardArray[i][j] = tempSprite;
			}
		}
	}
	
	public static BoardView getInstance(){
		if(instance == null){
			synchronized(BoardView.class){
				if(instance == null)
					instance = new BoardView();
			}
		}
		return instance;
	}


	public void draw(Canvas canvas){
		
		for (int i = 0; i < boardArray.length; i++) {
			for (int j = 0; j < boardArray[i].length; j++) {
				boardArray[i][j].draw(canvas);
			}
		}
	}
	
	public void update(float dt){
		for (int i = 0; i < boardArray.length; i++) {
			for (int j = 0; j < boardArray[i].length; j++) {
				boardArray[i][j].update(dt);
			}
		}
	}
	
	/**
	 * Y f¿rst, sŒ X
	 * @param squareY
	 * @param squareX
	 * @return
	 */
	public BoardTile getTile(int squareY, int squareX){
		return boardArray[squareY][squareX];
	}
	
	public BoardTile[][] getBoard(){
		return boardArray;
	}
	
}