package com.eobgames.spacesurvivor.game.objects;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.eobgames.spacesurvivor.game.Assets;
import com.eobgames.spacesurvivor.util.Constants;

public class Player extends AbstractGameObject {
	private static final String TAG = Player.class.getName();
	
	/** Collision circle*/
	public Circle circle;
	private Color circleColor;
	private Sprite circleSprite;
	private Vector2 startPos;
	
	private Vector3 touchPos;
	private boolean canMove;
	
	
	public Player (Vector3 worldTouchPos){
		touchPos = new Vector3();
		init();
	}
	
	private void init(){
		//reference the Sprite and set aspect according to WORLD_WIDTH and heigth
		circleSprite = Assets.instance.player.circlePlayer;
		//UtilFunctions.setSpriteAspectSize(circleSprite);
		circleSprite.setSize(13.2f, 13.2f);
		
		//set initial player stats
		//this.dimension.set()
		//this.position.set(circleSprite.getWidth()/2, circleSprite.getHeight()/2);
		this.position.set(Constants.WORLD_WIDTH/2, Constants.WORLD_HEIGHT/2);
		this.startPos = new Vector2(this.position);
		
		//circle used for collisions, circleColor for debugging
		circle = new Circle(this.position.x, this.position.y, 4.86f);
		
		canMove = false;
		
		this.updateSprite();
	}

	@Override
	public void update(float deltaTime) {
		if(canMove){
			updatePlayer(deltaTime);
			updateSprite();
		}
	}
	
	public void update(float deltaTime, Vector3 worldTouchPos){
		if(canMove){
			//hugsanlega bæta við if(Gdx.input.isTouched())...
			touchPos = worldTouchPos;
			
			updatePlayer(deltaTime);
			updateSprite();
		}
	}
	
	private void updatePlayer(float deltaTime){
		//move player
		if(Gdx.app.getType() == ApplicationType.Desktop){
			handleDesktopInput(deltaTime);
		}
		
		this.position.set(touchPos.x, touchPos.y);
		
		//handleTouchInput();
		
		this.circle.setPosition(this.position);

	}
	
	private void updateSprite(){
		circleSprite.setPosition(this.position.x - circleSprite.getWidth()/2, 
				this.position.y - circleSprite.getHeight()/2);
	}
	
	
	private void handleDesktopInput(float deltaTime){
		float moveX = 0.0f;
		float moveY = 0.0f;
		float vel = 20; //m/s
		float amount = vel * deltaTime;
		
		if(Gdx.input.isKeyPressed(Keys.LEFT)){
			moveX -= amount;
		}
		
		if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			moveX += amount;
		}
		
		if(Gdx.input.isKeyPressed(Keys.UP)){
			moveY += amount;
		}
		
		if(Gdx.input.isKeyPressed(Keys.DOWN)){
			moveY -= amount;
		}
		
		this.position.add(moveX,  moveY);
		
	}

	@Override
	public void render(SpriteBatch batch) {
		circleSprite.draw(batch);

	}
	
	public void drawCircle(){

	}
	
	public String getCirclePositionString(){
		return this.circle.toString();
	}
	
	public void setCanMove(boolean bool){
		this.canMove = bool;
	}
	
	/**
	 * Athugar hvort touchDown hafi gerst á spilara
	 * @param screenX x hnit úr touchDown eventi
	 * @param screenY y hnit úr touchDown eventi
	 */
	public void touchDown(Vector3 worldTouchPos){

		if(this.circle.contains(worldTouchPos.x, worldTouchPos.y))
			this.canMove = true;
	}
	
	public void touchUp(){
		this.canMove = false;
	}
	
	public void reset(){
		Gdx.app.debug(TAG, "playerPos: " + this.position.toString() + ", playerStartPos: " + this.startPos);
		this.position.set(this.startPos);
		this.circle.setPosition(this.position);
		circleSprite.setPosition(this.position.x - circleSprite.getWidth()/2, 
				this.position.y - circleSprite.getHeight()/2);
		Gdx.app.debug(TAG, "playerPos: " + this.position.toString() + ", playerStartPos: " + this.startPos);

	}

	public boolean isInStartPosition() {
		return this.position.equals(this.startPos);
	}
	

}
