package progark.pkg.game;

import sheep.game.State;
import sheep.input.TouchListener;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

public class GameMechanics extends State implements TouchListener{
	private int scrWidth, scrHeight;
	private boolean setup;
	private InGameGUI Gui;
	private MeleeUnit mu;
	private RangedUnit ru;
	private float newX, newY;
	private boolean isMoving;
	private Player player1;
	private Player player2;

	public GameMechanics() {
		setup = false;
		mu = new MeleeUnit();
		ru = new RangedUnit();
		newX = mu.getX();
		newY = mu.getY();
		player1 = new Player();
		player2 = new Player();
		

	}

	@Override
	public void draw(Canvas canvas) {
		scrHeight = canvas.getHeight();
		scrWidth = canvas.getWidth();
		//		setup();

		//		Gui.draw(canvas);
		canvas.drawColor(Color.BLUE);
		mu.draw(canvas);
		ru.draw(canvas);

	}

	@Override
	public void update(float dt) {
		super.update(dt);
		mu.update(dt);
		ru.update(dt);
		if (newX != mu.getX() || newY != mu.getY()){
			isMoving = true;
		} else {
			isMoving = false;
		}

		if (newX < mu.getX() - 10 || newX > mu.getX() + 10){
			float dx = newX - mu.getX();
			mu.setPosition(mu.getX() + dx/2, mu.getY());
			isMoving = true;
		} else if (newY < mu.getY() - 10 || newY > mu.getY() + 10){
			float dy = newY - mu.getY();
			mu.setPosition(mu.getX(), mu.getY() + dy/2);
			isMoving = true;
		} else 
			isMoving = false;
	}

	@Override
	public boolean onTouchDown(MotionEvent me){
		
		if (!isMoving){
			newX = me.getX();
			newY = me.getY();
		}
		return true;
	}
	/* En metod til å opprette gui'et i henhold til skjermstørrelsen
	 * fordi man i sheep kun kan hente skjermstørrelsen i draw og 
	 * dermed må opprette ting etter dette. 
	 * 
	 * Denne gj¿r forel¿pig at emulatoren ikke kan starte
	 */
	private void setup(){
		if (!setup) {
			Gui = new InGameGUI(scrHeight, scrWidth);
			setup = true;
		}
	}
}
