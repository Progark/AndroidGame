package progark.pkg.game;

import sheep.game.State;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class GameOver extends State{
	Paint paint, paint2;
	String winner;
	GameMechanics gm;
	
	public GameOver( GameMechanics gm){
		this.gm = gm;
		
		paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setTextSize(50);
		
		
		paint2 = new Paint();
		paint2.setColor(Color.GRAY);
		paint2.setTextSize(20);
		winner = gm.getWinner();
	}
	
	@Override
	public void draw(Canvas canvas){
		canvas.drawColor(Color.GREEN);
		canvas.drawText("The winner is", canvas.getWidth()/2 - 150, 300, paint);
		canvas.drawText(winner, canvas.getWidth()/2 - 120, 380, paint);
		canvas.drawText("To restart, touch the screen", canvas.getWidth()/2 - 150, 450, paint2);
		 
	}
	
	@Override
	public void update(float dt){
		super.update(dt);
		
	}
	
	@Override
	public boolean onTouchDown(MotionEvent me){
		gm.getSMV().setPopGameMechanics();
		return true;
	}
}
