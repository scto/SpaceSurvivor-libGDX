package com.eobgames.spacesurvivor.effects;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Keys;

/**
 * Manages particle effects, i.e. updates, rendering, rotations etc.
 * @author Einar Örn
 *
 */
public class ParticleEffectManager {
	
	ObjectMap<Integer, ParticleEffectPool> effectPools;
	//ParticleEffectPool effectPool;
	Array<PooledEffect> effects;
	//ObjectMap<Integer, PooledEffect> effects;

	/**
	 * 
	 */
	public ParticleEffectManager(){
		effects = new Array();
		//effects = new ObjectMap();
		effectPools = new ObjectMap();
	}
	
	/**
	 * Adds a new particle effect.
	 * Key is only used for special operations such as scaling, rotating etc. 
	 * Key could be ojbect.getHash()
	 * Every ParticleEffect is pooled, supply 1 for max 1
	 * @param file
	 * @param key a unique key
	 * @param poolMax max amount of pooled particleEffects
	 */
	public int addParticleEffect(FileHandle file, int poolMax){
		ParticleEffect effect = loadParticleEffect(file);
		ParticleEffectPool pool = new ParticleEffectPool(effect, 1, poolMax);
		
		int key = effect.hashCode();
		effectPools.put(new Integer(key), pool);
		return key;
	}
	
	public int addParticleEffect(ParticleEffect effect, int poolMax){
		ParticleEffectPool pool = new ParticleEffectPool(effect, 1, poolMax);
		int key = effect.hashCode();
		effectPools.put(new Integer(key), pool);
		return key;
	}
	
	/**
	 * Removes the particle effect from the manager,
	 * after which no references will refer to the ParticleEffect.
	 * @param key
	 */
	public void removeParticleEffect(int key){
		
	}
	
	/**
	 * updates every particle registered with the manager
	 */
	public void updateParticles(float deltaTime){
		
		for(PooledEffect effect : effects){
			effect.update(deltaTime);
			
			if(effect.isComplete()){
				effect.reset();
				effect.free();
				effects.removeValue(effect, false);
			}
		}

		/*Keys<Integer> keys = effects.keys();
		
		while(keys.hasNext()){
			Integer key = keys.next();
			PooledEffect effect = effects.get(key);
			effect.update(deltaTime);
			
			if(effect.isComplete()){
				effect.reset();
				effectPools.get(key).free(effect);
				effects.remove(key);
			}
		}*/
	}
	
	/**
	 * renders every currently active particle
	 */
	public void drawParticles(SpriteBatch batch){
		
		for(PooledEffect effect : effects){
			effect.draw(batch);
		}
		
		/*Keys<Integer> keys = effects.keys();
		
		while(keys.hasNext()){
			Integer key = keys.next();
			PooledEffect effect = effects.get(key);
			effect.draw(batch);
		}*/
	}
	
	//TODO: ætti frekar að skila pooled effect annarstaðar og nota set position á það
	// eins og með angle
	public void setPosition(int key, float x, float y){
		PooledEffect effect = effectPools.get(key).obtain();
		effect.setPosition(x, y);
		effect.start();
		
		effects.add(effect);
		//effects.put(key, effect);
		
		/*PooledEffect effect = enemyCollisionPool.obtain();
		effect.setPosition(x, y);
		effect.start();
		effects.add(effect);*/
	}
	
	
	public PooledEffect spawnParticle(int key, float x, float y){
		PooledEffect effect = effectPools.get(key).obtain();
		effect.setPosition(x, y);
		effect.start();
		
		//effects.put(key, effect);
		effects.add(effect);
		
		return effect;
	}
	
		
	/** Assumes that the particle effect image is in same folder **/
	public ParticleEffect loadParticleEffect(FileHandle file){
		ParticleEffect particleEffect = new ParticleEffect();
		FileHandle folder = file.parent();
		particleEffect.load(file, folder);

		return particleEffect;
	}

	/**
	 * sets the angle of the particle effect with give key
	 * NOTE: side effect: adds a particle at given position
	 */
	public void setAngle(PooledEffect effect, float angleLow, float angleHigh){
		
		for(ParticleEmitter emitter : effect.getEmitters()){
			emitter.getAngle().setHigh(angleHigh);
			emitter.getAngle().setLow(angleLow);
		}
	}
	
	public void setVelocity(PooledEffect effect, float low, float high){
		
		for(ParticleEmitter emitter : effect.getEmitters()){
			emitter.getVelocity().setLow(low);
			emitter.getVelocity().setHigh(high);
		}
	}
	
	public void freeAll(){
		Keys<Integer> keys = effectPools.keys();
		
		while(keys.hasNext){
			
		}
	}
	
	
	
	
}
