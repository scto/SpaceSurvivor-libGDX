package com.eobgames.spacesurvivor.game.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.eobgames.spacesurvivor.game.Assets;
import com.eobgames.spacesurvivor.util.Constants;

public class Boundary  extends AbstractGameObject {
	private Sprite boundary;
	public Circle circle;
	

	
	public Boundary(){
		
		this.boundary = Assets.instance.boundary.circleBoundary;
		
		boundary.setOriginCenter();
		//magic numbers from Contants class
		boundary.setSize(99.58f, 99.58f);
		
		boundary.setPosition(Constants.WORLD_WIDTH/2 - boundary.getWidth()/2, Constants.WORLD_HEIGHT/2 - boundary.getHeight()/2);
		circle = new Circle(boundary.getWidth()/2 + 0.79f, boundary.getHeight()/2 + 0.79f,  Constants.BOUNDARY_RADIUS);
		
		this.position.set(Constants.WORLD_WIDTH/2, Constants.WORLD_HEIGHT/2);
		
		//createPixmap();
	}
	
	

	
	public void render(SpriteBatch batch){
		boundary.draw(batch);
		//debugCircle.draw(batch);
	}
	
	public void resize(int width, int height){
	}

	@Override
	public void update(float deltaTime) {
	}
	
	public void dispose(){
		//textureCircle.dispose();
	}
	
	
	
	
	
	
	/**used for debug purposes*/
	//private Pixmap pixmap;
	//private Texture textureCircle;
	//Sprite debugCircle;
	/**used for debug purposes (visualizing inner boundary
	 * differend aspect ratio stretchings... all the fun stuff :/)*/
	/*private void createPixmap(){
		pixmap = new Pixmap((int)circle.radius*2+2, (int)circle.radius * 2+2, Format.RGBA8888);
		pixmap.setColor(0,0,1,1);
		pixmap.drawCircle(pixmap.getWidth()/2, pixmap.getHeight()/2, (int)circle.radius);
		
		Texture textureCircle = new Texture(pixmap);
		pixmap.dispose();
		debugCircle = new Sprite(textureCircle);
		debugCircle.setPosition(Constants.WORLD_WIDTH/2 - debugCircle.getWidth()/2, Constants.WORLD_HEIGHT/2 - debugCircle.getHeight()/2);
	}*/
	
}
