package progark.pkg.game;

import sheep.graphics.Image;

public class MeleeUnit extends Unit {

	public MeleeUnit() {
		super(new Image(R.drawable.s), new Image(R.drawable.sw1), new Image(R.drawable.sw2));
		this.setName("M");
		this.setDefence(70);
		this.setHealth(60);
		this.setSkill(40);
		this.setStrength(60);
		this.setRange(1);
	}
	
}
