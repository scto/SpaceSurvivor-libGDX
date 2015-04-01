package com.eobgames.spacesurvivor.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class UtilFunctions {

	public static Sprite spriteFromRegion(AtlasRegion tempReg){
		return new Sprite(tempReg.getTexture(), tempReg.getRegionX(), tempReg.getRegionY(),
				tempReg.getRegionWidth(), tempReg.getRegionHeight());
	}
	
	
	/**
	 * Converts screen coords to world coords
	 * @param screenX as reported by android (not related to "virtual world" size)
	 * @return
	 */
	public static float toWorldX(float screenX){
		return screenX * Constants.WORLD_WIDTH / Gdx.graphics.getWidth();
	}
	
	public static float toWorldY(float screenY){
		float y = Gdx.graphics.getHeight()- Gdx.input.getY();
		y *= Constants.WORLD_HEIGHT / Gdx.graphics.getHeight();
		return y;
	}
	
	/**
	 * Skilar sama value, nema með places marktæka stafi
	 * @param value gildið sem á að rúna
	 * @param places hversu margir marktækir stafir
	 * @return
	 */
	public static float round(float value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.floatValue();
	}
}
