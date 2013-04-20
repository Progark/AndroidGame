package progark.pkg.game;

import java.util.ArrayList;
import sheep.game.State;
import sheep.input.TouchListener;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class GameMechanics extends State implements TouchListener{
	private Player player1, player2;
	private Paint paint;

	private Unit selectedUnit  = null, attackedUnit = null;
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
	
	private GameInitObject gio;

	public GameMechanics(StartMenuView smv, GameInitObject gio) {
		this.smv = smv;
		this.gio = gio;
		
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(25);
		
		player1 = gio.getP1();
		player2 = gio.getP2();

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

		if (turn % 2 != 0)
			canvas.drawText("Player1's turn", Globals.canvasWidth/2 - 80, Globals.calculatedTileSize/2 + 28, paint);
		else
			canvas.drawText("Player2's turn", Globals.canvasWidth/2 - 80, Globals.calculatedTileSize/2 + 28, paint);

		if (timeLeftOfAnimation > 0 && attackedUnit != null){
			canvas.drawText("Damage: " + damageMade, Globals.calculatedTileSize + 50, Globals.calculatedTileSize/2 + 28 , paint);
			canvas.drawText("Health: " + attackedUnit.getHealth(), Globals.canvasWidth - Globals.calculatedTileSize - 180, Globals.calculatedTileSize/2 + 28, paint);
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
					deselectUnit(selectedUnit.getSquareY(), selectedUnit.getSquareX());
				}
				if (attackedUnit != null){
					board.getTile(attackedUnit.getSquareY(), attackedUnit.getSquareX()).setTileColor(Globals.NORMAL_TILE);
					attackedUnit = null;
				} else {	//Dersom uniten d¿de, vil attackedUnit v¾re null, og det vil derfor ikke v¾re mulig Œ hente ut posisjon til BoardTile. Resetter derfor alle
					for (int i = 0; i < board.getBoard().length; i++) {
						for (int j = 0; j < board.getBoard()[i].length; j++) {
							board.getTile(i, j).setTileColor(Globals.NORMAL_TILE);
						}
					}
				}

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
			if (me.getY() < Globals.calculatedTileSize/2){	//Hvis trykket overst pŒ skjermen
				if (me.getX() > Globals.menuWidth + 2 && me.getX() < Globals.canvasWidth/2 - 2)
					getGame().pushState(new PauseMenu());
				else if (me.getX() > Globals.canvasWidth/2 + 2 && me.getX() < Globals.canvasWidth - Globals.menuWidth - 2){
					if (turn % 2 != 0){
						winner = "Player2";
						getGame().pushState(new GameOver(this));
					}else {
						winner = "Player1";
						getGame().pushState(new GameOver(this));
					}
				}
				
			}else if (selectedUnit != null){
				if (isEmptySquare(squareYClicked, squareXClicked) && squareYClicked > 0 && isLegalMove(squareYClicked, squareXClicked)){
					//Move
					inAction = true;
					setLegalMovesSpritePosition(true);
					newPixelXPos = squareXClicked*Globals.calculatedTileSize;
					newPixelYPos = squareYClicked*Globals.calculatedTileSize;
					board.getTile(selectedUnit.getSquareY(), selectedUnit.getSquareX()).setTileColor(Globals.NORMAL_TILE);
					selectedUnit.setAnimation("W");
					movesLeft --;

				} else {
					if (isOppoentSquare(squareYClicked, squareXClicked)){
						//Attack!
						if (!isNeighbor(squareYClicked, squareXClicked)){
							if (selectedUnit.getName().equals("R") || selectedUnit.getName().equals("Ma")){
								inAction = true;
								setLegalMovesSpritePosition(true);
								timeLeftOfAnimation = Globals.ANIMATION_TIME;
								attackedUnit = getAttackedUnit(squareYClicked, squareXClicked);
								board.getTile(squareYClicked, squareXClicked).setTileColor(Globals.RED_TILE);
								selectedUnit.setAnimation("A");
								doFight();
								movesLeft --;
							}
						} else {
							inAction = true;
							setLegalMovesSpritePosition(true);
							timeLeftOfAnimation = Globals.ANIMATION_TIME;
							attackedUnit = getAttackedUnit(squareYClicked, squareXClicked);
							board.getTile(squareYClicked, squareXClicked).setTileColor(Globals.RED_TILE);
							selectedUnit.setAnimation("A");
							doFight();
							movesLeft --;
						}
					} else if (isSelectedUnit(squareYClicked, squareXClicked)){
						//Deselct the currently selected unit
						deselectUnit(squareYClicked, squareXClicked);
//						setLegalMovesSpritePosition(true);
					} else {
						//Select the new unit
						deselectUnit(selectedUnit.getSquareY(), selectedUnit.getSquareX());
//						selectUnit(squareYClicked, squareXClicked);
					}
				}
			} else {
				//Select a unit
				selectUnit(squareYClicked, squareXClicked);
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
	public void selectUnit(int squareY, int squareX){
		if (turn % 2 != 0){
			for (Unit u : player1.getUnits()) {
				if (u.getSquareX() == squareX && u.getSquareY() == squareY){
					selectedUnit = u;
					theLegalMoves();
					setLegalMovesSpritePosition(false);
					board.getTile(squareY, squareX).setTileColor(Globals.GREEN_TILE);
				}
			}
		} else {
			for (Unit u : player2.getUnits()) {
				if (u.getSquareX() == squareX && u.getSquareY() == squareY){
					selectedUnit = u;
					theLegalMoves();
					setLegalMovesSpritePosition(false);
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
	public void deselectUnit(int squareY, int squareX){
		if (squareX == selectedUnit.getSquareX() && squareY == selectedUnit.getSquareY()){
			setLegalMovesSpritePosition(true);
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
			deselectUnit(selectedUnit.getSquareY(), selectedUnit.getSquareX());
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
		return pixelValue/Globals.calculatedTileSize;
	}

	/**
	 * Tar inn squareverdi y og x og returnerer true dersom det ikke er noe i input-square
	 * @param squareX - Squareverdi, ikke pikselverdi
	 * @param squareY - Squareverdi, ikke pikselverdi
	 * @return
	 */
	public boolean isEmptySquare(int squareY, int squareX){
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
	public boolean isOppoentSquare(int squareY, int squareX){
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
	public boolean isSelectedUnit(int squareY, int squareX){
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
	public boolean isNeighbor(int squareY, int squareX){
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
	public Unit getAttackedUnit(int squareY, int squareX){
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
			deselectUnit(selectedUnit.getSquareY(), selectedUnit.getSquareX());

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

	/**
	 * Setter ny posisjon for legalMovesSprite. hvis variabelen er true resettes alt
	 * @param reset
	 */
	public void setLegalMovesSpritePosition(boolean reset){
		if (reset == true){
			for (Coordinate c : legalMoves) {
				board.getTile(c.getSquareY(), c.getSquareX()).setTileColor(Globals.NORMAL_TILE);
			}
		} else {

			for (Coordinate c : legalMoves) {
				board.getTile(c.getSquareY(), c.getSquareX()).setTileColor(Globals.BLUE_TILE);
			}
		}
	}
	/**
	 * Returnerer true dersom den valgte squaren er en del av de lovlige bevegelsene
	 * @param newSelectedSquareX
	 * @param newSelectedSquareY
	 * @param possibleCoordinates
	 * @return
	 */
	public boolean isLegalMove(int newSelectedSquareY, int newSelectedSquareX){
		for (Coordinate c : legalMoves) {
			if (c.getSquareX() == newSelectedSquareX && c.getSquareY() == newSelectedSquareY)
				return true;
		}
		return false;
	}
	/**
	 * Setter legalMoves til å være lik de nye lovlige movene
	 */
	public void theLegalMoves(){
		ArrayList<Coordinate> temp = new ArrayList<Coordinate>();
		for (int i = 0; i < selectedUnit.getMovement() + 1; i++) {
			int newMovement = selectedUnit.getMovement() - i;
			temp.addAll(plussMinus(selectedUnit.getSquareX(), selectedUnit.getSquareY() - i, newMovement));
			temp.addAll(plussMinus(selectedUnit.getSquareX(), selectedUnit.getSquareY() + i, newMovement));
		}

		//Klarer tydeligvis ikke Œ bruke remove
		for (Coordinate c : temp) {
			if (c.getSquareY() < 1 || c.getSquareX() < 0 || c.getSquareY() > Globals.BOARD_HEIGHT - 1 || c.getSquareX() > Globals.BOARD_WIDTH - 1){
				temp.get(temp.indexOf(c)).setSquareX(selectedUnit.getSquareX());
				temp.get(temp.indexOf(c)).setSquareY(selectedUnit.getSquareY());
			}
		}

		legalMoves = temp;
	}
	/**
	 * *Brukes av theLegalMoves til å finne lovlige move
	 * @param squareX
	 * @param squareY
	 * @param move
	 * @return
	 */
	public ArrayList<Coordinate> plussMinus(int squareX, int squareY, int move){
		ArrayList<Coordinate> temp = new ArrayList<Coordinate>();

		if (move == 0 && isEmptySquare(squareY, squareX)){
			temp.add(new Coordinate(squareY, squareX));
			return temp;
		}

		for (int i = 0; i < move; i++) {
			if (isEmptySquare(squareY, squareX))
				temp.add(new Coordinate(squareY, squareX));
			if (isEmptySquare(squareY, squareX + i + 1))
				temp.add(new Coordinate(squareY, squareX + i + 1));
			if (isEmptySquare(squareY, squareX - i - 1))
				temp.add(new Coordinate(squareY, squareX - i - 1));
		}


		return temp;
	}

	public GameInitObject getGameInitObject(){
		return gio;
	}


}



