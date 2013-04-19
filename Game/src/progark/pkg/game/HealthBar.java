package progark.pkg.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class HealthBar {
	private Unit unit;
	private Rect healthRectangle;
	private Paint paintRed, paintGreen;
	private float posX, posY;
	
	public HealthBar(Unit u){
		unit = u;
		
		posX = u.getX();
		posY = u.getY() + Globals.calculatedTileSize - 5;

		
		healthRectangle = new Rect((int)posX, (int)posY, (int)posX + Globals.calculatedTileSize, (int)posY + 5);
		
		paintRed = new Paint();
		paintRed.setColor(Color.RED);
		paintGreen = new Paint();
		paintGreen.setColor(Color.GREEN);
	}
	
	public void update(float dt){
		posX = unit.getX();
		posY = unit.getY() + Globals.calculatedTileSize - 5;
		
		float dx = 1.0f*unit.getHealth()/unit.getMaxHealth()*Globals.calculatedTileSize;
		
		
		healthRectangle.set((int)posX, (int)posY, (int)posX + (int)dx, (int)posY + 5);
	}
	
	public void draw(Canvas canvas){
		if (unit.getHealth()*1.0/unit.getMaxHealth() < 0.25)
			canvas.drawRect(healthRectangle, paintRed);
		else
			canvas.drawRect(healthRectangle, paintGreen);
	}
}

