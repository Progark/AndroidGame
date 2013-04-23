package progark.pkg.game;

import sheep.audio.Sound;

public class GameMusic {
	public static Sound backGroundMusic;
	public GameMusic(){
		backGroundMusic = new Sound(R.raw.bongos);
	}
	
	public static void playBackgroundMusic(){
		backGroundMusic.play(-1);
	}
	
	public static void stopBakcgroundMusic(){
		backGroundMusic.stop();
	}
}
