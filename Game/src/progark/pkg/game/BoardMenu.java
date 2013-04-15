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
			getGame().pushState(new GameMechanics());
		}
	}
	
}
