package progark.pkg.game;

import sheep.game.Sprite;
import sheep.graphics.Image;

import android.graphics.Canvas;

public class Board {
	private Image boardTileImage;
	private BoardTile[][] boardArray;
	float sx, sy;

	public Board() {
		boardTileImage = new Image(R.drawable.tile);
		
		sx = Globals.TILE_SIZE/boardTileImage.getWidth();
		sy = Globals.TILE_SIZE/boardTileImage.getHeight();
		
		
		boardArray = new BoardTile[Globals.BOARD_HEIGHT][Globals.BOARD_WIDTH];
		for (int i = 0; i < Globals.BOARD_HEIGHT; i++) {
			for (int j = 0; j < Globals.BOARD_WIDTH; j++) {
				BoardTile tempSprite = new BoardTile(new Image(R.drawable.tile));
				tempSprite.setOffset(0, 0);
				tempSprite.setScale(sx, sy);
				tempSprite.setPosition(j*Globals.TILE_SIZE, i*Globals.TILE_SIZE);
				boardArray[i][j] = tempSprite;
			}
		}
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
	
}