package progark.pkg.game;

import sheep.game.State;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

/*
 * Denne får nå startet spillet, men den ser ikke spesielt pen ut. Skal få fikset på det seinere...
 */
public class StartMenuView extends State {
	private Rect rect;
	private Paint paint;
	private Paint paint2;
	private float canvasHeight, canvasWidth;
	private int i = 0;
	private boolean popGameMechanics = false;

	public StartMenuView(){

		paint = new Paint();
		paint.setColor(Color.GREEN);
		paint2 = new Paint();
		paint2.setColor(Color.GRAY);
		paint2.setTextSize(20);

	}
	
	public void setPopGameMechanics(){
		
		getGame().popState();
		getGame().popState();
	}
	
	@Override
	public void update(float dt){

	}

	@Override
	public void draw(Canvas canvas){
		if (i == 0){
			canvasHeight = canvas.getHeight();
			canvasWidth = canvas.getWidth();
			i++;
		}

		rect = new Rect((int)canvasWidth/2 - 200, (int)canvasHeight/2 - 100, (int)canvasWidth/2 + 200, (int)canvasHeight/2 + 100);

		canvas.drawColor(Color.RED);
		canvas.drawRect(rect, paint);
		canvas.drawText("Start Game", (int)canvasWidth/2 - 100, (int)canvasHeight/2 - 25, paint2);
	}

	@Override
	public boolean onTouchDown(MotionEvent me){
		if (rect.contains((int)me.getX(), (int)me.getY()))
				getGame().pushState(new GameMechanics(this));
		return true;
	}
}
