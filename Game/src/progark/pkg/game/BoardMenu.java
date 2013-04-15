<<<<<<< HEAD
package progark.pkg.game;

import sheep.graphics.Image;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

//Den som inneholder totalt liv
public class BoardMenu {
	private int menuHeight, menuWidth; //TODO: Sette disse verdien når man har en meny
	private Rect pauseButton;
	private Image hero1, hero2;
	private Rect lifeBar1, lifeBar2;
	private Rect greyBar1, greyBar2;
	private int canvasHeight, canvasWidth;
	private int i = 0;
	private Paint paint1, paint2;
	private int life1, life2;
	
	public BoardMenu() {
		this.paint1 = new Paint(Color.LTGRAY);
		this.paint2 = new Paint(Color.BLACK);
	}

	public int getMenuHeight() {
		return menuHeight;
	}

	public void setMenuHeight(int menuHeight) {
		this.menuHeight = menuHeight;
	}

	public int getMenuWidth() {
		return menuWidth;
	}

	public void setMenuWidth(int menuWidth) {
		this.menuWidth = menuWidth;
	}
	
	public void draw(Canvas canvas){
		if(i == 0){
			canvasHeight = canvas.getHeight();
			canvasWidth = canvas.getWidth();
			setMenuHeight(25);
			setMenuWidth(canvas.getWidth()/6);
			menuHeight = getMenuHeight();
			menuWidth = getMenuWidth();
			i++;
		}
		pauseButton = new Rect(canvasWidth/2-menuWidth*2, 0, canvasWidth/2+menuWidth*2, menuHeight);
		canvas.drawRect(pauseButton, paint1);
		canvas.drawText("PAUSE", menuHeight/2-5, canvasWidth/2-25, paint2);
		
		greyBar1 = new Rect(0, 0, menuWidth, menuHeight);
		greyBar2 = new Rect(canvasWidth-menuWidth, 0, canvasWidth, menuHeight);
		canvas.drawRect(greyBar1, paint1);
		canvas.drawRect(greyBar2, paint1);
		
		lifeBar1 = new Rect(0, menuHeight-5, menuWidth, menuHeight);
		if(life1 >= 25){
			
		}
	}
	
	public void onTouchDown(MotionEvent me){
		if(pauseButton.contains((int)me.getX(), (int)me.getY())){
			//getGame().pushState(new GameMechanics());
		}
	}
	
}
=======
package progark.pkg.game;

import sheep.graphics.Image;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

//Den som inneholder totalt liv
public class BoardMenu {
	private int menuHeight, menuWidth; //TODO: Sette disse verdien når man har en meny
	private Rect pauseButton;
	private Image hero1, hero2;
	private Rect lifeBar1, lifeBar2;
	private Rect greyBar1, greyBar2;
	private int canvasHeight, canvasWidth;
	private int i = 0;
	private Paint paint1, paint2, paint3, paint4;
	private int life1, life2;
	private GameMechanics gm;
	
	public BoardMenu(GameMechanics gm) {
		paint1 = new Paint();
		paint1.setColor(Color.LTGRAY);
		paint2 = new Paint();
		paint2.setColor(Color.BLACK);
		paint2.setTextSize(18);
		paint3 = new Paint();
		paint3.setColor(Color.RED);
		paint4 = new Paint();
		paint4.setColor(Color.GREEN);
		this.gm = gm;
	}

	public int getMenuHeight() {
		return menuHeight;
	}

	public void setMenuHeight(int menuHeight) {
		this.menuHeight = menuHeight;
	}

	public int getMenuWidth() {
		return menuWidth;
	}

	public void setMenuWidth(int menuWidth) {
		this.menuWidth = menuWidth;
	}
	
	public void draw(Canvas canvas){
		if(i == 0){
			canvasHeight = canvas.getHeight();
			canvasWidth = canvas.getWidth();
			setMenuHeight(25);
			setMenuWidth(canvas.getWidth()/6);
			menuHeight = getMenuHeight();
			menuWidth = getMenuWidth();
			i++;
		}
		pauseButton = new Rect(canvasWidth/2-menuWidth*2, 0, canvasWidth/2+menuWidth*2, menuHeight);
		canvas.drawRect(pauseButton, paint1);
		canvas.drawText("PAUSE", menuHeight/2-5, canvasWidth/2-25, paint2);
		
		greyBar1 = new Rect(0, 0, menuWidth, menuHeight);
		greyBar2 = new Rect(canvasWidth-menuWidth, 0, canvasWidth, menuHeight);
		canvas.drawRect(greyBar1, paint1);
		canvas.drawRect(greyBar2, paint1);
		
		lifeBar1 = new Rect(0, menuHeight-5, (menuWidth/100)*life1, menuHeight);
		if(life1 <= 25){
			canvas.drawRect(lifeBar1, paint3);
		}
		else{
			canvas.drawRect(lifeBar1, paint4);
		}
		
		lifeBar2 = new Rect(canvasWidth-menuWidth, menuHeight-5, canvasWidth-(menuWidth/100)*life2, menuHeight);
		if(life1 <= 25){
			canvas.drawRect(lifeBar2, paint3);
		}
		else{
			canvas.drawRect(lifeBar2, paint4);
		}
		
		
	}
	
	public void onTouchDown(MotionEvent me){
		if(pauseButton.contains((int)me.getX(), (int)me.getY())){
			gm.getGame().pushState(new PauseMenu());
		}
	}
	
}
>>>>>>> lagt til boardMenu
