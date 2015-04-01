package com.eobgames.spacesurvivor.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class ParticleEffects implements Disposable {
	
	
		Array<ParticleEffectPool> effectPools;

		ParticleEffectPool enemyCollisionPool;
		Array<PooledEffect> effects;
		//
		ParticleEffectPool enemyTrailPool;
		Array<ParticleEffect> enemyTrailEffects;

		
		public ParticleEffects(){
			//effectPools = new Array();
			
			effects = new Array();
			ParticleEffect enemyCollision = loadEnemyCollision();
			enemyCollisionPool = new ParticleEffectPool(enemyCollision, 1, 5);
			
			
			enemyTrailEffects = new Array();
			//ParticleEffect enemyTrail = loadParticleEffect("trailTest.p");
			//enemyTrailPool = new ParticleEffectPool(enemyTrail, 1, 8);
			
			//enemyCollision.dispose();
			
			//effectPools.add(enemyCollisionPool);
		}
		
		
		
		private ParticleEffect loadParticleEffect(String name){
			ParticleEffect effect = new ParticleEffect();
			effect.load(Gdx.files.internal("particles/" + name), Gdx.files.internal("particles"));
			return null;
		}
		
		
		
		private ParticleEffect loadEnemyCollision(){
			ParticleEffect enemyCollision = new ParticleEffect();
			enemyCollision.load(Gdx.files.internal("particles/enemy_boundary.p"), Gdx.files.internal("particles"));

			ParticleEmitter peEmitter = enemyCollision.getEmitters().first();
			peEmitter.setPosition(0, 0);
			//enemyCollision.start();
			enemyCollision.scaleEffect(0.07f);
			return enemyCollision;
		}
		
		public void updateParticles(float deltaTime){
			/*for(ParticleEffectPool pool : effectPools){
				for(int i = 0; i < effects.size; i++){
					PooledEffect effect = (PooledEffect) effects.get(i);
					effect.update(deltaTime);
					if(effect.isComplete()){
						System.out.println("inside for-if");
						effect.reset();
						enemyCollisionPool.free(effect);
						effects.removeIndex(i);
					}
				}
			}*/
			
			for(int i = 0; i < effects.size; i++){
				PooledEffect effect = (PooledEffect) effects.get(i);
				effect.update(deltaTime);
				
				if(effect.isComplete()){
					System.out.println("inside for-if");
					effect.reset();
					enemyCollisionPool.free(effect);
					effects.removeIndex(i);
				}
			}
		}
		
		public void drawParticles(SpriteBatch batch){
			
			for(int i = 0; i < effects.size; i++){
				PooledEffect effect = (PooledEffect) effects.get(i);
				effect.draw(batch);
			}
		}
			
			
		public void addEnemyCollisionParticle(float x, float y){
			PooledEffect effect = enemyCollisionPool.obtain();
			effect.setPosition(x, y);
			effect.start();
			effects.add(effect);
		}

		

		@Override
		public void dispose() {
			//óþarfi held ég með pool, kanna samt betur
			enemyCollisionPool.freeAll(effects);
			enemyCollisionPool.clear();
		}
		
		
		

}
