package com.eobgames.spacesurvivor.util;

import com.badlogic.gdx.Gdx;

//TODO: fyrir allan klasan, taka aðeins til, hópa saman skilda hluti, taka út gamla sem ekki eru í notkun

public class Constants {
	/*
	 * Size of sprites is based on original res of 1080x720 (so magic numbers (almost))
	 *  Boundary: 717/720 => 99.58f, og radius: 635/2 / 720 => 44.16667
	 *  Player: 95/720 => 13.2f, og radius: 70/2 / 720 => 4.86
	 *  Enemy: 115/720 => 15.97f og radius: 90/2 / 720 => 6.25;
	 */
	
	//Asset Sizes based on original 1080x720 res
	public static final float BOUNDARY_RADIUS = 44.16667f;
	public static final float ENEMY_RADIUS = 6.25f;
	public static final float ENEMY_SIZE = 15.97f;
	public static final float PLAYER_RADIUS = 4.86f;
	
	//for debug everywhere
	public static final boolean DEBUG = false;
	
	//The full world size in virtual units
	public static final float WORLD_WIDTH = 100.0f;
	public static final float WORLD_HEIGHT = 100f;
	
	//size for gui drawing
	public static final float CAMERA_GUI_WIDTH = 500;
	public static final float CAMERA_GUI_HEIGHT = 900;
	
	//preferences
	public static final String PREFERENCES = "spacesurvivor.prefs";
	
	//TextureAtlases
	public static final String ATLAS_GAME = "images/mainAssets.atlas";

}
