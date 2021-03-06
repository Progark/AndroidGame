package progark.pkg.game;

import sheep.audio.Sound;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Image;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

public class GameOverView extends State{

	private GameMechanics gm;
	private float sx;
	private Sprite winnerSprite;
	private Image winnerImage;
	private GameMusic gameMusic;
	private static GameOverView instance = null;
	
	public GameOverView(GameMechanics gm, GameMusic gameMusic){
		this.gm = gm;
		this.gameMusic = gameMusic;
		if (gm.getTurn() % 2 != 0){
			winnerImage = new Image(R.drawable.wp1);
			sx = (1.0f*Globals.canvasWidth)/winnerImage.getWidth();
			winnerSprite = new Sprite(winnerImage);
			winnerSprite.setOffset(0, 0);
			winnerSprite.setScale(sx, sx);
			winnerSprite.setPosition(0, Globals.canvasHeight/4);
		} else {
			winnerImage = new Image(R.drawable.wp2);
			sx = (1.0f*Globals.canvasWidth)/winnerImage.getWidth();
			winnerSprite = new Sprite(winnerImage);
			winnerSprite.setOffset(0, 0);
			winnerSprite.setScale(sx, sx);
			winnerSprite.setPosition(0, Globals.canvasHeight/4);
		}
		
		Sound sound = new Sound(R.raw.winning);
		sound.play();
//		Globals.backgroundMusic.stop();
		gameMusic.stopBakcgroundMusic();
	}

	@Override
	public void draw(Canvas canvas){
		canvas.drawColor(Color.WHITE);
		winnerSprite.draw(canvas);

	}

	@Override
	public void update(float dt){
		super.update(dt);
		winnerSprite.update(dt);

	}

	@Override
	public boolean onTouchDown(MotionEvent me){
		gameMusic.playBackgroundMusic();
		gm.getSMV().setPopGameMechanics();
		return true;
	}
}
