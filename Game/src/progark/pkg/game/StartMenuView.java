package progark.pkg.game;

import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Image;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

/*
 * Denne får nå startet spillet, men den ser ikke spesielt pen ut. Skal få fikset på det seinere...
 */
public class StartMenuView extends State {
	private int i = 0;
	private Image welcomeImage;
	private Sprite welcomeSprite;
	private float sx, sy;

	public StartMenuView(){
		welcomeImage = new Image(R.drawable.start);
		welcomeSprite = new Sprite(welcomeImage);
		welcomeSprite.setOffset(0,0);
	}
	
	public void setPopGameMechanics(){
		
		getGame().popState();
		getGame().popState();
	}
	
	@Override
	public void update(float dt){
		super.update(dt);
		welcomeSprite.update(dt);
	}

	@Override
	public void draw(Canvas canvas){
		if (i == 0){
			Globals.canvasHeight = canvas.getHeight();
			Globals.canvasWidth = canvas.getWidth();
			Globals.calculatedTileSize = Globals.canvasWidth/Globals.BOARD_WIDTH;
			
			sx = 1.0f*Globals.canvasWidth/welcomeImage.getWidth();
			sy = 1.0f*Globals.canvasHeight/welcomeImage.getHeight();
			welcomeSprite.setScale(sx, sy);
			welcomeSprite.setPosition(Globals.canvasWidth/2, Globals.canvasHeight/2);
			
			i++;
		}
		
		welcomeSprite.draw(canvas);
	}

	@Override
	public boolean onTouchDown(MotionEvent me){
		GameInitObject gio = new GameInitObject();		
		getGame().pushState(new HeroChooseView(gio, Globals.PLAYER_ONE, this));
		return true;
	}
}
