package com.eobgames.spacesurvivor.util;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Klasi fyrir collision checks í þeim fáu tilfellum sem LibGDX er ekki að standa sig
 * @author Einar Örn
 *
 */
public class CollissionChecker {
	
	private static Vector2 vectorA = new Vector2();
	private static Vector2 vectorB = new Vector2();

	public static boolean circleContainsCircle(Circle circA, Circle circB){
		vectorA.x = circA.x;
		vectorA.y = circA.y;
		vectorB.x = circB.x;
		vectorB.y = circB.y;
		
		boolean bool = circA.radius > circB.radius;
		bool = bool && (vectorA.dst2(vectorB) < (circA.radius - circB.radius) * (circA.radius - circB.radius));
		
		return bool;
	}
	
}
