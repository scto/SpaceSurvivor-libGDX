package com.eobgames.spacesurvivor.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eobgames.spacesurvivor.game.objects.Enemy;
import com.eobgames.spacesurvivor.screens.GameScreen;
import com.eobgames.spacesurvivor.util.Constants;

public class WorldRenderer {
	
	public Sprite background;
	
	private OrthographicCamera camera;
	private OrthographicCamera cameraGui;
	
	//sama batch og er notað í menuscreen
	public SpriteBatch batch;
	public WorldController worldController;
	
	private BitmapFont fontNeonLike = Assets.instance.fonts.neonLike;

	
	public WorldRenderer(WorldController worldController, SpriteBatch batch, GameScreen gameScreen){
		this.worldController = worldController;
		this.batch = batch;
		this.camera = gameScreen.camera;
		this.cameraGui = gameScreen.cameraGui;
		
		loadBackground();
	}
	
	
	private void loadBackground(){
		this.background = Assets.instance.background.background;
	}
	
	
	public void render(SpriteBatch batch){
		//cameraHelper.applyTo(camera);
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderGraphics();
		renderGui();
	}
	
	private void renderGui(){
		//String timeString = "TIME: ";
		batch.setProjectionMatrix(cameraGui.combined);
		batch.begin();
			fontNeonLike.draw(batch, "TIME: " + worldController.getTime(),
					Constants.CAMERA_GUI_WIDTH/2 - 65, Constants.CAMERA_GUI_HEIGHT*0.95f);
			
			if(Constants.DEBUG)
				renderDebugGui(batch);
		batch.end();
	}
	
	private void renderDebugGui(SpriteBatch batch){
		//nothing for now
		fontNeonLike.draw(batch, "fps: " + Gdx.graphics.getFramesPerSecond(), 10, 60);
	}

	
	//fyrir allar myndir
	private void renderGraphics(){
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
			//render background first, then boundary
			//then player, then enemies
			background.draw(batch);
			worldController.particleEffectManager.drawParticles(batch);
			worldController.boundary.render(batch);
			worldController.player.render(batch);
			renderEnemies(batch);
			
			if(Constants.DEBUG)
				renderDebugGraphics(batch);
		batch.end();
	}
	
	private void renderEnemies(SpriteBatch batch){
		for(Enemy enemy : worldController.enemies){
			enemy.render(batch);
		}
	}
	
	private void renderDebugGraphics(SpriteBatch batch){
		//nothing for now
		
	}
	
	public void resize(int width, int height){
		//used for landscape view
		/*camera.viewportWidth = (Constants.WORLD_HEIGHT / height) * width;
		camera.update();
		background.setSize(camera.viewportWidth, camera.viewportHeight);
		background.setPosition((Constants.WORLD_WIDTH-camera.viewportWidth)/2, 
				(Constants.WORLD_HEIGHT- camera.viewportHeight)/2);*/
		
		//used for portrait view
		camera.viewportHeight = (Constants.WORLD_WIDTH / width) * height;
		camera.update();
		
		background.setSize(camera.viewportWidth, camera.viewportHeight);
		background.setPosition((Constants.WORLD_WIDTH-camera.viewportWidth)/2, 
				(Constants.WORLD_HEIGHT- camera.viewportHeight)/2);
	}

}
