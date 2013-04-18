package progark.pkg.game;

import sheep.game.Sprite;
import sheep.graphics.Image;

import android.graphics.Canvas;

public class Board {
	private int boardHeight, boardWidth;
	private int k;
	private Image boardTileImage;
	private Sprite[][] boardArray;
	float sx, sy;

	public Board() {
		k = 0;
		boardTileImage = new Image(R.drawable.tile);
	}
	
	public void draw(Canvas canvas){
		if (k == 0) {
			boardHeight = canvas.getHeight() / Globals.TILE_SIZE;
			boardWidth = canvas.getWidth() / Globals.TILE_SIZE;
			sx = Globals.TILE_SIZE/boardTileImage.getWidth();
			sy = Globals.TILE_SIZE/boardTileImage.getHeight();			
			k++;
		}
		boardArray = new Sprite[boardHeight][boardWidth];
		for (int i = 0; i < boardHeight; i++) {
			for (int j = 0; j < boardWidth; j++) {
				boardArray[i][j] = new Sprite(boardTileImage);
				Sprite tempSprite = boardArray[i][j];
				tempSprite.setOffset(0, 0);
				tempSprite.setPosition(i*Globals.TILE_SIZE,j*Globals.TILE_SIZE + Globals.TILE_SIZE);
				tempSprite.setScale(sx, sy);
				tempSprite.draw(canvas);
//				boardArray[i][j].setOffset(0, 0);
//				boardArray[i][j].setPosition(i*Globals.TILE_SIZE,j*Globals.TILE_SIZE + Globals.TILE_SIZE);
//				boardArray[i][j].setScale(sx, sy);
//				boardArray[i][j].draw(canvas);
			}
		}
	}
	
}