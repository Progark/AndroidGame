package progark.pkg.game;

import java.util.ArrayList;

import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Image;
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
	private Sprite selectedUnitSprite;
	private ArrayList<ArrayList<Sprite>> sprites;
	private int newXPos = -1, newYPos = -1;
	private int speed = 200;	//Skal flytte seg 100 piksler per sekund

	public GameMechanics() {
		setup = false;
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(25);
		
		
		player1 = new Player();
		//Bakgrunnen
		sprites = new ArrayList<ArrayList<Sprite>>();
		Image image = new Image(R.drawable.tile);
		float sx = 100/image.getWidth();
		float sy = 100/image.getHeight();
		for (int i = 0; i < 9; i++) {
			sprites.add(new ArrayList<Sprite>());
			for (int j = 0; j < 7; j++) {
				sprites.get(i).add(new Sprite(image));
				sprites.get(i).get(j).setPosition(j*100, i*100);
				sprites.get(i).get(j).setOffset(0,0);
				sprites.get(i).get(j).setScale(sx, sy);
			}
		}

		//Sprite som endrer fargen på bakgrunnen til den valgte uniten
		selectedUnitSprite = new Sprite(new Image(R.drawable.tilegreen));
		selectedUnitSprite.setOffset(0, 0);
		selectedUnitSprite.setScale(sx, sy);
		selectedUnitSprite.setPosition(-150, 150);
	}

	@Override
	public void draw(Canvas canvas) {
		scrHeight = canvas.getHeight();
		scrWidth = canvas.getWidth();
		setup();

<<<<<<< HEAD
		Gui.draw(canvas);
		//canvas.drawColor(Color.BLUE);
		player1.draw(canvas);
		
		for (Unit u : player1.getUnits()) {
			u.setPosSqX((int)(u.getX()/100));
			u.setPosSqY((int)(u.getY()/100));
=======
		//		Gui.draw(canvas);
		//Tegner bakken under/bak hver av unitene
		for (ArrayList<Sprite> spriteList : sprites) {
			for (Sprite s : spriteList) {
				s.draw(canvas);
			}
>>>>>>> Singleplayer spilling er n√• mulig
		}


		if (selectedUnit != null){
			selectedUnitSprite.draw(canvas);
		} else {
			canvas.drawText("Ikke valgt en enhet", 200, 300, paint);			
		}
		player1.draw(canvas);
		for (Unit u : player1.getUnits()) {
			u.setSquareX((int)(u.getX()/100));
			u.setSquareY((int)(u.getY()/100));
		}

		canvas.drawText("nX: " + newXPos, 200, 200, paint);

	}

	@Override
	public void update(float dt) {
		super.update(dt);
		for (ArrayList<Sprite> spriteList : sprites) {
			for (Sprite s : spriteList) {
				s.update(dt);
			}
		}

		if (newXPos > -1 || newYPos > -1)
			doMove(dt);

		selectedUnitSprite.update(dt);

		player1.update(dt);

	}

	//Blir kun selected hvis man velger en av player1 sine units
	public boolean selectUnit(int x, int y){
		for (Unit u : player1.getUnits()) {
			if (u.getSquareX() == x && u.getSquareY() == y){
				selectedUnit = u;
				selectedUnitSprite.setPosition(selectedUnit.getSquareX()*100, selectedUnit.getSquareY()*100);
				return true;
			}
		}
		return false;
	}

	//Blir kun deselected hvis man trykker på samme figur
	public boolean deselectedUnit(int x, int y){
		if (x == selectedUnit.getSquareX() && y == selectedUnit.getSquareY()){
			selectedUnit = null;
			selectedUnitSprite.setPosition(-150, -150);
			return true;
		}
		return false;
	}

	public void doMove(float dt){
		
		//Hvis figuren har kommet frem så skal den ikke lenger være valgt
		if (newXPos == selectedUnit.getX() && newYPos == selectedUnit.getY()){
			selectedUnit = null;
			newXPos = -1;
			newYPos = -1;
		}
		//Hvis figuren skal flyttes mot høyre
		else if (selectedUnit.getX() < newXPos){
			if (selectedUnit.getX() > newXPos - 21)
				selectedUnit.setPosition(newXPos, selectedUnit.getY());
			else
				selectedUnit.setPosition(selectedUnit.getX() + speed*dt, selectedUnit.getY());
		} 
		//Hvis figuren skal flyttes mot venstre
		else if (selectedUnit.getX() > newXPos){
			if (selectedUnit.getX() < newXPos + 21)
				selectedUnit.setPosition(newXPos, selectedUnit.getY());
			else
				selectedUnit.setPosition(selectedUnit.getX() - speed*dt, selectedUnit.getY());
		}
		//Hvis figuren skal flyttes mot bunnen
		else if (selectedUnit.getY() < newYPos){
			if (selectedUnit.getY() > newYPos - 21)
				selectedUnit.setPosition(selectedUnit.getX(), newYPos);
			else
				selectedUnit.setPosition(selectedUnit.getX(), selectedUnit.getY() + speed*dt);
		} 
		//Hvis figuren skal flyttes mot toppen
		else if (selectedUnit.getY() > newYPos){
			if (selectedUnit.getY() < newYPos + 21)
				selectedUnit.setPosition(selectedUnit.getX(), newYPos);
			else
				selectedUnit.setPosition(selectedUnit.getX(), selectedUnit.getY() - speed*dt);
		}
	}

	@Override
	public boolean onTouchDown(MotionEvent me){
		int x = (int)me.getX();	//Piksel posisjon x
		int y = (int)me.getY();	//Piksel posisjon y

		//Hvis en unit allerede er valgt
		if (selectedUnit != null){
			Unit temp = selectedUnit;
			
			//Hvis den valgte figuren skal bevege seg
			if (x != selectedUnit.getSquareX() || y != selectedUnit.getSquareY()){
				newXPos = ((int)(x/100))*100;	//Piksel til øverste venstre punkt i rute posisjon x
				newYPos = ((int)(y/100))*100;	//Piksel til øverste venstre punkt i rute posisjon y
				selectedUnitSprite.setPosition(-150, -150);
			} 
			//Hvis man prøver å "deselecte" en allerede valgt figur ved å klikke på den igjen
			else if (deselectedUnit(getSquareX(x), getSquareY(y))){
			} 
			//Hvis man prøver å velge en annen figur enn den som allerede er valgt skal den nye figuren velges og den gamle figuren deselectes
			else if (selectUnit(getSquareX(x), getSquareY(y)))
				deselectedUnit(temp.getSquareX(), temp.getSquareY());
		} 
		//Hvis ingen unit er valgt, men man velger en unit
		else if (selectUnit(getSquareX(x), getSquareY(y))){
			//Do nothing
		}
		
		return true;
	}

	//Størrelsen på hver rute er hardkodet til å være 100x100
	public int getSquareX(int x){
		return x/100;
	}

	//Størrelsen på hver rute er hardkodet til å være 100x100
	public int getSquareY(int y){
		return y/100;
	}







	/* En metod til Â opprette gui'et i henhold til skjermst¯rrelsen
	 * fordi man i sheep kun kan hente skjermst¯rrelsen i draw og 
	 * dermed mÂ opprette ting etter dette. 
	 */
	private void setup(){
		if (!setup) {
			Gui = new InGameGUI(scrHeight, scrWidth);
			setup = true;
		}
	}
}
