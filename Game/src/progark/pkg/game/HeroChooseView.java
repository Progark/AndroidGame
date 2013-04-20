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
	private boolean popGameBoolean;
	private Player hero1Player, hero2Player, hero3Player, hero4Player;
	private StartMenuView smw;
	private Rect nextRectButton, selectedHero;
	private Sprite hero1, hero2, hero3, hero4;
	private float sx, sy;
	private String selectedHeroString = "Player1";
	private int player;
	
	HeroChooseView(GameInitObject gio, int player, StartMenuView smw) {
		gameInitObject = gio;
		textPaint = new Paint();
		textPaint.setColor(Color.BLACK);
		textPaint.setTextSize(25);
		this.smw = smw;
		this.player = player;
		if (player == 1)
			popGameBoolean = false;
		else 
			popGameBoolean = true;
		
		nextRectButton = new Rect(Globals.canvasWidth - Globals.calculatedTileSize*3, Globals.canvasHeight - Globals.calculatedTileSize - 50,
					Globals.canvasWidth - Globals.calculatedTileSize, Globals.canvasHeight - 50);
		nextRectButtonPaint = new Paint();
		nextRectButtonPaint.setColor(Color.GRAY);
		
		sx = 1.0f*(Globals.canvasWidth/2 - 50)/Globals.PLAYER_ONE_IMAGE.getWidth();
		sy = 1.0f*(Globals.canvasHeight/4 - 25)/Globals.PLAYER_ONE_IMAGE.getHeight();
		
		hero1 = new Sprite(Globals.PLAYER_ONE_IMAGE);
		hero1.setScale(sx, sy);
		hero1.setOffset(0, 0);
		hero1.setPosition(25, Globals.canvasHeight/4);
		selectedHero = new Rect(20, Globals.canvasHeight/4 - 5, Globals.canvasWidth/2 - 20, Globals.canvasHeight/2 - 21	);
		selectedHeroPaint = new Paint();
		selectedHeroPaint.setColor(Color.RED);
		
		if (player == 1){
			hero1Player = new Player(1, "H1");
			hero2Player = new Player(1, "H2");
			hero3Player = new Player(1, "V");
			hero4Player = new Player(1, "P");
		} else {
			hero1Player = new Player(2, "H1");
			hero2Player = new Player(2, "H2");
			hero3Player = new Player(2, "V");
			hero4Player = new Player(2, "P");
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
		canvas.drawText("Selected hero: ", Globals.canvasWidth/2 - 80, 100, textPaint);
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
			
		
			
		else if (me.getY() > Globals.canvasHeight - 200){
			if (popGameBoolean) {
				getGame().pushState(new GameMechanics(smw, gameInitObject));
			} else {
				getGame().pushState(new HeroChooseView(gameInitObject, Globals.PLAYER_TWO, smw));
			}
		}
		return true;
			
	}
}
