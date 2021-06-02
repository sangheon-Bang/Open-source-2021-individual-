package com.example.batteryalarm;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.BatteryManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import java.util.Random;

public class AlarmService extends Service {
    private static int batPct;
    int a;

    private BroadcastReceiver alarmReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();

            if(intent.ACTION_BATTERY_CHANGED.equals(action)){

                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

                float batteryPct = level / (float)scale;

                batPct= (int)(batteryPct * 100)+a;

                if(batPct<=MainActivity.getBatSet()){
                    a=100;
                    Intent intent1= new Intent(getApplicationContext(),AlarmScreen.class);
                    startActivity(intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }


            }
        }
    };

    @Override
    public void onCreate() {
        registerReceiver(alarmReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(alarmReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}


