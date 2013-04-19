package progark.pkg.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import sheep.game.State;
import sheep.gui.TextButton;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;

public class HeroChooseView extends State {
	private String playerNameLabel, ChooseYourHeroLabel; 
	private TextField textField;
	private GameInitObject gameInitObject;
	private Paint textPaint;
	private TextButton nextButton;
	private boolean popGameBoolean;
	private Player p;
	private StartMenuView smw;
	
	
	HeroChooseView(GameInitObject gio, int player, StartMenuView smw) {
		playerNameLabel = "Player Name:";
		ChooseYourHeroLabel = "Choose your HERO!";
		textField = new TextField(Globals.canvasWidth + 30, Globals.canvasWidth - 30, 30, 60);
		gameInitObject = gio;
		textPaint = new Paint();
		textPaint.setColor(Color.WHITE);
		textPaint.setTextSize(20);
		this.smw = smw;
		nextButton = new TextButton(Globals.canvasWidth / 2 - 200, Globals.canvasHeight - 30, "Next");
		nextButton.addWidgetListener(new NextEvent());
	
		
		switch (player) {
		case Globals.PLAYER_ONE:
			popGameBoolean = false;
			playerNameLabel = "Player 1";
			p = gameInitObject.getP1();
			p.setPlayerName("Player 1");
			p.setPlayerNo(Globals.PLAYER_ONE);
			gameInitObject.setP2(p);
			break;
		case Globals.PLAYER_TWO:
			popGameBoolean = true;
			playerNameLabel = "Player 2";
			p = gameInitObject.getP2();
			p.setPlayerName("Player 2");
			p.setPlayerNo(Globals.PLAYER_TWO);
			gameInitObject.setP2(p);
			break;
		default:
			break;
		}
		
		
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawColor(Color.RED);
		canvas.drawText(playerNameLabel, 5, 40, textPaint);
		textField.draw(canvas);
		canvas.drawText(ChooseYourHeroLabel, 5, 140, textPaint);
		nextButton.draw(canvas);
	}
	
	@Override
	public boolean onTouchDown(MotionEvent me){
		if (me.getY() > Globals.canvasHeight - 200){
			if (textField.getNameString() != "") {
				p.setPlayerName(textField.getNameString());
			}
			
			if (popGameBoolean) {
				getGame().pushState(new GameMechanics(smw));
			} else {
				getGame().pushState(new HeroChooseView(gameInitObject, Globals.PLAYER_TWO, smw));
			}
		}
		return true;
			
	}
	class NextEvent implements WidgetListener{
		@Override
		public void actionPerformed(WidgetAction action) {
			if (textField.getNameString() != "") {
				p.setPlayerName(textField.getNameString());
			}
			
			if (popGameBoolean) {
				getGame().pushState(new GameMechanics(smw));
			} else {
				getGame().pushState(new HeroChooseView(gameInitObject, Globals.PLAYER_TWO, smw));
			}
		}
		
	}
}
