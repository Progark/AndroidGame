package progark.pkg.game;

import sheep.graphics.Image;

public class MagicUnit extends Unit{

	public MagicUnit(float x, float y) {
		super(new Image(R.drawable.m), new Image(R.drawable.mw1), new Image(R.drawable.mw2), new Image(R.drawable.mw3), 
				new Image(R.drawable.ms1), new Image(R.drawable.ms2));
		
		setPosition(x, y);
		this.setName("Ma"); //Endret også navnet på melee til "Me"
		this.setDefence(55);
		this.setHealth(45);
		this.setSkill(40);
		this.setStrength(40);
		this.setRange(4);
		this.setMaxHealth(45);
		this.setMovement(1);
	}
	

}
