package progark.pkg.game;

import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Image;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

public class PauseMenuView extends State{

	private Image pauseImage;
	private Sprite pauseSprite;
	private float sx;
	private GameMusic gameMusic;
	private static PauseMenuView instance = null;
	
	private PauseMenuView(GameMusic gameMusic){
		pauseImage = new Image(R.drawable.pause);
		sx = (1.0f*Globals.canvasWidth)/pauseImage.getWidth();
		pauseSprite = new Sprite(pauseImage);
		pauseSprite.setOffset(0, 0);
		pauseSprite.setScale(sx, sx);
		pauseSprite.setPosition(0, Globals.canvasHeight/4);
		this.gameMusic = gameMusic;
		gameMusic.stopBakcgroundMusic();
	}
	
	public static PauseMenuView getInstance(GameMusic gameMusic){
		if(instance == null){
			synchronized(PauseMenuView.class){
				if(instance == null)
					instance = new PauseMenuView(gameMusic);
			}
		}
		return instance;
	}

	@Override
	public void update(float dt){
		super.update(dt);
		pauseSprite.update(dt);
	}

	@Override
	public void draw(Canvas canvas){
		canvas.drawColor(Color.WHITE);
		pauseSprite.draw(canvas);
	}

	@Override
	public boolean onTouchDown(MotionEvent me){
		gameMusic.playBackgroundMusic();
		getGame().popState();
		return true;
	}




}
