package com.example.volansys.alarmmanager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;


import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {
    private ToggleButton mtoggleAlarm;
    public static int NOTIFICATION_ID = 1;
    private String channel = "Wake Up";
    private String channelId = "wakupAlarm";
    private String channelDesc = "WakeUP";

    private NotificationManager notificationManager;


    private static final String ACTION_NOTIFY="com.example.volansys.alarmmanager.ACTION_NOTIFY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtoggleAlarm = findViewById(R.id.toggleAlarm);
        final AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent=new Intent(this,MyReceiver.class);
        final PendingIntent pendingIntent=PendingIntent.getBroadcast(this,NOTIFICATION_ID,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        mtoggleAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //createNotification("Alarm On!!",getApplicationContext());
                    long stime=SystemClock.elapsedRealtime()+10*1000;
                    long dtime=10*1000;
                    Log.d("str","stime:"+stime+"  dtime:"+dtime);
                    alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,stime,dtime,pendingIntent);
                    Toast.makeText(MainActivity.this, "Alarm set", Toast.LENGTH_SHORT).show();
                }
                else {

                    alarmManager.cancel(pendingIntent);
                    Toast.makeText(MainActivity.this, "Alarm off", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}

