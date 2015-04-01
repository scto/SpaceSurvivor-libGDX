package com.eobgames.spacesurvivor.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.eobgames.spacesurvivor.effects.ParticleEffectManager;
import com.eobgames.spacesurvivor.game.objects.Boundary;
import com.eobgames.spacesurvivor.game.objects.Enemy;
import com.eobgames.spacesurvivor.game.objects.Player;
import com.eobgames.spacesurvivor.screens.GameScreen;
import com.eobgames.spacesurvivor.util.CollissionChecker;
import com.eobgames.spacesurvivor.util.Constants;
import com.eobgames.spacesurvivor.util.GamePreferences;
import com.eobgames.spacesurvivor.util.Stopwatch;

public class WorldController {
	private final String TAG = WorldController.class.getName();
	
	public Player player;
	public Boundary boundary;
	public Array<Enemy> enemies;
	
	//particle effects
	public ParticleEffectManager particleEffectManager;
	public int peKeyEnemyBoundaryCollision;
	public int peKeyEnemyTrail;
	public int peKeyPlayerTrail;
	
	public GameScreen gameScreen;
	
	/** worldTouchPos contains screen coords projected to virtual world coords */
	Vector3 worldTouchPos;
	Vector3 screenTouchPos;
	
	//usedfor calculations, to avoid creating new vectors every frame
	Vector2 tempVector1;
	Vector2 tempVector2;
	Vector2 tempVector3;
	
	Stopwatch stopwatch;
	private float elapsedTime;
	
	public WorldController(GameScreen gameScreen){
		this.gameScreen = gameScreen;

		worldTouchPos = new Vector3();
		screenTouchPos = new Vector3();

		this.player = new Player(worldTouchPos);
		this.boundary = new Boundary();
		
		//tempVectors are used each frame for calculations,
		//instead of creating new vectors each frame (avoids GC)
		tempVector1 = new Vector2();
		tempVector2 = new Vector2();
		tempVector3 = new Vector2();
		
		//particleEffects
		particleEffectManager = new ParticleEffectManager();
		loadParticleEffects();
		
		enemies = new Array<Enemy>();
		createEnemies();
		
		this.stopwatch = new Stopwatch();
		elapsedTime = 0.000f;
	}
	
	
	private void createEnemies(){
		//Magic numbers 
		float leftSide = 0.32f;
		float rightSide = 0.68f;
		float bottom = 0.25f;
		float top = 0.75f;
		enemies.add(new Enemy(Constants.WORLD_WIDTH*leftSide, Constants.WORLD_HEIGHT*bottom));
		enemies.add(new Enemy(Constants.WORLD_WIDTH*rightSide, Constants.WORLD_HEIGHT*bottom));
		enemies.add(new Enemy(Constants.WORLD_WIDTH*leftSide, Constants.WORLD_HEIGHT*top));
		enemies.add(new Enemy(Constants.WORLD_WIDTH*rightSide, Constants.WORLD_HEIGHT*top));
	}
	
	
	private void loadParticleEffects(){
		
		//Load Enemy Boundary collision particle effect
		ParticleEffect effect = particleEffectManager.loadParticleEffect(Gdx.files.internal("particles/enemy_boundary.p"));
		effect.scaleEffect(0.1f);
		peKeyEnemyBoundaryCollision = particleEffectManager.addParticleEffect(effect, 12);
		effect = null;
		
		//load EnemyTrail particle effect
		effect = particleEffectManager.loadParticleEffect(Gdx.files.internal("particles/trailTest"));
		effect.scaleEffect(0.3f);
		peKeyEnemyTrail = particleEffectManager.addParticleEffect(effect, 12);
		effect = null;
		
		//load player trail
		//effect = particleEffectManager.loadParticleEffect(Gdx.files.internal("particles/playerTrail"));
		//effect.scaleEffect(0.3f);
		//peKeyPlayerTrail = particleEffectManager.addParticleEffect(effect, 4);
		//effect.dispose();
	}
	
	
	public void update(float deltaTime){
		elapsedTime = stopwatch.elapsedTime();
		handleInput();
		player.update(deltaTime, worldTouchPos);
		//particleEffectManager.update() happens in the Game screen, as to still update particles
		//while paused
		updateEnemies(deltaTime);
		checkCollissions(deltaTime);
	}
	
	private void updateEnemies(float deltaTime){
		for(Enemy enemy : enemies){
			particleEffectManager.spawnParticle(peKeyEnemyTrail,
					enemy.position.x+enemy.dirVector.x*2.5f, enemy.position.y+enemy.dirVector.y*2.5f);
			enemy.update(deltaTime);
		}
	}
	
	private void handleInput(){
		if(Gdx.input.isTouched()){
			screenTouchPos.x = Gdx.input.getX();
			screenTouchPos.y = Gdx.input.getY();
			worldTouchPos = gameScreen.camera.unproject(screenTouchPos);
		}
	}
	
	private void checkCollissions(float dt){
		//Boundary Collisions
		checkPlayerBoundaryCollision();
		checkEnemyBoundaryCollision();
		
		checkPlayerEnemyCollision();
	}
	
	private void checkPlayerEnemyCollision(){
		for(Enemy enemy : enemies){
			if(enemy.circle.overlaps(player.circle)){
				gameOver();
				
			}
		}
	}
	
	private void gameOver(){
		gameScreen.setPaused(true);
		GamePreferences prefs = GamePreferences.instance;
		
		if(this.elapsedTime > prefs.bestTime){
			prefs.bestTime = this.elapsedTime;
			prefs.saveSaveState();
		}
		
		gameScreen.setGameOver(true);
	}
	
	private void checkPlayerBoundaryCollision(){
		if(!CollissionChecker.circleContainsCircle(boundary.circle, player.circle)){
			gameOver();
		}
	}
	
	private void checkEnemyBoundaryCollision(){
		for(Enemy enemy : enemies){
			if(!CollissionChecker.circleContainsCircle(boundary.circle, enemy.circle)){
				spawnEnemyParticle(enemy);
				tempVector1.set(boundary.position); // origin (boundary middle)
				tempVector2.set(enemy.position); //enemy middle
				tempVector2.sub(tempVector1).nor(); //normal vector from enemy direction
				enemy.dirVector.sub(tempVector2.scl(2.0f)).nor();
			}
		}
	}
	
	private void spawnEnemyParticle(Enemy enemy){
		float angle = enemy.dirVector.angle();
		float radius = enemy.circle.radius;
		float circX = MathUtils.cosDeg(angle)*radius + enemy.circle.x;
		float circY = MathUtils.sinDeg(angle)*radius + enemy.circle.y;
		//particleEffects.addEnemyCollisionParticle(circX, circY);
		particleEffectManager.spawnParticle(peKeyEnemyBoundaryCollision, circX, circY);
		//particleEffectManager.setPosition(1, circX, circY);
	}
	
	public void reset(){
		player.reset();
		for(Enemy enemy : enemies){
			enemy.reset();
		}
	}
	
	public void dispose(){
		boundary.dispose();
	}
	
	public boolean isNewGame(){
		boolean bool = player.isInStartPosition();
		for(Enemy enemy : enemies){
			bool = bool && enemy.isInStartPosition();
		}
		Gdx.app.debug(TAG, "new game: " + bool);
		return bool;
	}
	
	public void resetStopwatch(){
		this.stopwatch.resetTime();
	}
	
	public void handlePaused(){
		this.stopwatch.pause();
	}
	
	public void handleResume(){
		this.stopwatch.resume();
	}
	
	/**return how many seconds have passed, accounting for pauses*/
	public float getTime(){
		return this.elapsedTime;
	}
	

}
