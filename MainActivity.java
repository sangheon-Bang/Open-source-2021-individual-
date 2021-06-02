package com.example.batteryalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    static int bat_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.text1);
        Switch switchButton = (Switch) findViewById(R.id.switch1);


        final String[] battery_set = {"5","6","7","8","9","10","11","12","13","14","100"};


       Spinner spinner = (Spinner) findViewById(R.id.spinner1);

       ArrayAdapter<String> adapter;
       adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,battery_set);
       spinner.setAdapter(adapter);
       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               bat_set=Integer.parseInt(battery_set[i]);
               textView.setText("배터리 잔량이"+bat_set+"% 미만이 될 시 알람이 울립니다.");
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {
                textView.setText("배터리 잔량을 선택해주세요");
           }
       });


       Intent alarmService = new Intent(getApplicationContext(),AlarmService.class);


        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked){
                    switchButton.setText("알람 활성화");
                    startService(alarmService);
                    textView.setText("배터리 잔량이"+bat_set+"% 미만이 될 시 알람이 울립니다.");
                }
                else{
                    switchButton.setText("알람 비활성화");
                    stopService(alarmService);
                    textView.setText("배터리 잔량이"+bat_set+"% 미만이 될 시 알람이 울립니다.");
                }
            }
        });



    }

    public static int getBatSet(){
        return bat_set;
    }


}