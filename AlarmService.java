package com.example.team12_alarm.Service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.team12_alarm.Activities.AlarmActivity;
import com.example.team12_alarm.Activities.AppAutoActivity;

public class AlarmService extends Service {
    String TAG = "TAG+Service";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "AlarmService");
        if(AppAutoActivity.isReserOn()){                //예약실행 버튼 눌렀을때
            Intent intent1 = new Intent(Intent.ACTION_MAIN);
            intent1.setComponent(new ComponentName(AppAutoActivity.getPackName(), AppAutoActivity.getWholeClassName()));
            startActivity(intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
        else if(AppAutoActivity.isReserOff()){                 //예약종료 버튼 눌렀을때
            ActivityManager am = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
            am.killBackgroundProcesses(AppAutoActivity.getPackName());
        }
        else{
            Intent alarmIntent = new Intent(getApplicationContext(), AlarmActivity.class);
            startActivity(alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

        return super.onStartCommand(intent, flags, startId);
    }
}
