package progark.pkg.game;

import sheep.game.State;
import sheep.input.TouchListener;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class GameMechanics extends State implements TouchListener{
	private int scrWidth, scrHeight;
	private boolean setup;
	private InGameGUI Gui;
	private Player player1;
	private Unit selectedUnit;
	private Paint paint;
	
	public GameMechanics() {
		setup = false;
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(40);
		player1 = new Player();
	}

	@Override
	public void draw(Canvas canvas) {
		scrHeight = canvas.getHeight();
		scrWidth = canvas.getWidth();
		//		setup();

		//		Gui.draw(canvas);
		canvas.drawColor(Color.BLUE);
		player1.draw(canvas);
		
		for (Unit u : player1.getUnits()) {
			u.setPosSqX((int)(u.getX()/100));
			u.setPosSqY((int)(u.getY()/100));
		}
		
		if (selectedUnit != null){
			canvas.drawText("Valgt en enhet", 200, 300, paint);
		} else {
			canvas.drawText("Ikke valgt en enhet", 200, 300, paint);
		}

	}

	@Override
	public void update(float dt) {
		super.update(dt);
		player1.update(dt);
	}

	//Blir kun selected hvis man velger en av player1 sine units
	public boolean selectUnit(int x, int y){
		for (Unit u : player1.getUnits()) {
			if (u.getPosX() == x && u.getPosY() == y){
				selectedUnit = u;
				return true;
			}
		}
		return false;
	}
	
	//Blir kun deselected hvis man trykker pŒ samme figur
	public boolean deselectedUnit(int x, int y){
		if (x == selectedUnit.getPosX() && y == selectedUnit.getPosY()){
			selectedUnit = null;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean onTouchDown(MotionEvent me){
		int x = (int)me.getX();
		int y = (int)me.getY();
		
		if (selectedUnit != null){
			//try Do move
			//else deselect
			
		} else if (selectUnit(getSquareX(x), getSquareY(y))){
			//Sjekker om man har valgt en unit
		}
		
		return true;
	}
	
	//St¿rrelsen pŒ hver rute er hardkodet til Œ v¾re 100x100
	public int getSquareX(int x){
		return x/100;
	}
	
	//St¿rrelsen pŒ hver rute er hardkodet til Œ v¾re 100x100
	public int getSquareY(int y){
		return y/100;
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
