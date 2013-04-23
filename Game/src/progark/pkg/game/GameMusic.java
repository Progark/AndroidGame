package progark.pkg.game;

import sheep.audio.Sound;

public class GameMusic {
	public static Sound backGroundMusic;
	private static GameMusic instance = null;
	
	private GameMusic(){
		backGroundMusic = new Sound(R.raw.bongos);
	}
	
	public static GameMusic getInstance(){
		if(instance == null){
			synchronized(GameMusic.class){
				if(instance == null)
					instance = new GameMusic();
			}
		}
		return instance;
	}
	
	public static void playBackgroundMusic(){
		backGroundMusic.play(-1);
	}
	
	public static void stopBakcgroundMusic(){
		backGroundMusic.stop();
	}
}
