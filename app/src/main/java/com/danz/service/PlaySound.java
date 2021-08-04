package com.danz.service;
import android.net.Uri;
import android.media.MediaPlayer;
import android.os.Handler;
import android.content.Context;
import java.io.File;

public class PlaySound {
	boolean delayed;
	MediaPlayer FXPlayer;
    public void play(String s,Context context) {
        if (true) {
            if (!delayed) {
                delayed = true;
                if (FXPlayer != null) {
                    FXPlayer.stop();
                    FXPlayer.release();
                }
                FXPlayer = MediaPlayer.create(context,Uri.fromFile(new File(StaticActivity.cacheDir+s)));
                if (FXPlayer != null)
				//Volume reduced so sounds are not too loud
                    FXPlayer.setVolume(0.5f, 0.5f);
                FXPlayer.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							delayed = false;
						}
					}, 100);
            }
        }
    }
		public String ON = "On.ogg";
		public String OFF = "Off.ogg";
		public String OPEN_MENU = "OpenMenu.ogg";
		public String BACK = "Back.ogg";
		public String SELECT = "Select.ogg";
        public String INCREASE = "SliderIncrease.ogg";
        public String DECREASE= "SliderDecrease.ogg";
}

