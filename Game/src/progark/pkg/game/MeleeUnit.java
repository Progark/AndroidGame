package progark.pkg.game;

import sheep.graphics.Image;

//Her må det taes hensyn til at en unit kan være sterkere enn en annen, må derfor gange opp helse og angrep
public class MeleeUnit extends Unit {

	public MeleeUnit(float x, float y) {
		super(new Image(R.drawable.s), new Image(R.drawable.sw1), new Image(R.drawable.sw2), new Image(R.drawable.sw3), 
				new Image(R.drawable.sh1), new Image(R.drawable.sh2));
		
		setPosition(x, y);
		this.setName("Me"); //Endret dette fordi jeg lagde en magic-klasse. For å skille på de trengtes to bokstaver
		this.setDefence(70);
		this.setHealth(60);
		this.setSkill(40);
		this.setStrength(45);
		this.setRange(1);
		this.setMaxHealth(60);
		this.setMovement(3);
	}	
}
