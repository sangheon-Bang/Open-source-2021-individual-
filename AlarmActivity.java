package com.example.killapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class AlarmActivity extends AppCompatActivity {

    private static boolean flag=false;
    Calendar calendar;
    SwipeButton swipeButton;
    TextView timeText;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        calendar = Calendar.getInstance();
        swipeButton = (SwipeButton) findViewById(R.id.swipe_btn);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        // 잠금 화면 위로 activity 띄워줌

        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.dundun);   // 소리를 재생할 MediaPlayer
        mediaPlayer.setLooping(false);
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){

            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                flag=true;
                finish();
                if(flag&& SendSMS.getIsSelected()) {
                    Toast.makeText(getApplicationContext(), "문자메세지 전송", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + SendSMS.getPhoneNum()));
                    intent.putExtra("sms_body", SendSMS.getSmsText());
                    startActivity(intent);
                }
            }

        });



        swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                mediaPlayer.stop();
                finish();
            }
        }); // Swipe Button 밀어서 해제
    }

    public static boolean getFlag(){
        return flag;
    }



}
