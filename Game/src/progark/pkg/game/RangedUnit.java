package progark.pkg.game;

import sheep.graphics.Image;

public class RangedUnit extends Unit {

	public RangedUnit() {
		super(new Image(R.drawable.aw1), new Image(R.drawable.aw2), new Image(R.drawable.aw3));	//Her må bildefilnavnene endres
		this.setName("R");
		this.setDefence(30);
		this.setHealth(60);
		this.setSkill(40);
		this.setStrength(40);
		this.setRange(5);
	}
}
