package com.example.batteryalarm;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;

import androidx.core.app.ActivityCompat;

public class AlarmScreen extends Activity {
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savesInstanceState) {

        super.onCreate(savesInstanceState);
        setContentView(R.layout.alarm_screen);

        mediaPlayer = MediaPlayer.create(this,R.raw.dundun);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

        Button btn = (Button) findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                ActivityCompat.finishAffinity(AlarmScreen.this);
                System.exit(0);

            }


        });
    }
}
