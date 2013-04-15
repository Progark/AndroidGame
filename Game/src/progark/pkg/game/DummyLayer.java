package progark.pkg.game;

import sheep.game.State;
import sheep.graphics.Image;
import android.graphics.Canvas;
import android.graphics.Color;

public class DummyLayer extends State{
	
	DummyUnit du;
	
	
	public DummyLayer(){
		du = new DummyUnit(new Image(R.drawable.sw1frame), new Image(R.drawable.sw1frame), new Image(R.drawable.sw2frame));
	}
	
	@Override
	public void update(float dt){
		du.update(dt);
	}
	
	@Override
	public void draw(Canvas canvas){
		canvas.drawColor(Color.GREEN);
		du.draw(canvas);
	}
}
