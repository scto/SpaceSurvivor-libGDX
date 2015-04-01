package com.eobgames.spacesurvivor.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eobgames.spacesurvivor.SpaceSurvivorMain;
import com.eobgames.spacesurvivor.game.WorldController;
import com.eobgames.spacesurvivor.game.WorldRenderer;
import com.eobgames.spacesurvivor.util.Constants;
import com.eobgames.spacesurvivor.util.GameScreenInputAdapter;

public class GameScreen extends AbstractGameScreen {
	public static final String TAG = GameScreen.class.getName();
	
	private SpaceSurvivorMain game;
	private WorldController worldController;
	private WorldRenderer worldRenderer;
	SpriteBatch batch;
	
	public OrthographicCamera camera;
	public OrthographicCamera cameraGui;
	
	GameScreenInputAdapter inputAdapter;
	
	private boolean paused = true;
	private boolean gameOver = false;
	
	public GameScreen(SpaceSurvivorMain game){
		super(game);
		this.batch = game.batch;
		this.game = game;
		
		camera = new OrthographicCamera();
		cameraGui = new OrthographicCamera();
		initCamera();
		
		worldController = new WorldController(this);
		worldRenderer = new WorldRenderer(worldController, batch, this);
		
		inputAdapter = new GameScreenInputAdapter(worldController, this);
		
		//stillir bakgrunn á réttan stað ásamt viewport hæð
		worldRenderer.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	private void initCamera(){
		camera.setToOrtho(false, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
		camera.position.set(Constants.WORLD_WIDTH/2,Constants.WORLD_HEIGHT/2,0);

		camera.update();
		
		cameraGui.setToOrtho(false, Constants.CAMERA_GUI_WIDTH, Constants.CAMERA_GUI_HEIGHT);
		cameraGui.update();
		
	}
	
	
	public void setPaused(boolean bool){
		this.paused = bool;
	}
	
	
	public void togglePaused(){
		this.paused = !this.paused;
		
		if(this.paused){
			worldController.handlePaused();
		}
		else worldController.handleResume();
	}
	
	public boolean isPaused(){
		return this.paused;
	}
	
	public void singleStep(){
		worldController.update(Gdx.graphics.getDeltaTime());
	}
	
	//aðeins kallað á þegar er í game over state
	private void handleGameOver(SpriteBatch batch){
		//er hér bara ef ég vil implementa game over skjá
	}
	
	public boolean isGameOver(){
		Gdx.app.debug(TAG, "game over: " + this.gameOver);
		return this.gameOver;
	}
	
	public void setToMenuScreen(){
		worldController.reset();
		this.game.setToMenuScreen();
	}
	
	
	public void setGameOver(Boolean bool){
		this.gameOver = bool;
	}
	
	
	

	@Override
	public void render(float deltaTime) {
		worldController.particleEffectManager.updateParticles(deltaTime);
		if(!paused) worldController.update(deltaTime);
		
		worldRenderer.render(batch);
		
		if(gameOver) handleGameOver(batch);
	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
	}

	@Override
	public void show() {
		paused = false;
		gameOver = false;
		Gdx.input.setInputProcessor(inputAdapter);
		
		if(worldController.isNewGame()){
			worldController.resetStopwatch();
			worldController.player.setCanMove(true);
		}
	}

	@Override
	public void hide() {
		paused = true;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		worldController.dispose();
	}
	
	
	
	
	
}
