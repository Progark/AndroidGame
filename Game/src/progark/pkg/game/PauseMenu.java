package progark.pkg.game;

import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Image;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

public class PauseMenu extends State{

	private Image pauseImage;
	private Sprite pauseSprite;
	private float sx;

	public PauseMenu(){
		pauseImage = new Image(R.drawable.pause);
		sx = (1.0f*Globals.canvasWidth)/pauseImage.getWidth();
		pauseSprite = new Sprite(pauseImage);
		pauseSprite.setOffset(0, 0);
		pauseSprite.setScale(sx, sx);
		pauseSprite.setPosition(0, Globals.canvasHeight/4);
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
		getGame().popState();
		return true;
	}




}
