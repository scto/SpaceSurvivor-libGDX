package com.eobgames.spacesurvivor.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;
import com.eobgames.spacesurvivor.SpaceSurvivorMain;
import com.eobgames.spacesurvivor.game.Assets;
import com.eobgames.spacesurvivor.util.Constants;
import com.eobgames.spacesurvivor.util.GamePreferences;
import com.eobgames.spacesurvivor.util.MenuScreenInputAdapter;

public class MenuScreen extends AbstractGameScreen {
	private static final String TAG = MenuScreen.class.getName();
	private MenuScreenInputAdapter inputAdapter;
	
	private SpaceSurvivorMain game;
	OrthographicCamera camera;
	OrthographicCamera cameraGui;
	SpriteBatch batch;
	
	Vector3 touchPos;
	Circle playerCircle;
	
	//Fonts
	//neonlights gæti verið flott fyrir best time font
	BitmapFont neonLike = Assets.instance.fonts.neonLike;
	
	private float bestTime = 0.0f;
	
	private boolean debugOn = false;

	public MenuScreen(SpaceSurvivorMain game) {
		super(game);
		this.game = game;
		batch = game.batch;
		
		inputAdapter = new MenuScreenInputAdapter();
		
		camera = new OrthographicCamera(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
		cameraGui = new OrthographicCamera();

		initCamera();
		initSprites();
		
		getBestTime();
		
		touchPos = new Vector3();
		playerCircle = new Circle(Constants.WORLD_WIDTH/2, Constants.WORLD_HEIGHT/2, Constants.PLAYER_RADIUS*1.05f); 
	}
	
	private void getBestTime(){
		GamePreferences prefs = GamePreferences.instance;
		prefs.loadSaveState();
		this.bestTime = prefs.bestTime;
	}
	
	//setur upphafsstillingar fyrir camera
	private void initCamera(){
		camera.setToOrtho(false, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
		camera.position.set(Constants.WORLD_WIDTH/2,Constants.WORLD_HEIGHT/2,0);

		camera.update();
		
		cameraGui.setToOrtho(false, Constants.CAMERA_GUI_WIDTH, Constants.CAMERA_GUI_HEIGHT);
		cameraGui.update();
		
	}
	
	private void initSprites(){
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(debugOn) handleDebug(deltaTime);
		
		renderGraphics(deltaTime);
		renderText();
		
		handleInput();
	}
	
	private void renderText(){
		
		batch.setProjectionMatrix(cameraGui.combined);
		batch.begin();
			float x = Constants.CAMERA_GUI_WIDTH / 2; 
			float y = Constants.CAMERA_GUI_HEIGHT / 2;
			y *=1.25;
			String titleText = "SPACE SURVIVOR \n DRAG TO PLAY";
			String bestTimeText = "BEST TIME " +  this.bestTime;
			
			//neonLike.setColor(Color.RED);
			neonLike.drawMultiLine(batch, titleText, x, y, 1, BitmapFont.HAlignment.CENTER);
			
			y/=1.4;
			
			//neonTest.setColor(Color.RED);
			neonLike.drawMultiLine(batch, bestTimeText, x, y, 1, BitmapFont.HAlignment.CENTER);
		batch.end();
	}
	
	
	//fyrir öll sprites
	private void renderGraphics(float dt){
		game.renderGameWorld(dt);

	}

	@Override
	public void resize(int width, int height) {
		camera.viewportHeight = (Constants.WORLD_WIDTH / width) * height;
		camera.update();
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(inputAdapter);
		getBestTime();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
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
		this.dispose();
	}
	
	
	private void handleInput(){
		if(Gdx.input.isTouched()){
			touchPos.x = Gdx.input.getX();
			touchPos.y = Gdx.input.getY();
			touchPos.set(camera.unproject(touchPos));
			if(playerCircle.contains(touchPos.x, touchPos.y))
				game.setToGameScreen();
		}
	}
	
	private void handleDebug(float deltaTime){
		//Gdx.app.debug(TAG, "camera w / h : " + camera.viewportWidth + ", " + camera.viewportHeight);
	}
	

}
