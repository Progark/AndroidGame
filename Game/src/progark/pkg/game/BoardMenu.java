package progark.pkg.game;

import sheep.game.Sprite;
import sheep.graphics.Image;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class BoardMenu {

	private int  menuWidth; 
	private Image hero1, hero2;
	private Rect lifeBar1, lifeBar2, greyBar;
	private Paint paintLTGray, paintBlack, paintRed, paintGreen;
	private GameMechanics gm;
	private Sprite player1, player2;
	private float sx, sy;
	
	public BoardMenu(GameMechanics gm){
		paintLTGray = new Paint();
		paintLTGray.setColor(Color.LTGRAY);
		paintBlack = new Paint();
		paintBlack.setColor(Color.BLACK);
		paintBlack.setTextSize(18);
		paintRed = new Paint();
		paintRed.setColor(Color.RED);
		paintGreen = new Paint();
		paintGreen.setColor(Color.GREEN);
		this.gm = gm;
		
		hero1 = new Image(R.drawable.playerone);
		hero2 = new Image(R.drawable.playertwo);
		player1 = new Sprite(hero1);
		player2 = new Sprite(hero2);
	
		menuWidth = Globals.canvasWidth/Globals.MENU_PARTS;		//For øyeblikket deles denne på seks
		
		sx = menuWidth/hero1.getWidth();
		sy = (Globals.TILE_SIZE - 10 )/hero1.getHeight();
		
		
		greyBar = new Rect(0, 0, Globals.canvasWidth, Globals.TILE_SIZE);
		
		player1.setOffset(0, 0);
		player1.setScale(sx, sy);
		player1.setPosition(0, 0);
		
		player2.setOffset(0, 0);
		player2.setScale(sx, sy);
		player2.setPosition(Globals.canvasWidth - menuWidth, 0);
		
	}

	
	
	public void draw(Canvas canvas){
		canvas.drawRect(greyBar, paintLTGray);
		canvas.drawText("PAUSE", Globals.canvasWidth/2 - 25, Globals.TILE_SIZE/2 - 9, paintBlack);
		
		
		player1.draw(canvas);
		player2.draw(canvas);
		
		int healthPlayer1 = (int)(gm.getPlayer1().getHealth()/gm.getPlayer1().getMaxTotalHealth()*menuWidth);
		int healthPlayer2 = (int)(gm.getPlayer2().getHealth()/gm.getPlayer2().getMaxTotalHealth()*menuWidth);
	
		
		lifeBar1 = new Rect(0, Globals.TILE_SIZE - 10, healthPlayer1, Globals.TILE_SIZE);
		lifeBar2 = new Rect(Globals.canvasWidth - healthPlayer2, Globals.TILE_SIZE - 10, Globals.canvasWidth, Globals.TILE_SIZE);
		
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