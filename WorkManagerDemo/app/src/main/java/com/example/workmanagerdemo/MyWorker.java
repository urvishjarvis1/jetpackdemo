package com.example.workmanagerdemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;

public class MyWorker extends Worker {
    @NonNull
    @Override
    public Result doWork() {
        String title = getInputData().getString("title");
        String msg = getInputData().getString("msg");

        if (title == null || msg == null)
            sendNotifcation("Simple WorkManager", "Notifying using WorkManager");
        else sendNotifcation(title, msg);
        Data output = new Data.Builder().putString("output", "data from worker").build();
        setOutputData(output);
        return Result.SUCCESS;
    }

    private void sendNotifcation(String title, String msg) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "default").setContentTitle(title).setContentText(msg).setSmallIcon(R.mipmap.ic_launcher_round).setDefaults(Notification.DEFAULT_ALL);
        notificationManager.notify(1, notification.build());

    }
}
