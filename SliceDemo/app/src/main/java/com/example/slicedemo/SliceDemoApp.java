package com.example.slicedemo;

import android.app.Application;
import android.content.Context;

public class SliceDemoApp extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = SliceDemoApp.this;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        SliceDemoApp.context = context;
    }
}
