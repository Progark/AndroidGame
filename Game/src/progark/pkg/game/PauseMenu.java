package progark.pkg.game;

import sheep.game.State;
import android.R.color;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class PauseMenu extends State{

	private Rect rect;
	private Paint paint1, paint2;
	private int canvasHeight, canvasWidth;
	private int i = 0;
	
	public PauseMenu(){
		this.paint1 = new Paint(Color.WHITE);
		this.paint2 = new Paint(Color.BLACK);
		paint2.setTextSize(35);
		
	}
	
	@Override
	public void draw(Canvas canvas){
		if(i == 0){
			canvasHeight = canvas.getHeight();
			canvasWidth = canvas.getWidth();
			i++;
		}
		rect = new Rect(canvasWidth/2 - 200, canvasHeight/2 - 100, canvasWidth/2+200, canvasHeight/2 + 100);
		canvas.drawColor(color.darker_gray);
		canvas.drawRect(rect, paint1);
		canvas.drawText("Resume",canvasHeight/2-100, canvasWidth/2-25, paint2);
		canvas.drawText("You have paused the game", 20, canvasWidth/2 -25, paint1);
	}
	
	@Override
	public boolean onTouchDown(MotionEvent me){
		if(rect.contains((int)me.getX(), (int)me.getY())){
			getGame().popState();
		}
		return true;
	}
	
	
	
	
}
