package progark.pkg.game;

public class Coordinate {
	private int squareX, squareY;

	public Coordinate(int squareY, int squareX){
		this.squareY = squareY;
		this.squareX = squareX;
	}

	public int getSquareX(){
		return squareX;
	}

	public int getSquareY(){
		return squareY;
	}

	public void setSquareX(int x){
		squareX = x;
	}

	public void setSquareY(int y){
		squareY = y;
	}
}
