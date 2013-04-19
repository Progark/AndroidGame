package progark.pkg.game;

import sheep.graphics.Image;

public class Globals {
	public static final int PLAYER_ONE = 1, PLAYER_TWO = 2;
	public static final float ANIMATION_TIME = 1.0f;
	public static final int MOVING_SPEED = 400; 	//100 pixler per sekund
	public static final int MENU_PARTS = 6;
	public static final Image NORMAL_TILE = new Image(R.drawable.tile);
	public static final Image GREEN_TILE = new Image(R.drawable.tilegreen);
	public static final Image RED_TILE = new Image(R.drawable.tilered);
	public static final Image BLUE_TILE = new Image(R.drawable.tileblue);
	public static final int BOARD_HEIGHT = 13, BOARD_WIDTH = 10;
	
	public static int canvasHeight, canvasWidth;
	public static int calculatedTileSize;
	public static int menuWidth;
	
}
