package com.eobgames.spacesurvivor;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eobgames.spacesurvivor.game.Assets;
import com.eobgames.spacesurvivor.screens.GameScreen;
import com.eobgames.spacesurvivor.screens.MenuScreen;


public class SpaceSurvivorMain extends Game {
	
	private GameScreen gameScreen;
	private MenuScreen menuScreen;
	
	public SpriteBatch batch;
	
	@Override
	public void create() {
		//Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.setLogLevel(Application.LOG_NONE);
		
		batch = new SpriteBatch();
		Assets.instance.init(new AssetManager());
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		//gameScreen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		setScreen(menuScreen);
	}
	
	@Override
	public void render(){
		//annars renderast ekki skjárinn í setScreen (frá wiki)
		super.render();
	}
	
	@Override
	public void dispose(){
		// TODO : dispose of everything from here
		//Dispose of menu screen
		//Dispose of game screen
		//Dispose of assets
		//gameScreen.dispose();
		//menuScreen.dispose();
		//batch.dispose();
		//this.dispose(); //þarf þessa?
	}
	
	public void setToGameScreen(){

		setScreen(gameScreen);
	}
	
	public void setToMenuScreen(){
		setScreen(menuScreen);
	}
	
	public void renderGameWorld(float dt){
	
		this.gameScreen.render(dt);
	}

}
