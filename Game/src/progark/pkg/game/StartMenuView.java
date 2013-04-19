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
			Globals.canvasHeight = canvas.getHeight();
			Globals.canvasWidth = canvas.getWidth();
			
			i++;
		}

		rect = new Rect((int)Globals.canvasWidth/2 - 200, (int)Globals.canvasHeight/2 - 100, (int)Globals.canvasWidth/2 + 200, (int)Globals.canvasHeight/2 + 100);

		canvas.drawColor(Color.RED);
		canvas.drawRect(rect, paint);
		canvas.drawText("Start Game", (int)Globals.canvasWidth/2 - 100, (int)Globals.canvasHeight/2 - 25, paint2);
	}

	@Override
	public boolean onTouchDown(MotionEvent me){
				getGame().pushState(new GameMechanics(this));
		return true;
	}
}
