package progark.pkg.game;

import java.util.ArrayList;

import sheep.graphics.Image;

import android.graphics.Canvas;

public class Board {
	int boardHeight, boardWidth;
	Image boardTileImage;

	public Board(int boardHeight, int boardWidth) {		
		//boardheigh modulo figurst�rrelse vil gi h�yden, tilsvarende for vidden.
		this.boardHeight = boardHeight % (int)boardTileImage.getHeight();
		this.boardWidth = boardWidth % (int)boardTileImage.getWidth();
	}
	
	public void draw(Canvas canvas){
		for (int i = 0; i < boardHeight; i++) {
			for (int j = 0; j < boardWidth; j++) {
				//TODO: Laste inn en og en boardtile og for hver Tile legge p� posisjonen med boardtilens vidde
				//Eksempel: new BoardTile(Bilde-Fil, i, j); i og j for � angi posisjonen til spriten. 
			}
			//TODO: Legge p� h�ydeposisjonen med boardtilens h�yde
		}
	}
	
}
