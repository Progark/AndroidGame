package progark.pkg.game;

import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.graphics.SpriteView;

public class BoardTile extends Sprite {
	

	/**
	 * Konstruktør som tar inn bilde for første gangsbruk
	 * 
	 * @param image
	 */
	
	public BoardTile(Image image) {
		super(image);
	}
	
	/**
	 * Setter farge ved hjelp av global Tile variabel
	 * @param tileColor: Bruk globals.*farge*_TILE
	 */
	public void setTileColor(Image tileColor){
		this.setView((SpriteView)tileColor);
	}
	
}
