package progark.pkg.game;

import sheep.graphics.Image;

//Her m� det taes hensyn til at en unit kan v�re sterkere enn en annen, m� derfor gange opp helse og angrep
public class MeleeUnit extends Unit {

	public MeleeUnit(float x, float y) {
		super(new Image(R.drawable.sw1frame), new Image(R.drawable.sw2frame), new Image(R.drawable.sw3frame));
		setPosition(x, y);
		this.setName("M");
		this.setDefence(70);
		this.setHealth(60);
		this.setSkill(40);
		this.setStrength(60);
		this.setRange(1);
	}	
}
