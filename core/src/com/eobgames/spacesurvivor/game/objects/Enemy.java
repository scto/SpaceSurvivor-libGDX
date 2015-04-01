package com.eobgames.spacesurvivor.game.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.eobgames.spacesurvivor.game.Assets;
import com.eobgames.spacesurvivor.util.Constants;

public class Enemy extends AbstractGameObject {
	
	/** Collision circle*/
	public Circle circle;
	
	private Vector2 startPos;
	Sprite sprite;
	public Vector2 dirVector;
	Vector2 velVector;
	Vector2 tempVector;
	public Vector2 tempNormal;
	
	private float moveSpeed = 50; //units/s
	
	public Enemy(float x, float y){
		
		sprite = Assets.instance.enemy.circleEnemy;
		this.circle = new Circle(x, y, Constants.ENEMY_RADIUS);
		this.position.set(x, y);
		
		startPos = this.position.cpy();
		dirVector = new Vector2();
		velVector = new Vector2();
		tempVector = new Vector2();
		tempNormal = new Vector2();
		
		initDirVector();
		
		sprite.setSize(Constants.ENEMY_SIZE, Constants.ENEMY_SIZE);
		
	}
	
	private void initDirVector(){
		float x = MathUtils.random(-1.0f, 1.0f);
		float y = MathUtils.random(-1.0f, 1.0f);
		dirVector.set(x, y).nor();
		//Gdx.app.debug("Enemy class", "dirVector" + dirVector.toString());
	}
	
	@Override
	public void update(float deltaTime) {
		tempVector.x = dirVector.x;
		tempVector.y = dirVector.y;
		//float displacement = moveSpeed*deltaTime;
		
		tempVector.scl(moveSpeed*deltaTime);
		
		this.position.add(tempVector);
		//this.position.add(dirVector);
		
		circle.setPosition(this.position);
		//Gdx.app.debug("Enemy class", "position vector " + this.position.toString());
		//Gdx.app.debug("Enemy class", "displacement: " + displacement);
	}

	@Override
	public void render(SpriteBatch batch) {
		//
		sprite.setPosition(this.position.x - sprite.getWidth()/2, this.position.y - sprite.getHeight()/2);
		sprite.draw(batch);
	}
	
	public void setDirVector(Vector2 vector){
		this.dirVector.set(vector);
	}
	
	public void reset(){
		this.position.set(this.startPos);
		this.initDirVector();
	}

	public boolean isInStartPosition() {
		return this.startPos.equals(this.position);
	}


}
