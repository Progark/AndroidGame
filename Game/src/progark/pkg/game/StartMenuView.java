package progark.pkg.game;

import sheep.audio.Sound;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Image;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

/*
 * Denne får nå startet spillet, men den ser ikke spesielt pen ut. Skal få fikset på det seinere...
 */
public class StartMenuView extends State {
	private int i = 0;
	private Image welcomeImage;
	private Sprite welcomeSprite;
	private float sx;
//	private Sound sound;
	private GameMusic gameMusic;

	public StartMenuView(){
		welcomeImage = new Image(R.drawable.start);
		welcomeSprite = new Sprite(new Image(R.drawable.start));
		welcomeSprite.setOffset(0,0);
//		sound = new Sound(R.raw.bongos);
//		Globals.backgroundMusic.play(-1);
		gameMusic = new GameMusic();
		gameMusic.playBackgroundMusic();
	}
	
	public void setPopGameMechanics(){	
		getGame().popState();
		getGame().popState();
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
			welcomeSprite.setScale(sx, sx);
			welcomeSprite.setPosition(0, Globals.canvasHeight/4);
			
			i++;
		}
		canvas.drawColor(Color.WHITE);
		welcomeSprite.draw(canvas);
	}

	@Override
	public boolean onTouchDown(MotionEvent me){
		GameInitObject gio = new GameInitObject();		
		getGame().pushState(new HeroChooseView(gio, Globals.PLAYER_ONE, this, gameMusic));
		return true;
	}
}
