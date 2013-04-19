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
	private Player player1, player2;
	private Paint paint;

	private Unit selectedUnit  = null, attackedUnit = null, displayHealthUnit = null;
	private int newPixelXPos = -100, newPixelYPos = -100;	//Dette skal v¾re verdien til ¿verste venstre hj¿rne av korrekt Square. Satt til -100 slik at det er langt utenfor skjermen

	private int speed = Globals.MOVING_SPEED;	//Skal flytte seg 100 piksler per sekund
	private int turn = 0, movesLeft;
	private boolean inAction = false;	//Denne skal settes til true nŒr det skal skje en animasjon som for eksempel det Œ gŒ eller det Œ angripe

	//Har med animering av attack Œ gj¿re
	private float timeLeftOfAnimation = 0;	//NB! Brukes ikke når en unit skal flytte på seg, men heller når den gjør et angrep/blir angrepet/blir sett på health messig
	private int damageMade = -1;
	private String winner;

	private StartMenuView smv;
	
	private BoardMenu boardMenu;
	private Board board;
	private ArrayList<Coordinate> legalMoves;
	
	public GameMechanics(StartMenuView smv) {
		this.smv = smv;
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(25);


		player1 = new Player(1);
		player2 = new Player(2);

		boardMenu = new BoardMenu(this);
		board = new Board();
		legalMoves = new ArrayList<Coordinate>();
	}

	@Override
	public void draw(Canvas canvas) {
		board.draw(canvas);
		boardMenu.draw(canvas);
		player1.draw(canvas);
		player2.draw(canvas);
		
		if (timeLeftOfAnimation > 0 && displayHealthUnit != null)
			canvas.drawText("Health: " + displayHealthUnit.getHealth(), Globals.canvasWidth - Globals.TILE_SIZE - 150, Globals.TILE_SIZE - 50, paint);
		
		if (timeLeftOfAnimation > 0 && attackedUnit != null){
			canvas.drawText("Damage: " + damageMade, Globals.TILE_SIZE + 50, Globals.TILE_SIZE - 50 , paint);
			canvas.drawText("Health: " + attackedUnit.getHealth(), Globals.canvasWidth - Globals.TILE_SIZE - 150, Globals.TILE_SIZE - 50, paint);
		}
	}

	@Override
	public void update(float dt) {
		super.update(dt);
		board.update(dt);
		boardMenu.update(dt);
		//Passer pŒ at angrepsanimasjonen varer i rett tid, at man fŒr lov til Œ trykke pŒ skjermen etter at animasjon er over og at attackedSprite flyttes ut av vinduet. 
		if (timeLeftOfAnimation > 0){
			timeLeftOfAnimation -= dt;
			if (timeLeftOfAnimation <= 0){
				timeLeftOfAnimation = 0;
				
				
				if (selectedUnit != null){
					selectedUnit.setAnimation("S");
					deselectUnit(selectedUnit.getSquareX(), selectedUnit.getSquareY());
				}
				
				board.getTile(attackedUnit.getSquareY(), attackedUnit.getSquareX()).setTileColor(Globals.NORMAL_TILE);
				attackedUnit = null;
				
				inAction = false;
			}
		}
		
		if (inAction == false && movesLeft == 0)
			switchPlayerTurn();

		if (newPixelXPos > -100 || newPixelYPos > -100)
			doMove(dt);

		player1.update(dt);
		player2.update(dt);
		

	}


	@Override
	public boolean onTouchDown(MotionEvent me){
		int squareXClicked = getSquare((int)me.getX());	//Squareposisjon til me.getX()
		int squareYClicked = getSquare((int)me.getY()); //Squareposisjon til me.getY()

		if (!inAction){
			if (selectedUnit != null){
				if (isEmptySquare(squareXClicked, squareYClicked) && squareYClicked != 0){
					//Move
					inAction = true;
//					setLegalMovesSpritePosition(true);
					newPixelXPos = squareXClicked*Globals.TILE_SIZE;
					newPixelYPos = squareYClicked*Globals.TILE_SIZE;
					board.getTile(selectedUnit.getSquareY(), selectedUnit.getSquareX()).setTileColor(Globals.NORMAL_TILE);
					selectedUnit.setAnimation("W");
					movesLeft --;

				} else {
					if (isOppoentSquare(squareXClicked, squareYClicked)){
						//Attack!
						if (!isNeighbor(squareXClicked, squareYClicked)){
							if (selectedUnit.getName().equals("R")){
								inAction = true;
//								setLegalMovesSpritePosition(true);
								timeLeftOfAnimation = Globals.ANIMATION_TIME;
								attackedUnit = getAttackedUnit(squareXClicked, squareYClicked);
								board.getTile(squareYClicked, squareXClicked).setTileColor(Globals.RED_TILE);
								selectedUnit.setAnimation("A");
								doFight();
								movesLeft --;
							}
						} else {
							inAction = true;
//							setLegalMovesSpritePosition(true);
							timeLeftOfAnimation = Globals.ANIMATION_TIME;
							attackedUnit = getAttackedUnit(squareXClicked, squareYClicked);
							board.getTile(squareYClicked, squareXClicked).setTileColor(Globals.RED_TILE);
							selectedUnit.setAnimation("A");
							doFight();
							movesLeft --;
						}
					} else if (isSelectedUnit(squareXClicked, squareYClicked)){
						//Deselct the currently selected unit
						deselectUnit(squareXClicked, squareYClicked);
//						setLegalMovesSpritePosition(true);
					} else {
						//Select the new unit
						selectUnit(squareXClicked, squareYClicked);
					}
				}
			} else {
				//Select a unit
				selectUnit(squareXClicked, squareYClicked);
			}

		}



		return true;
	}

	/**
	 * Velger den uniten man trykker pŒ, sŒ lenge der er en unit der man trykker. Dersom det er en-player sin tur kan man kun velge en-player sine units. Den valgte uniten vil lyse gr¿nt. 
	 * @param squareX - Squareverdi, ikke pikselverdi
	 * @param squareY - Squareverdi, ikke pikselverdi
	 * @return
	 */
	public void selectUnit(int squareX, int squareY){
		if (turn % 2 != 0){
			for (Unit u : player1.getUnits()) {
				if (u.getSquareX() == squareX && u.getSquareY() == squareY){
					selectedUnit = u;
//					theLegalMoves();
//					setLegalMovesSpritePosition(false);
					board.getTile(squareY, squareX).setTileColor(Globals.GREEN_TILE);
				}
			}
		} else {
			for (Unit u : player2.getUnits()) {
				if (u.getSquareX() == squareX && u.getSquareY() == squareY){
					selectedUnit = u;
//					theLegalMoves();
//					setLegalMovesSpritePosition(false);
					board.getTile(squareY, squareX).setTileColor(Globals.GREEN_TILE);
				}
			}
		}
	}

	/**
	 * Dersom en unit er selected, vil den bli deselected dersom man trykker pŒ den igjen
	 * @param squareX - Squareverdi, ikke pikselverdi
	 * @param squareY - Squareverdi, ikke pikselverdi
	 * @return
	 */
	public void deselectUnit(int squareX, int squareY){
		if (squareX == selectedUnit.getSquareX() && squareY == selectedUnit.getSquareY()){
			selectedUnit = null;
			board.getTile(squareY, squareX).setTileColor(Globals.NORMAL_TILE);			
		}
	}

	/**
	 * Kalkulerer resultatet av angrepet. Dersom den ene enheten d¿r blir den fjernet fra playerX.getUnits()
	 */
	public void doFight(){
		damageMade = (int)(selectedUnit.getStrength()*Math.random());
		attackedUnit.setHealth(attackedUnit.getHealth() - damageMade);

		if (attackedUnit.getHealth() < 1){
			if (player1.getUnits().contains(attackedUnit)){
				Unit temp = attackedUnit;
				attackedUnit = null;
				player1.getIndividualHealthBars().remove(player1.getUnits().indexOf(temp));
				player1.getUnits().remove(temp);
				gameOver();
			} else {
				Unit temp = attackedUnit;
				attackedUnit = null;
				player2.getIndividualHealthBars().remove(player2.getUnits().indexOf(temp));
				player2.getUnits().remove(temp);
				gameOver();
			}
		}
	}

	/**
	 * Flytter figuren basert pŒ hvor lang tid det har gŒtt. Figuren vil f¿rst flytter helt til h¿yre/venstre, deretter vil den flyttes
	 * helt opp eller ned. For ¿yeblikket vil figuren gŒ igjennom eventuelle andre figurer
	 * 
	 * NOTE TO SELF! Animasjon er forel¿pig ikke tatt ordentlig hensyn til
	 * @param dt
	 */
	public void doMove(float dt){
		//Hvis figuren har kommet frem sŒ skal den ikke lenger v¾re valgt
		if (newPixelXPos == selectedUnit.getX() && newPixelYPos == selectedUnit.getY()){
			selectedUnit.setAnimation("S");
			deselectUnit(selectedUnit.getSquareX(), selectedUnit.getSquareY());
			newPixelXPos = -100;
			newPixelYPos = -100;
			inAction = false;
		}
		//Hvis figuren skal flyttes mot h¿yre
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
	 * Tar pikselverdi og gj¿r om til Squareverdi
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
	 * Tar inn squareverdi x og y, returnerer uniten som er pŒ gitt square
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
	 * Sjekker om noen har vunnet spillet. Hvis det er tilfellet, lagres navnet pŒ vinneren i strengen
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
	 * Bytter spillertur. Dette innebærer å deselecte selectedUnit, oppdatere turn og sette moves left
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
}
