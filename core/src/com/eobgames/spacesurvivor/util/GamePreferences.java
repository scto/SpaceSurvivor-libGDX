package com.eobgames.spacesurvivor.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class GamePreferences {
	public static final String TAG = GamePreferences.class.getName();
	
	//TODO: hafa tónlist í leiknum
	//public boolean sound;
	//public boolean music;
	//public float volSound;
	//public float volMusic;
	/**Geymir besta tíma í sekúndum*/
	
	public float bestTime;
	
	private Preferences prefs;
	
	public static final GamePreferences instance = new GamePreferences();
	
	private GamePreferences() {
		prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
	}
	
	public void loadPrefs(){
		//load all preferences here e.g.
		//sound = prefs.getBoolean("sound", true); //true default ef ekki finnst
		
	}
	
	public void loadSaveState(){
		bestTime = prefs.getFloat("bestTime", 0.000f);
	}
	
	public void savePrefs(){
		//save all preferences here, e.g.
		//prefs.putBoolean("sound", sound); //sound er bool
		
		
		prefs.flush();
	}
	
	public void saveSaveState(){
		prefs.putFloat("bestTime", this.bestTime);
		
		prefs.flush();
	}
	
	

}
