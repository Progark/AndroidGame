package progark.pkg.game;

import sheep.graphics.Image;

//Her må det taes hensyn til at en unit kan være sterkere enn en annen, må derfor gange opp helse og angrep
public class MeleeUnit extends Unit {

	public MeleeUnit(float x, float y) {
		super(new Image(R.drawable.s), new Image(R.drawable.sw1), new Image(R.drawable.sw2));
		setPosition(x, y);
		this.setName("M");
		this.setDefence(70);
		this.setHealth(60);
		this.setSkill(40);
		this.setStrength(60);
		this.setRange(1);
	}	
}
