package progark.pkg.game;

import sheep.graphics.Image;

public class RangedUnit extends Unit {

	public RangedUnit(float x, float y) {
		super(new Image(R.drawable.a), new Image(R.drawable.aw1), new Image(R.drawable.aw2), new Image(R.drawable.aw3),
				new Image(R.drawable.as1), new Image(R.drawable.as2));
		
		setPosition(x, y);
		this.setName("R");
		this.setDefence(30);
		this.setHealth(50);
		this.setSkill(40);
		this.setStrength(35);
		this.setRange(5);
		this.setMaxHealth(50);
		this.setMovement(2);
	}
}
