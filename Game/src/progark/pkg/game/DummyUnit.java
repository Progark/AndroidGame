package progark.pkg.game;

import java.util.ArrayList;

import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.graphics.SpriteView;

public class DummyUnit extends Sprite{

	float speed = 10;
	ArrayList<Image> im;
	Image currentI;
	int frame = 0;
	float time = 0;
	
	public DummyUnit(Image image1, Image image2, Image image3){
		super(image1);
		im = new ArrayList<Image>();
		im.add(image1);
		im.add(image2);
		im.add(image3);
		setPosition(100f, 100f);
	}
	
	/*
	 * Hvordan animere? Se her for eksempel, da spesifikt setView((SpreiteView))
	 * @see sheep.game.Sprite#update(float)
	 */
	@Override
	public void update(float dt){
		super.update(dt);
		time += dt;
		if (time >= 0.2){
			frame ++;
			if (frame > 2)
				frame = 0;
			setView((SpriteView)im.get(frame));
		}
		setPosition(getX() + speed, getY()+ speed);
	}
}
