package progark.pkg.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;
import sheep.gui.Widget;
import sheep.input.Keyboard;

public class TextField extends Widget {
	private int leftRectSide, rightRectSide, topRectSide, bottomRectSide;
	private Rect bgRect;
	private Rect frRect;
	private Paint bgPaint;
	private Paint frPaint;
	private Paint textPaint;
	private String nameString;
	
	/**
	 * Constructor: Parameters assigns the position of the TextField 
	 * 
	 * @param leftRectSide
	 * @param rightRectSide
	 * @param topRectSide
	 * @param bottomRectSide
	 */
	public TextField(int leftRectSide, int rightRectSide, int topRectSide, int bottomRectSide) {
		this.leftRectSide = leftRectSide;
		this.rightRectSide = rightRectSide;
		this.topRectSide = topRectSide;
		this.bottomRectSide = bottomRectSide;

		frRect = new Rect(leftRectSide+1, topRectSide+1, rightRectSide+1, bottomRectSide+1);
		bgRect = new Rect(leftRectSide, topRectSide, rightRectSide, bottomRectSide);
		
		bgPaint = new Paint();
		bgPaint.setColor(Color.WHITE);
		frPaint = new Paint();
		frPaint.setColor(Color.GRAY);
		textPaint = new Paint();
		textPaint.setColor(Color.BLACK);
		textPaint.setTextSize(20);
		
		nameString = "";
		
	}
	
	public String getNameString() {
		return nameString;
	}
	
	@Override
	public void draw(Canvas canvas) {
		
		canvas.drawRect(frRect, frPaint);
		canvas.drawRect(bgRect, bgPaint);
		canvas.drawText(nameString, leftRectSide + 1, topRectSide + 1, textPaint);	
	}
	
	@Override
	public boolean onTouchDown(MotionEvent event) {
		Keyboard.getInstance(); 
		return true;
	}
	
	@Override
	public boolean onKeyDown(KeyEvent event) {
		nameString += event.getCharacters();
		return true;
	}
	
}
