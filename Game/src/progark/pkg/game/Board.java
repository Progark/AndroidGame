package progark.pkg.game;

import sheep.graphics.Image;

import android.graphics.Canvas;

public class Board {
	private int boardHeight, boardWidth;
	private Image boardTileImage = new Image(R.drawable.tile);
	private BoardTile[][] boardArray;

	public Board(int boardHeight, int boardWidth) {		
		this.boardHeight = boardHeight / (int)boardTileImage.getHeight();
		this.boardWidth = boardWidth / (int)boardTileImage.getWidth();
		System.out.println("bh : " + boardHeight + "bw : " + boardWidth);
		boardArray = new BoardTile[boardHeight][boardWidth];
	}
	
	public void draw(Canvas canvas){
		for (int i = 0; i < boardHeight; i++) {
			for (int j = 0; j < boardWidth; j++) {
				boardArray[i][j] = new BoardTile(boardTileImage);
				boardArray[i][j].setPosition((float)i*boardTileImage.getHeight() - boardTileImage.getHeight()/2, (float)j*boardTileImage.getWidth() - boardTileImage.getWidth()/2);
				boardArray[i][j].draw(canvas);
			}
		}
	}
	
}
