package progark.pkg.game;

import sheep.audio.Sound;
import sheep.graphics.Image;

public class Globals {
	public static final int PLAYER_ONE = 1, PLAYER_TWO = 2;
	public static final float ANIMATION_TIME = 2.0f;
	public static final int MOVING_SPEED = 400; 	//100 pixler per sekund
	public static final int MENU_PARTS = 6;
	public static final Image NORMAL_TILE = new Image(R.drawable.tile);
	public static final Image GREEN_TILE = new Image(R.drawable.tilegreen);
	public static final Image RED_TILE = new Image(R.drawable.tilered);
	public static final Image BLUE_TILE = new Image(R.drawable.tileblue);
	public static final int BOARD_HEIGHT = 20, BOARD_WIDTH = 10;
	public static final Image VIKING = new Image(R.drawable.viking);
	public static final Image PRINCESS = new Image(R.drawable.princess);
	public static final Image PLAYER_ONE_IMAGE = new Image(R.drawable.playerone);
	public static final Image PLAYER_TWO_IMAGE = new Image(R.drawable.playertwo);
	public static final Sound backgroundMusic = new Sound(R.raw.bongos);
	
	
	public static int canvasHeight, canvasWidth;
	public static int calculatedTileSize;
	public static int menuWidth;
	
}
