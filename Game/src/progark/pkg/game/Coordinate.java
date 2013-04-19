package progark.pkg.game;

public class Coordinate {
	private int squareX, squareY;

	public Coordinate(int x, int y){
		squareX = x;
		squareY = y;
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
