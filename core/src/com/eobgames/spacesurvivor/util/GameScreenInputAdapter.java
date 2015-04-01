package com.eobgames.spacesurvivor.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.eobgames.spacesurvivor.game.WorldController;
import com.eobgames.spacesurvivor.screens.GameScreen;

public class GameScreenInputAdapter extends InputAdapter {
	public static final String TAG = GameScreenInputAdapter.class.getName();
	
	private WorldController worldController;
	private GameScreen gameScreen;
	Vector3 worldTouchPos;
	Vector3 screenTouchPos;
	
	public GameScreenInputAdapter(WorldController worldController, GameScreen gameScreen){
		this.worldController = worldController;
		this.gameScreen = gameScreen;
		
		worldTouchPos = new Vector3();
		screenTouchPos = new Vector3();
	}

	@Override
	public boolean keyUp(int keycode){
		if(Constants.DEBUG){
			handleKeyUpDebugInput(keycode);
		}
		
		return false;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer){
		return false;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button){
		screenTouchPos.x = screenX;
		screenTouchPos.y = screenY;
		worldTouchPos = worldController.gameScreen.camera.unproject(screenTouchPos);
		worldController.player.touchDown(worldTouchPos);
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button){
		worldController.player.touchUp();
		
		if(gameScreen.isGameOver()){
			gameScreen.setToMenuScreen();
		}
		return false;
	}
	
	private void handleKeyUpDebugInput(int keycode){
		if(keycode == Keys.C){
			Gdx.app.debug(TAG, "pressed C from gameScreen");
		}
		
		if(keycode == Keys.K)
			logPlayerPosition();
		
		if(keycode == Keys.P){
			gameScreen.togglePaused();
		}
		
		if(keycode == Keys.O && gameScreen.isPaused()){
			gameScreen.singleStep();
		}
	}
	
	private void logPlayerPosition(){
		Gdx.app.debug(TAG, "player position: " + worldController.player.position 
				+ ", player circle position: " + worldController.player.getCirclePositionString());
	}
}
