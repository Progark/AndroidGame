package progark.pkg.game;

import java.util.ArrayList;
import java.util.Iterator;

import sheep.game.Sprite;
import sheep.graphics.Image;

import android.graphics.Canvas;

public class Board {
	private int boardHeight = 11, boardWidth = 7;
	private Image boardTileImage;
	private Sprite[][] boardArray;
	float sx, sy;

	public Board() {
		boardTileImage = new Image(R.drawable.tile);
		
		sx = Globals.TILE_SIZE/boardTileImage.getWidth();
		sy = Globals.TILE_SIZE/boardTileImage.getHeight();
		
		
		boardArray = new Sprite[boardHeight][boardWidth];
		for (int i = 0; i < boardHeight; i++) {
			for (int j = 0; j < boardWidth; j++) {
				Sprite tempSprite = new Sprite(new Image(R.drawable.tile));
				tempSprite.setOffset(0, 0);
				tempSprite.setScale(sx, sy);
				tempSprite.setPosition(i*Globals.TILE_SIZE,j*Globals.TILE_SIZE + Globals.TILE_SIZE);
				
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
	
}