package com.example.team12_alarm.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.team12_alarm.R;
import com.example.team12_alarm.Service.AlarmReceiver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AppAutoActivity extends AppCompatActivity {

    private static String packName;
    private static String wholeClassName;
    private static boolean ReserOn;
    private static boolean ReserOff;
    int alarmHour, alarmMinute;
    Calendar alarmCalendar;
    Button alarmButton, ReservationOnButton,ReservationOffButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appauto);

        alarmButton = (Button) findViewById(R.id.alarmBtn);
        ReservationOnButton = (Button) findViewById(R.id.reserONBtn);
        ReservationOffButton= (Button) findViewById(R.id.reserOFFBtn);
        ListView listView = findViewById(R.id.listView);

        List<String> list =new ArrayList<>();
        list.add("유튜브");
        list.add("TV");
        list.add("인터넷");
        list.add("카카오톡");
        list.add("전화");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String data = (String) adapterView.getItemAtPosition(position);
                switch(data){
                    case "유튜브":
                        packName="com.google.android.youtube";
                        wholeClassName="com.google.android.youtube.HomeActivity";
                        Toast.makeText(getApplicationContext(), "유튜브 선택됨", Toast.LENGTH_SHORT).show();
                    /*case "TV":
                        packName="com.sec.android.app.dmb";
                        wholeClassName="com.sec.android.app.dmb.activity.DMBFullScreenView";
                        Toast.makeText(getApplicationContext(), "TV 선택됨", Toast.LENGTH_SHORT).show();
                    case "인터넷":
                        packName="com.android.browser";
                        wholeClassName="com.android.browser.BrowserActivity";
                        Toast.makeText(getApplicationContext(), "인터넷 선택됨", Toast.LENGTH_SHORT).show();
                    case "카카오톡":
                        packName="com.kakao.talk";
                        wholeClassName="찾지못했음..";
                    case "전화":
                        packName="com.sec.android.app.dialertab";
                        wholeClassName="com.sec.android.app.dialertab.DialerTabActivity";
                        Toast.makeText(getApplicationContext(), "전화 선택됨", Toast.LENGTH_SHORT).show();*/

                }

            }
        });

        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener listener=new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        alarmHour = hourOfDay;
                        alarmMinute = minute;
                        setAlarm();
                    }
                };

                TimePickerDialog timePickerDialog
                        = new TimePickerDialog(AppAutoActivity.this, android.R.style.Theme_Holo_Light_Dialog,
                        listener, alarmHour, alarmMinute, true);
                timePickerDialog.show();
            }
        });

        ReservationOnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ReserOn=true;
                alarmButton.setVisibility(View.VISIBLE);
            }
        });

        ReservationOffButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ReserOff=true;
                alarmButton.setVisibility(View.VISIBLE);
            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }


    void setAlarm() {
        Date date=new Date();
        alarmCalendar = Calendar.getInstance();
        alarmCalendar.setTimeInMillis(System.currentTimeMillis());
        alarmCalendar.set(Calendar.HOUR_OF_DAY, alarmHour);
        alarmCalendar.set(Calendar.MINUTE, alarmMinute);
        alarmCalendar.set(Calendar.SECOND, 0);
        // TimePickerDialog 에서 설정한 시간을 알람 시간으로 설정

        if (alarmCalendar.before(Calendar.getInstance())) alarmCalendar.add(Calendar.DATE, 1);
        // 알람 시간이 현재시간보다 빠를 때 하루 뒤로 맞춤
        //intent
        Intent alarmIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmIntent.setAction(AlarmReceiver.ACTION_RESTART_SERVICE);
        //Broadcast
        PendingIntent alarmCallPendingIntent
                = PendingIntent.getBroadcast
                (AppAutoActivity.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            alarmManager.setExactAndAllowWhileIdle
                    (AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(), alarmCallPendingIntent);
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            alarmManager.setExact
                    (AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(), alarmCallPendingIntent);
    }

    public static String getPackName(){
        return packName;
    }

    public static String getWholeClassName(){
        return wholeClassName;
    }

    public static boolean isReserOn(){
        return ReserOn;
    }

    public static boolean isReserOff(){
        return ReserOff;
    }

}
