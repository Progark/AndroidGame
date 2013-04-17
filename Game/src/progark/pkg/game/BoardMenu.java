package progark.pkg.game;

import sheep.game.Sprite;
import sheep.graphics.Image;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

//Den som inneholder totalt liv
//Må legge til muligheten for å ha to bilder på skjermen
public class BoardMenu {

	private int menuHeight, menuWidth; //TODO: Sette disse verdien nÃ‚r man har en meny
	private Image hero1, hero2;
	private Rect pauseButton;
	private Rect lifeBar1, lifeBar2;
	private Rect greyBar;
	private int canvasHeight, canvasWidth;
	private int i = 0;
	private Paint paint1, paint2, paint3, paint4, paint5;
	private GameMechanics gm;
	private Sprite player1, player2;
	private float sx, sy;
	
	public BoardMenu(GameMechanics gm){
		paint1 = new Paint();
		paint1.setColor(Color.LTGRAY);
		paint2 = new Paint();
		paint2.setColor(Color.WHITE);
		paint2.setTextSize(18);
		paint3 = new Paint();
		paint3.setColor(Color.RED);
		paint4 = new Paint();
		paint4.setColor(Color.CYAN);
		paint5 = new Paint();
		paint5.setColor(Color.GRAY);
		this.gm = gm;
		
		hero1 = new Image(R.drawable.playerone);
		hero2 = new Image(R.drawable.playertwo);
		player1 = new Sprite(hero1);
		player2 = new Sprite(hero2);
	
		
		
		
	}

	public int getMenuHeight() {
		return menuHeight;
	}

	public int getMenuWidth() {
		return menuWidth;
	}
	
	public void update(float dt){
		player1.update(dt);
		player2.update(dt);
	}
	
	public void draw(Canvas canvas){
		if(i == 0){
			canvasHeight = canvas.getHeight();
			canvasWidth = canvas.getWidth();
			menuWidth = canvasWidth/Globals.MENU_PARTS;		//For øyeblikket deles denne på seks
			menuHeight = Globals.TILE_SIZE;		//Ved å sette høyden på menyen til å være like TILE_SIZE, blir det lettere å plassere ting under menyen
			sx = menuWidth/hero1.getWidth();
			sy = Globals.TILE_SIZE/hero1.getHeight();
			i++;
		}
		
		//Tegnes bak alt, strekker seg fra venstre helt til høyre
		greyBar = new Rect(0, 0, canvasWidth, menuHeight);
		canvas.drawRect(greyBar, paint5);	//Grå farge, ikke lenger sort

		
		player1.setScale(sx, sy);
		player1.setOffset(0, 0);
		player1.setPosition(0, 0);
		player1.draw(canvas);
		
		player2.setScale(sx, sy);
		player2.setOffset(0, 0);
		player2.setPosition(canvasWidth - menuWidth, 0);
		player2.draw(canvas);
	
		//Lager pause-"knappen"
		pauseButton = new Rect(canvasWidth/2 - menuWidth*2, 0, canvasWidth/2 + menuWidth*2, menuHeight);
		canvas.drawRect(pauseButton, paint1);
		canvas.drawText("PAUSE", canvasWidth/2 - 25, menuHeight/2 - 10, paint2);
		if ((gm.getTurn() % 2) != 0)
			canvas.drawText("Player 1`s turn!", canvasWidth/2 - 55, menuHeight/2 + 10, paint2);
		else 
			canvas.drawText("Player 2`s turn!", canvasWidth/2 - 55, menuHeight/2 + 10, paint2);
		
		
		float lifeLeftPlayer1 = gm.getPlayer1().getHealth()/gm.getPlayer1().getMaxTotalHealth()*menuWidth;
		float lifeLeftPlayer2 = gm.getPlayer2().getHealth()/gm.getPlayer2().getMaxTotalHealth()*menuWidth;
		
		lifeBar1 = new Rect(0, menuHeight - 10, (int)lifeLeftPlayer1, menuHeight);
		if (lifeLeftPlayer1 <= menuWidth*0.25)
			canvas.drawRect(lifeBar1, paint3);
		else
			canvas.drawRect(lifeBar1, paint4);


		lifeBar2 = new Rect(canvasWidth - (int)lifeLeftPlayer2, menuHeight - 10, canvasWidth, menuHeight);
		if (lifeLeftPlayer2 <= menuWidth*0.25)
			canvas.drawRect(lifeBar2, paint3);
		else
			canvas.drawRect(lifeBar2, paint3);
		
//////		La inn en int for Ã¥ regne ut riktig forhold mellom life og menuWidth (menuWidth er 1/6 av canvasWidth)
////		int lifebarometer1 = (menuWidth * (life1/100));
//////		Det er det tredje parameteret som ikke stemmer, og jeg har prÃ¸vd med ulike fremgangsmÃ¥ter. 
//////		Jeg tenkte at nÃ¥r lifebarometer er et forholdstall til menuWidth burde det vÃ¦re riktig.
//////		Tydeligvis ikke... Hjelp!
////		
////		lifeBar1 = new Rect(0, menuHeight-10, lifebarometer1, menuHeight);
////		if(life1 <= 25){
////			canvas.drawRect(lifeBar1, paint3);
////		}
////		else{
////			canvas.drawRect(lifeBar1, paint4);
////		}
//
////		Samme problem her
//		int lifebarometer2 = (menuWidth * (life2/100));
//		System.out.println(lifebarometer2);
////		3.parameter som ikke stemmer her ogsÃ¥...
//		lifeBar2 = new Rect(canvasWidth-menuWidth, menuHeight-10, (canvasWidth-menuWidth)+lifebarometer2, menuHeight);
//		if(life1 <= 25){
//			canvas.drawRect(lifeBar2, paint3);
//		}
//		else{
//			canvas.drawRect(lifeBar2, paint4);
//		}
	}
//	public void onTouchDown(MotionEvent me){
//	if(pauseButton.contains((int)me.getX(), (int)me.getY())){
//		gm.getGame().pushState(new PauseMenu());
//	}
//}
}
