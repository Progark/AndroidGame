package progark.pkg.game;

import sheep.game.Game;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {
	Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        game = new Game(this, null);
        game.pushState(new StartMenuView());
        setContentView(game);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    	GameMusic.stopBakcgroundMusic();
    	game = null;
    	setContentView(R.layout.activity_main);
    	
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	GameMusic.playBackgroundMusic();
    }
    
}
