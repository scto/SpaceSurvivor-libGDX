package com.eobgames.spacesurvivor.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractGameObject {
	
	//Hvert game object hefur staðsetningu, stærð
	//og origin (punkturinn þar sem scale, rotation... gerast um)
	//ásamt scale og rotation.
	
	//Hugsað þannig að hægt sé að búa til sitthvoran abstract klasann 
	//fyrir dynamic entities og static objects
	
	public Vector2 position; 
	public Vector2 dimension;
	public Vector2 origin;
	public Vector2 scale;
	public float rotation;
	
	/**
	 * @param position (0,0) by default
	 * @param dimension  (1,1) bara placeholder til að hafa allarvegana einhverja stærð
	 * @param origin (0,0) by default
	 * @param scale (1,1) by default
	 * @param rotation 0 by default
	 * 
	 */
	public AbstractGameObject (){
		position = new Vector2();
		dimension = new Vector2(1,1);
		origin = new Vector2();
		scale = new Vector2(1,1);
		rotation = 0;
	}
	
	public abstract void update(float deltaTime);
	
	public abstract void render(SpriteBatch batch);

}
