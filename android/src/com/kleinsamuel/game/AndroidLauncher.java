package com.kleinsamuel.game;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		View decorView = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_FULLSCREEN
				| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
		decorView.setSystemUiVisibility(uiOptions);

		AudioAttributes attr = new AudioAttributes.Builder()
				.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
				.setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
				.setUsage(AudioAttributes.USAGE_GAME)
				.build();

		SoundPool soundPool = new SoundPool.Builder().setAudioAttributes(attr).build();

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useImmersiveMode = true;
		config.hideStatusBar = true;
		config.useWakelock = true;
		initialize(new GameClass(), config);
	}
}
