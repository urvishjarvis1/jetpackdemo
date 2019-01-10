package com.example.slicedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SliceBroadcastListener extends BroadcastReceiver {
    public static String ACTION_UP="com.example.slicedemo.ACTION_CHANGE";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        if(ACTION_UP.equals(action)&&intent.getExtras()!=null){
            int newValue=intent.getExtras().getInt("newvalue",MainActivity.mCount);
            MainActivity.updateTemp(context,newValue);
        }
    }
}
