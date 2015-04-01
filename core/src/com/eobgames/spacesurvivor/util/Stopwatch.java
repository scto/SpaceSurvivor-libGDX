package com.eobgames.spacesurvivor.util;

import com.badlogic.gdx.utils.TimeUtils;


public class Stopwatch {
	
	private long startTime;
	private long pauseTime;
	
	public Stopwatch(){
		startTime = TimeUtils.millis();
		pauseTime = startTime;
	}
	
	/**endurstillir á 0*/
	public void resetTime(){
		startTime = TimeUtils.millis();
	}
	
	/** returns time in seconds since last reset*/
	public float elapsedTime(){
		return (float)((TimeUtils.millis() - startTime) / (float)1000);
	}
	
	/**stopwatch pauses */
	public void pause(){
		this.pauseTime = TimeUtils.millis();
	}
	
	/**stopwatch starts measuring again after pause*/
	public void resume(){
		startTime = (startTime-pauseTime) + TimeUtils.millis();
	}
	
	/** tester function*/
	public static void main(String args[]){
		Stopwatch sw = new Stopwatch();
		int i = 1;
		float time = sw.elapsedTime();
		
		for(int j = 0; j<10; j++){
			while(time<i){
				time = sw.elapsedTime();
			}
			
			System.out.println(time);
			i++;
		}
	}
	
}
