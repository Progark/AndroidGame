package progark.pkg.game;

import sheep.graphics.Image;

public class RangedUnit extends Unit {

	public RangedUnit(float x, float y) {
		super(new Image(R.drawable.aw1frame), new Image(R.drawable.aw2frame), new Image(R.drawable.aw3frame));
		setPosition(x, y);
		
		this.setName("R");
		this.setDefence(30);
		this.setHealth(60);
		this.setSkill(40);
		this.setStrength(40);
		this.setRange(5);
	}
}
