package com.eobgames.spacesurvivor.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

public class MenuScreenInputAdapter extends InputAdapter {
	public static final String TAG = MenuScreenInputAdapter.class.getName();

	public boolean keyUp(int keycode){
		if(Constants.DEBUG){
			handleKeyUpDebugInput(keycode);
		}
		
		return false;
	}
	
	private void handleKeyUpDebugInput(int keycode){
		if(keycode == Keys.C)
			Gdx.app.debug(TAG, "pressed C from menuScreen");
	}
	
}
