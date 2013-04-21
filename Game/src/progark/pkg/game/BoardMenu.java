package progark.pkg.game;

import sheep.game.Sprite;
import sheep.graphics.Image;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class BoardMenu {

	private Image hero1, hero2;
	private Rect lifeBar1, lifeBar2, greyBar, pauseButton, forfeitButton;
	private Paint paintLTGray, paintBlack, paintRed, paintGreen, paintBlue;
	private GameMechanics gm;
	private Sprite player1, player2;
	private float sx, sy;
	
	public BoardMenu(GameMechanics gm){
		paintLTGray = new Paint();
		paintLTGray.setColor(Color.LTGRAY);
		paintLTGray.setTextSize(25);
		paintBlack = new Paint();
		paintBlack.setColor(Color.BLACK);
		paintBlack.setTextSize(18);
		paintRed = new Paint();
		paintRed.setColor(Color.RED);
		paintGreen = new Paint();
		paintGreen.setColor(Color.GREEN);
		paintBlue = new Paint();
		paintBlue.setColor(Color.BLUE);
		this.gm = gm;
		
		
		if (gm.getPlayer1().getPlayerName().equals("Hero1")){
			hero1 = new Image(R.drawable.playerone);
			player1 = new Sprite(hero1);
		} else if (gm.getPlayer1().getPlayerName().equals("Hero2")){
			hero1 = new Image(R.drawable.playertwo);
			player1 = new Sprite(hero1);
		} else if (gm.getPlayer1().getPlayerName().equals("Viking")) {
			hero1 = new Image(R.drawable.viking);
			player1 = new Sprite(hero1);
		} else if (gm.getPlayer1().getPlayerName().equals("Princess")) {
			hero1 = new Image(R.drawable.princess);
			player1 = new Sprite(hero1);
		}
		
		if (gm.getPlayer2().getPlayerName().equals("Hero1")){
			hero2 = new Image(R.drawable.playerone);
			player2 = new Sprite(hero2);
		} else if (gm.getPlayer2().getPlayerName().equals("Hero2")){
			hero2 = new Image(R.drawable.playertwo);
			player2 = new Sprite(hero2);
		} else if (gm.getPlayer2().getPlayerName().equals("Viking")){
			hero2 = new Image(R.drawable.viking);
			player2 = new Sprite(hero2);
		} else if (gm.getPlayer2().getPlayerName().equals("Princess")){
			hero2 = new Image(R.drawable.princess);
			player2 = new Sprite(hero2);
		}
		
		Globals.menuWidth = Globals.canvasWidth/Globals.MENU_PARTS;		//For øyeblikket deles denne på seks
		
		sx = Globals.menuWidth/hero1.getWidth();
		sy = (Globals.calculatedTileSize - 10 )/hero1.getHeight();
		
		
		greyBar = new Rect(0, 0, Globals.canvasWidth, Globals.calculatedTileSize);
		pauseButton = new Rect(Globals.menuWidth + 2, 0, Globals.canvasWidth/2 - 2, Globals.calculatedTileSize/2);
		forfeitButton = new Rect(Globals.canvasWidth/2 + 2, 0, Globals.canvasWidth - Globals.menuWidth - 2, Globals.calculatedTileSize/2);
		
		player1.setOffset(0, 0);
		player1.setScale(sx, sy);
		player1.setPosition(0, 0);
		
		player2.setOffset(0, 0);
		player2.setScale(sx, sy);
		player2.setPosition(Globals.canvasWidth - Globals.menuWidth, 0);
		
	}

	
	
	public void draw(Canvas canvas){
		canvas.drawRect(greyBar, paintLTGray);
		canvas.drawRect(pauseButton, paintBlack);
		canvas.drawText("PAUSE", Globals.menuWidth + 80, Globals.calculatedTileSize/4 + 5, paintLTGray);
		canvas.drawRect(forfeitButton, paintBlack);
		canvas.drawText("FORFEIT", Globals.canvasWidth/2 + Globals.menuWidth - 50, Globals.calculatedTileSize/4 + 5, paintLTGray);
		
		
		player1.draw(canvas);
		player2.draw(canvas);
		
		int healthPlayer1 = (int)(gm.getPlayer1().getHealth()/gm.getPlayer1().getMaxTotalHealth()*Globals.menuWidth);
		int healthPlayer2 = (int)(gm.getPlayer2().getHealth()/gm.getPlayer2().getMaxTotalHealth()*Globals.menuWidth);
	
		
		lifeBar1 = new Rect(0, Globals.calculatedTileSize - 10, healthPlayer1, Globals.calculatedTileSize);
		lifeBar2 = new Rect(Globals.canvasWidth - healthPlayer2, Globals.calculatedTileSize - 10, Globals.canvasWidth, Globals.calculatedTileSize);
		
		if (gm.getPlayer1().getHealth()/gm.getPlayer1().getMaxTotalHealth() < 0.25)
			canvas.drawRect(lifeBar1, paintRed);
		else
			canvas.drawRect(lifeBar1, paintGreen);
		
		if (gm.getPlayer2().getHealth()/gm.getPlayer2().getMaxTotalHealth() < 0.25)
			canvas.drawRect(lifeBar2, paintRed);
		else
			canvas.drawRect(lifeBar2, paintGreen);
	}
	
	public void update(float dt){
		player1.update(dt);
		player2.update(dt);
	}
}