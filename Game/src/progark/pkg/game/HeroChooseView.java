package progark.pkg.game;

import sheep.game.Sprite;
import sheep.game.State;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class HeroChooseView extends State { 
	private GameInitObject gameInitObject;
	private Paint textPaint, nextRectButtonPaint, selectedHeroPaint;
	private Player hero1Player, hero2Player, hero3Player, hero4Player;
	private StartMenuView smv;
	private Rect nextRectButton, selectedHero;
	private Sprite hero1, hero2, hero3, hero4;
	private float sx, sy;
	private String selectedHeroString = "";
	private int player;
	private GameMusic gameMusic;
	
	public HeroChooseView(GameInitObject gio, int player, StartMenuView smv, GameMusic gameMusic) {
		gameInitObject = gio;
		textPaint = new Paint();
		textPaint.setColor(Color.BLACK);
		textPaint.setTextSize(25);
		this.smv = smv;
		this.player = player;
		this.gameMusic = gameMusic;
		
		nextRectButton = new Rect(Globals.canvasWidth - Globals.calculatedTileSize*3, Globals.canvasHeight - Globals.calculatedTileSize - 50,Globals.canvasWidth - Globals.calculatedTileSize, Globals.canvasHeight - 50);
		nextRectButtonPaint = new Paint();
		nextRectButtonPaint.setColor(Color.GRAY);
		
		sx = 1.0f*(Globals.canvasWidth/2 - 50)/Globals.PLAYER_ONE_IMAGE.getWidth();
		sy = 1.0f*(Globals.canvasHeight/4 - 25)/Globals.PLAYER_ONE_IMAGE.getHeight();
		
		hero1 = new Sprite(Globals.PLAYER_ONE_IMAGE);
		hero1.setScale(sx, sy);
		hero1.setOffset(0, 0);
		hero1.setPosition(25, Globals.canvasHeight/4);
		selectedHero = new Rect(-150, -150, -140, -140);
		selectedHeroPaint = new Paint();
		selectedHeroPaint.setColor(Color.RED);
		
		if (player == 1){
			hero1Player = new Player(1,"Hero1");
			hero2Player = new Player(1, "Hero2");
			hero3Player = new Player(1, "Viking");
			hero4Player = new Player(1, "Princess");
		} else {
			hero1Player = new Player(2, "Hero1");
			hero2Player = new Player(2, "Hero2");
			hero3Player = new Player(2, "Viking");
			hero4Player = new Player(2, "Princess");
		}
		
		hero2 = new Sprite(Globals.PLAYER_TWO_IMAGE);
		hero2.setScale(sx, sy);
		hero2.setOffset(0, 0);
		hero2.setPosition(Globals.canvasWidth/2 + 25, Globals.canvasHeight/4);
		
		hero3 = new Sprite(Globals.VIKING);
		hero3.setScale(sx, sy);
		hero3.setOffset(0, 0);
		hero3.setPosition(25, Globals.canvasHeight/2 + 10);
		
		hero4 = new Sprite(Globals.PRINCESS);
		hero4.setScale(sx, sy);
		hero4.setOffset(0, 0);
		hero4.setPosition(Globals.canvasWidth/2 + 25, Globals.canvasHeight/2 + 10);	
	}
	
	@Override
	public void update(float dt){
		hero1.update(dt);
		hero2.update(dt);
		hero3.update(dt);
		hero4.update(dt);
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		
		canvas.drawRect(selectedHero, selectedHeroPaint);
		
		if (player == 1)
			canvas.drawText("Player 1, select commander: ", Globals.canvasWidth/2 - 100, 100, textPaint);
		else
			canvas.drawText("Player 2, select commander: ", Globals.canvasWidth/2 - 100, 100, textPaint);
		
		canvas.drawText(selectedHeroString, Globals.canvasWidth/2 - 40, 200, textPaint);
		
		hero1.draw(canvas);
		hero2.draw(canvas);
		hero3.draw(canvas);
		hero4.draw(canvas);
		
		canvas.drawRect(nextRectButton, nextRectButtonPaint);
		canvas.drawText("Next", Globals.canvasWidth - Globals.calculatedTileSize*2 - 28, Globals.canvasHeight - Globals.calculatedTileSize - 5, textPaint);
	}
	
	@Override
	public boolean onTouchDown(MotionEvent me){
		if (me.getX() > 25 && me.getX() < Globals.canvasWidth/2 - 25 && me.getY() > Globals.canvasHeight/4 && me.getY() < Globals.canvasHeight/2 - 25){
			selectedHero = new Rect(20, Globals.canvasHeight/4 - 5, Globals.canvasWidth/2 - 20, Globals.canvasHeight/2 - 21	);
			selectedHeroString = "Hero 1";
			if (player == 1)
				gameInitObject.setP1(hero1Player);
			else
				gameInitObject.setP2(hero1Player);
		} 
		
		else if (me.getX() > Globals.canvasWidth/2 + 25 && me.getX() < Globals.canvasWidth - 25 && me.getY() > Globals.canvasHeight/4 && me.getY() < Globals.canvasHeight/2 - 25){
			selectedHero = new Rect(Globals.canvasWidth/2 + 20, Globals.canvasHeight/4 - 5, Globals.canvasWidth - 20, Globals.canvasHeight/2 - 21	);
			selectedHeroString = "Hero 2";
			if (player == 1)
				gameInitObject.setP1(hero2Player);
			else
				gameInitObject.setP2(hero2Player);
		}
	
		else if (me.getX() > 25 && me.getX() < Globals.canvasWidth/2 - 25 && me.getY() > Globals.canvasHeight/2 + 10 && me.getY() < 3*Globals.canvasHeight/4 - 15){
			selectedHero = new Rect(20, Globals.canvasHeight/2 + 5, Globals.canvasWidth/2 - 20, 3*Globals.canvasHeight/4 - 11);
			selectedHeroString = "Viking";
			if (player == 1)
				gameInitObject.setP1(hero3Player);
			else
				gameInitObject.setP2(hero3Player);
		}
			
		else if (me.getX() > Globals.canvasWidth/2 + 25 && me.getX() < Globals.canvasWidth - 25 && me.getY() > Globals.canvasHeight/2 + 10 && me.getY() < 3*Globals.canvasHeight/4 - 15){
			selectedHero = new Rect(Globals.canvasWidth/2 + 20, Globals.canvasHeight/2 + 5, Globals.canvasWidth - 20, 3*Globals.canvasHeight/4 - 11	);
			selectedHeroString = "Princess";
			if (player == 1)
				gameInitObject.setP1(hero4Player);
			else
				gameInitObject.setP2(hero4Player);
		}
		
		else if (me.getX() > Globals.canvasWidth - Globals.calculatedTileSize*3 && me.getY() > Globals.canvasHeight - Globals.calculatedTileSize - 50 && me.getX() < Globals.canvasWidth - Globals.calculatedTileSize && me.getY() < Globals.canvasHeight - 50){
			if (player == 1)
					getGame().pushState(new HeroChooseView(gameInitObject, Globals.PLAYER_TWO, smv, gameMusic));
			else
					getGame().pushState(new GameMechanics(smv, gameInitObject, gameMusic));
		}
		return true;
			
	}
}
