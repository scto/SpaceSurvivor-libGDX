package com.eobgames.spacesurvivor.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.eobgames.spacesurvivor.SpaceSurvivorMain;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
			config.useAccelerometer = false;
			config.useCompass = false;
		initialize(new SpaceSurvivorMain(), config);
	}
}
