package progark.pkg.game;

import android.graphics.Canvas;
import sheep.game.State;

public class OverallController extends State {
	private int scrWidth, scrHeight;
	private boolean setup;
	private InGameGUI Gui;
	
	public OverallController() {
		setup = false;
	}
	
	public void draw(Canvas canvas) {
		scrHeight = canvas.getHeight();
		scrWidth = canvas.getWidth();
		setup();
		
		Gui.draw(canvas);
	}
	
	public void update(float dt) {
		
	}
	
	/* En metod til å opprette gui'et i henhold til skjermstørrelsen
	 * fordi man i sheep kun kan hente skjermstørrelsen i draw og 
	 * dermed må opprette ting etter dette. 
	 */
	private void setup(){
		if (!setup) {
			Gui = new InGameGUI(scrHeight, scrWidth);
			setup = true;
		}
	}
}
