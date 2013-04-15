package progark.pkg.game;

import java.awt.Dimension;

import sheep.game.Sprite;
import sheep.graphics.Image;

public class BoardTile extends Sprite {
	private int xPos, yPos;


	public BoardTile(Image tileImage, int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public int getxPos() {
		return xPos;
	}
	
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}
	
	public int getyPos() {
		return yPos;
	}
	public void setYPos(int yPos){
		this.yPos = yPos;
	}
	
	
	
	//TODO: Legge til listeners som kan aktiveres når en enhet er valgt, slik at man kan flytte den til denne tilen.
	
}
