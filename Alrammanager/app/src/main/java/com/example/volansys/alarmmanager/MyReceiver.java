package com.example.volansys.alarmmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {
    private static String TAG = "DOZE DEMO";
    private static String GROUP_ID = "com.example.Alarmgroup.ALRAM";
    private String channel = "Wake Up";
    private String channelId = "wakupAlarm";
    private String channelDesc = "WakeUP";
    private NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotification("WakeUP", context);
        Log.d("str", "onrec");
    }

    private void createNotification(String msg, Context context) {
        MainActivity.NOTIFICATION_ID += 1;
        NotificationCompat.Builder builder;

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Intent toast = new Intent(context, NotificationActionReciever.class);
        toast.setAction("com.example.volansys.alarmmanager.ACTION_TOAST");
        PendingIntent toastIntent = PendingIntent.getBroadcast(context, 1, toast, 0);
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mchannel = notificationManager.getNotificationChannel(channelId);
            if (mchannel == null) {
                mchannel = new NotificationChannel(channelId, channel, NotificationManager.IMPORTANCE_HIGH);
                long[] longs = new long[]{100l, 200l, 300l, 400l, 500l, 400l, 300l, 200l, 400l};
                mchannel.setDescription(channelDesc);
                mchannel.enableVibration(true);
                mchannel.setVibrationPattern(longs);
                notificationManager.createNotificationChannel(mchannel);
            }
            builder = new NotificationCompat.Builder(context, channelId);


            builder.setContentTitle(msg)  // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder).setContentText(context.getString(R.string.app_name)).setDefaults(Notification.DEFAULT_ALL).setAutoCancel(true).setStyle(new NotificationCompat.BigTextStyle().setBigContentTitle("Wake Up Buddy")).setTicker(msg).setContentIntent(pendingIntent).setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400}).setAutoCancel(true).addAction(R.mipmap.ic_launcher_round, "Toast", toastIntent).setGroup(GROUP_ID).setGroupSummary(true);
        } else {

            builder = new NotificationCompat.Builder(context);


            builder.setContentTitle(msg)                           // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setContentText(context.getString(R.string.app_name))  // required
                    .setDefaults(Notification.DEFAULT_ALL).setAutoCancel(true).setTicker(msg).setStyle(new NotificationCompat.BigTextStyle().setBigContentTitle("Wake Up Buddy")).setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400}).setContentIntent(pendingIntent).setPriority(Notification.PRIORITY_HIGH).setAutoCancel(true).addAction(R.mipmap.ic_launcher_round, "Toast", toastIntent).setGroup(GROUP_ID).setGroupSummary(true);
        } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        Notification notification = builder.build();
        Log.d(TAG, "createNotification: Notification fired!! " + MainActivity.NOTIFICATION_ID);

        notificationManager.notify(MainActivity.NOTIFICATION_ID, notification);
    }
}
