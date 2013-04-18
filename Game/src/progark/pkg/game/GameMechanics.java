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
//	private int scrWidth, scrHeight;
	private boolean setup;
	private InGameGUI Gui;
	private Player player1, player2;
	private Paint paint;

	private Unit selectedUnit  = null, attackedUnit = null;
	private Sprite selectedUnitSprite, attackedSprite;

	private int newPixelXPos = -100, newPixelYPos = -100;	//Dette skal være verdien til øverste venstre hjørne av korrekt Square. Satt til -100 slik at det er langt utenfor skjermen

	private int speed = Globals.MOVING_SPEED;	//Skal flytte seg 100 piksler per sekund
	private int turn = 0, movesLeft;
	private boolean inAction = false;	//Denne skal settes til true når det skal skje en animasjon som for eksempel det å gå eller det å angripe
	private boolean displayHealth = false;
	private Unit displayHealthUnit;
	private Sprite displayHealthUnitSprite;
	//Har med animering av attack å gjøre
	private float timeLeftOfAttackAnimation = 0;
	private int damageMade = -1;
	private String winner;

	private StartMenuView smv;
	
	private BoardMenu boardMenu;
	private Board board;
	
	public GameMechanics(StartMenuView smv) {
		this.smv = smv;
		setup = false;
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(25);


		player1 = new Player(1);
		player2 = new Player(2);
		
		//Denne brukes for ¯yeblikket til Â settes st¯rrelsten pÂ selectedUnitSprite og attackSprite
		//MÂ derfor ikke slettes
		Image image = new Image(R.drawable.tile);
		float sx = Globals.TILE_SIZE/image.getWidth();
		float sy = Globals.TILE_SIZE/image.getHeight();

		

		//Sprite som endrer fargen på bakgrunnen til den valgte uniten
		selectedUnitSprite = new Sprite(new Image(R.drawable.tilegreen));
		selectedUnitSprite.setOffset(0, 0);
		selectedUnitSprite.setScale(sx, sy);
		selectedUnitSprite.setPosition(-150, 150);

		//Sprite som lyser opp å på den som blir angrepet
		attackedSprite = new Sprite(new Image(R.drawable.tilered));
		attackedSprite.setOffset(0, 0);
		attackedSprite.setScale(sx, sy);
		attackedSprite.setPosition(-150, -150);
		
		//Sprite som lyser opp dersom man skal vite livet
		displayHealthUnitSprite = new Sprite(new Image(R.drawable.tilegreen));
		displayHealthUnitSprite.setOffset(0,0);
		displayHealthUnitSprite.setScale(sx, sy);
		displayHealthUnitSprite.setPosition(-150, -150);

		boardMenu = new BoardMenu(this);
		board = new Board();
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawColor(Color.GREEN);
		
		if (selectedUnit != null){
			selectedUnitSprite.draw(canvas);
		} 
		if (timeLeftOfAttackAnimation > 0 && attackedUnit != null){
			attackedSprite.draw(canvas);
		}
		
		
		board.draw(canvas);
		
		

		for (Unit unit : player1.getUnits()) {
			unit.setSquareX((int)(unit.getX()/Globals.TILE_SIZE));
			unit.setSquareY((int)(unit.getY()/Globals.TILE_SIZE));
		}

		for (Unit u : player2.getUnits()) {
			u.setSquareX((int)(u.getX()/Globals.TILE_SIZE));
			u.setSquareY((int)(u.getY()/Globals.TILE_SIZE));
		}

		boardMenu.draw(canvas);
	
		
		if (timeLeftOfAttackAnimation > 0 && displayHealthUnit != null){
			displayHealthUnitSprite.draw(canvas);
			canvas.drawText("Health: " + displayHealthUnit.getHealth(), Globals.canvasWidth - Globals.TILE_SIZE - 150, Globals.TILE_SIZE - 50, paint);
		}
		
		if (timeLeftOfAttackAnimation > 0 && attackedUnit != null){
			canvas.drawText("Damage: " + damageMade, Globals.TILE_SIZE + 50, Globals.TILE_SIZE - 50 , paint);
			canvas.drawText("Health: " + attackedUnit.getHealth(), Globals.canvasWidth - Globals.TILE_SIZE - 150, Globals.TILE_SIZE - 50, paint);
		}
		
		
		player1.draw(canvas);
		player2.draw(canvas);
	}

	@Override
	public void update(float dt) {
		super.update(dt);

		
		board.update(dt);
		//Passer på at angrepsanimasjonen varer i rett tid, at man får lov til å trykke på skjermen etter at animasjon er over og at attackedSprite flyttes ut av vinduet. 
		if (timeLeftOfAttackAnimation > 0){
			timeLeftOfAttackAnimation -= dt;
			if (timeLeftOfAttackAnimation <= 0){
				timeLeftOfAttackAnimation = 0;
				
				
				if (selectedUnit != null){
					selectedUnit.setAnimation("S");
					deselectUnit(selectedUnit.getSquareX(), selectedUnit.getSquareY());
				}
				attackedSprite.setPosition(-150, -150);
				attackedUnit = null;
				displayHealthUnitSprite.setPosition(-150, -150);
				displayHealthUnit = null;
				inAction = false;
			}
		}
		
		if (inAction == false && movesLeft == 0)
			switchPlayerTurn();

		if (newPixelXPos > -100 || newPixelYPos > -100)
			doMove(dt);

		selectedUnitSprite.update(dt);
		attackedSprite.update(dt);
		displayHealthUnitSprite.update(dt);

		player1.update(dt);
		player2.update(dt);
		boardMenu.update(dt);

	}


	@Override
	public boolean onTouchDown(MotionEvent me){
		int squareXClicked = getSquare((int)me.getX());	//Squareposisjon til me.getX()
		int squareYClicked = getSquare((int)me.getY()); //Squareposisjon til me.getY()

		if (!inAction){
			if (selectedUnit != null){
				if (isEmptySquare(squareXClicked, squareYClicked)){
					//Move
					inAction = true;
					newPixelXPos = squareXClicked*Globals.TILE_SIZE;
					newPixelYPos = squareYClicked*Globals.TILE_SIZE;
					selectedUnitSprite.setPosition(-150, -150);	//Flytter ("fjerner") valgt sprite utenfor vinduet
					selectedUnit.setAnimation("W");
					movesLeft --;

				} else {
					if (isOppoentSquare(squareXClicked, squareYClicked)){
						//Attack!
						if (!isNeighbor(squareXClicked, squareYClicked)){
							if (selectedUnit.getName().equals("R")){
								inAction = true;
								timeLeftOfAttackAnimation = Globals.ANIMATION_TIME;
								attackedUnit = getAttackedUnit(squareXClicked, squareYClicked);
								attackedSprite.setPosition(squareXClicked*Globals.TILE_SIZE, squareYClicked*Globals.TILE_SIZE);
								selectedUnit.setAnimation("A");
								doFight();
								movesLeft --;
							}
						} else {
							inAction = true;
							timeLeftOfAttackAnimation = Globals.ANIMATION_TIME;
							attackedUnit = getAttackedUnit(squareXClicked, squareYClicked);
							attackedSprite.setPosition(squareXClicked*Globals.TILE_SIZE, squareYClicked*Globals.TILE_SIZE);
							selectedUnit.setAnimation("A");
							doFight();
							movesLeft --;
						}
						//IF: ikke nabo, 
						//IF: selectedUnit == Archer, THEN: Attak with arrow
						//ELSE: da vet vi at det er en melee enhet
						//IF: avstand innenfor movement, THEN: Move, THEN: Attack
						//ELSE: attack
					} else if (isSelectedUnit(squareXClicked, squareYClicked)){
						//Deselct
						deselectUnit(squareXClicked, squareYClicked);
					} else {
						//Select
						selectUnit(squareXClicked, squareYClicked);
					}
				}
			} else {
				if (isOppoentSquare(squareXClicked, squareYClicked)){
					//Display health left of opponent
					inAction = true;
					displayHealthUnit = getAttackedUnit(squareXClicked, squareYClicked);
					displayHealthUnitSprite.setPosition(squareXClicked*Globals.TILE_SIZE, squareYClicked*Globals.TILE_SIZE);
					timeLeftOfAttackAnimation = Globals.ANIMATION_TIME;
					
				} else {
				//Select
				selectUnit(squareXClicked, squareYClicked);
				}
			}

		}



		return true;
	}

	/**
	 * Velger den uniten man trykker på, så lenge der er en unit der man trykker. Dersom det er en-player sin tur kan man kun velge en-player sine units. Den valgte uniten vil lyse grønt. 
	 * @param squareX - Squareverdi, ikke pikselverdi
	 * @param squareY - Squareverdi, ikke pikselverdi
	 * @return
	 */
	public void selectUnit(int squareX, int squareY){
		if (turn % 2 != 0){
			for (Unit u : player1.getUnits()) {
				if (u.getSquareX() == squareX && u.getSquareY() == squareY){
					selectedUnit = u;
					selectedUnitSprite.setPosition(selectedUnit.getSquareX()*Globals.TILE_SIZE, selectedUnit.getSquareY()*Globals.TILE_SIZE);
				}
			}
		} else {
			for (Unit u : player2.getUnits()) {
				if (u.getSquareX() == squareX && u.getSquareY() == squareY){
					selectedUnit = u;
					selectedUnitSprite.setPosition(selectedUnit.getSquareX()*Globals.TILE_SIZE, selectedUnit.getSquareY()*Globals.TILE_SIZE);
				}
			}
		}
	}

	/**
	 * Dersom en unit er selected, vil den bli deselected dersom man trykker på den igjen
	 * @param squareX - Squareverdi, ikke pikselverdi
	 * @param squareY - Squareverdi, ikke pikselverdi
	 * @return
	 */
	public void deselectUnit(int squareX, int squareY){
		if (squareX == selectedUnit.getSquareX() && squareY == selectedUnit.getSquareY()){
			selectedUnit = null;
			selectedUnitSprite.setPosition(-150, -150);
		}
	}

	/**
	 * Kalkulerer resultatet av angrepet. Dersom den ene enheten dør blir den fjernet fra playerX.getUnits()
	 */
	public void doFight(){
		damageMade = (int)(selectedUnit.getStrength()*Math.random());
		attackedUnit.setHealth(attackedUnit.getHealth() - damageMade);

		if (attackedUnit.getHealth() < 1){
			if (player1.getUnits().contains(attackedUnit)){
				Unit temp = attackedUnit;
				attackedUnit = null;
				player1.getUnits().remove(temp);
				gameOver();
			} else {
				Unit temp = attackedUnit;
				attackedUnit = null;
				player2.getUnits().remove(temp);
				gameOver();
			}
		}
	}

	/**
	 * Flytter figuren basert på hvor lang tid det har gått. Figuren vil først flytter helt til høyre/venstre, deretter vil den flyttes
	 * helt opp eller ned. For øyeblikket vil figuren gå igjennom eventuelle andre figurer
	 * 
	 * NOTE TO SELF! Animasjon er foreløpig ikke tatt ordentlig hensyn til
	 * @param dt
	 */
	public void doMove(float dt){
		//Hvis figuren har kommet frem så skal den ikke lenger være valgt
		if (newPixelXPos == selectedUnit.getX() && newPixelYPos == selectedUnit.getY()){
			selectedUnit.setAnimation("S");
			deselectUnit(selectedUnit.getSquareX(), selectedUnit.getSquareY());
			newPixelXPos = -100;
			newPixelYPos = -100;
			inAction = false;
		}
		//Hvis figuren skal flyttes mot høyre
		else if (selectedUnit.getX() < newPixelXPos){
			inAction = true;
			if (selectedUnit.getX() > newPixelXPos - 21)	//Hvis figuren er innenfor denne avstanden blir den automatisk flyttet til riktig x posisjon
				selectedUnit.setPosition(newPixelXPos, selectedUnit.getY());
			else
				selectedUnit.setPosition(selectedUnit.getX() + speed*dt, selectedUnit.getY());
		} 
		//Hvis figuren skal flyttes mot venstre
		else if (selectedUnit.getX() > newPixelXPos){
			inAction = true;
			if (selectedUnit.getX() < newPixelXPos + 21) //Hvis figuren er innenfor denne avstanden blir den automatisk flyttet til riktig x posisjon
				selectedUnit.setPosition(newPixelXPos, selectedUnit.getY());
			else
				selectedUnit.setPosition(selectedUnit.getX() - speed*dt, selectedUnit.getY());
		}
		//Hvis figuren skal flyttes mot bunnen
		else if (selectedUnit.getY() < newPixelYPos){
			inAction = true;
			if (selectedUnit.getY() > newPixelYPos - 21) //Hvis figuren er innenfor denne avstanden blir den automatisk flyttet til riktig y posisjon
				selectedUnit.setPosition(selectedUnit.getX(), newPixelYPos);
			else
				selectedUnit.setPosition(selectedUnit.getX(), selectedUnit.getY() + speed*dt);
		} 
		//Hvis figuren skal flyttes mot toppen
		else if (selectedUnit.getY() > newPixelYPos){
			inAction = true;
			if (selectedUnit.getY() < newPixelYPos + 21) //Hvis figuren er innenfor denne avstanden blir den automatisk flyttet til riktig x posisjon
				selectedUnit.setPosition(selectedUnit.getX(), newPixelYPos);
			else
				selectedUnit.setPosition(selectedUnit.getX(), selectedUnit.getY() - speed*dt);
		}
	}

	/**
	 * Tar pikselverdi og gjør om til Squareverdi
	 * @param pixelValue
	 * @return SquareValue
	 */
	public int getSquare(int pixelValue){
		return pixelValue/Globals.TILE_SIZE;
	}

	/**
	 * Tar inn squareverdi x og y og returnerer true dersom det ikke er noe i input-square
	 * @param squareX - Squareverdi, ikke pikselverdi
	 * @param squareY - Squareverdi, ikke pikselverdi
	 * @return
	 */
	public boolean isEmptySquare(int squareX, int squareY){
		for (Unit u : player1.getUnits()) {
			if (u.getSquareX() == squareX && u.getSquareY() == squareY)
				return false;
		}
		for (Unit u : player2.getUnits()) {
			if (u.getSquareX() == squareX && u.getSquareY() == squareY)
				return false;
		}
		return true;
	}

	/**
	 * Tar inn squareverdi x og y, returnerer true dersom det er en square som inneholder motstanderen sin unit
	 * @param squareX - Squareverdi, ikke pikselverdi
	 * @param squareY - Squareverdi, ikke pikselverdi
	 * @return
	 */
	public boolean isOppoentSquare(int squareX, int squareY){
		//Hvis en-player sin tur
		if ((turn % 2) != 0){
			for (Unit u : player2.getUnits()) {
				if (u.getSquareX() == squareX && u.getSquareY() == squareY)
					return true;
			}
		} 
		//Hvis to-player sin tur
		else {
			for (Unit u : player1.getUnits()) {
				if (u.getSquareX() == squareX && u.getSquareY() == squareY)
					return true;
			}
		}
		return false;
	}

	/**
	 * Tar inn squareverdi x og y, returnerer true dersom squaren inneholder selectedUnit
	 * @param squareX - Squareverdi, ikke pixelverdi
	 * @param squareY - Squareverdi, ikke pixelverdi
	 * @return
	 */
	public boolean isSelectedUnit(int squareX, int squareY){
		if (selectedUnit.getSquareX() == squareX && selectedUnit.getSquareY() == squareY)
			return true;
		return false;
	}

	/**
	 * Tar inn squareverdi x og y, returnerer true dersom squaren er naboen til selectedUnit
	 * @param squareX
	 * @param squareY
	 * @return
	 */
	public boolean isNeighbor(int squareX, int squareY){
		if (Math.abs(selectedUnit.getSquareX() - squareX) < 2 && Math.abs(selectedUnit.getSquareY() - squareY) < 2)
			return true;
		return false;
	}

	/**
	 * Tar inn squareverdi x og y, returnerer uniten som er på gitt square
	 * @param squareX - Squareverdi, ikke pixelverdi
	 * @param squareY - Squareverdi, ikke pixelverdi
	 * @return
	 */
	public Unit getAttackedUnit(int squareX, int squareY){
		if ((turn % 2) != 0 ){
			for (Unit u : player2.getUnits()) {
				if (u.getSquareX() == squareX && u.getSquareY() == squareY)
					return u;
			}
		} else {
			for (Unit u : player1.getUnits()) {
				if (u.getSquareX() == squareX && u.getSquareY() == squareY)
					return u;
			}
		}
		return null;
	}


	/**
	 * Sjekker om noen har vunnet spillet. Hvis det er tilfellet, lagres navnet på vinneren i strengen
	 * winner og den returnerer true. Ellers returnerer den false.
	 * @return
	 */
	public void gameOver(){
		if (player1.getHealth() == 0){
			winner = "Player 2";
			getGame().pushState(new GameOver(this));
		}else if (player2.getHealth() == 0){
			winner = "Player 1";
			getGame().pushState(new GameOver(this));
		}
	}

	/**
	 * Bytter spillertur. Dette innebÊrer Â deselecte selectedUnit, oppdatere turn og sette moves left
	 */
	public void switchPlayerTurn(){
		//Deselecter alt som er valgt
		if (selectedUnit != null)
			deselectUnit(selectedUnit.getSquareX(), selectedUnit.getSquareY());
		
		turn ++;
		if ((turn % 2) != 0)
			movesLeft = player1.getUnits().size();
		else
			movesLeft = player2.getUnits().size();
	}

	public String getWinner(){
		return winner;
	}
	
	public StartMenuView getSMV(){
		return smv;
	}
	
	public Player getPlayer1(){
		return player1;
	}
	
	public Player getPlayer2(){
		return player2;
	}
	
	public int getTurn(){
		return turn;
	}

	/* En metod til Â opprette gui'et i henhold til skjermst¯rrelsen
	 * fordi man i sheep kun kan hente skjermst¯rrelsen i draw og 
	 * dermed mÂ opprette ting etter dette. 
	 */
//	private void setup(){
//		if (!setup) {
//			Gui = new InGameGUI(Globals.canvasHeight, Globals.canvasWidth);
//			setup = true;
//		}
//	}
}
















