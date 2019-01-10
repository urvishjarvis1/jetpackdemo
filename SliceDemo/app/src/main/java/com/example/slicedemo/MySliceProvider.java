package com.example.slicedemo;


import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.SliceProvider;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;

public class MySliceProvider extends SliceProvider {
    private static int sReqcode = 0;

    /**
     * Instantiate any required objects. Return true if the provider was successfully created,
     * false otherwise.
     */
    @Override
    public boolean onCreateSliceProvider() {
        return true;
    }

    private PendingIntent getChangeIntent(int value) {
        Intent intent = new Intent(SliceBroadcastListener.ACTION_UP);
        intent.setClass(getContext(), SliceBroadcastListener.class);
        intent.putExtra("newvalue", value);
        return PendingIntent.getBroadcast(getContext(), sReqcode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }



    /**
     * Converts URL to content URI (i.e. content://com.example.slicedemo...)
     */
    @Override
    @NonNull
    public Uri onMapIntentToUri(@Nullable Intent intent) {
        // Note: implementing this is only required if you plan on catching URL requests.
        // This is an example solution.
        Uri.Builder uriBuilder = new Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT);
        if (intent == null) return uriBuilder.build();
        Uri data = intent.getData();
        if (data != null && data.getPath() != null) {
            String path = data.getPath().replace("/", "");
            uriBuilder = uriBuilder.path(path);
        }
        Context context = getContext();
        if (context != null) {
            uriBuilder = uriBuilder.authority(context.getPackageName());
        }
        return uriBuilder.build();
    }
    /**
     * Construct the Slice and bind data if available.
     */
    public Slice onBindSlice(Uri sliceUri) {

        if (SliceDemoApp.getContext() == null) {
            return null;
        }

        Context context = SliceDemoApp.getContext();
        /*int add=context.getResources().getIdentifier("ic_add_24dp","drawable",context.getPackageName());*/
        /*Drawable drawable=context.getResources().getDrawable(R.drawable.ic_add_24dp);
        Bitmap icon =((BitmapDrawable)drawable).getBitmap();*/
        SliceAction countUp = new SliceAction(getChangeIntent(MainActivity.mCount + 1), /*IconCompat.createWithBitmap(icon)*/IconCompat.createWithResource(context, R.drawable.ic_add_24dp), ListBuilder.ICON_IMAGE, "add");
        SliceAction countDown = new SliceAction(getChangeIntent(MainActivity.mCount - 1), IconCompat.createWithResource(context, R.drawable.ic_minus_24dp), ListBuilder.ICON_IMAGE, "minus");
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        SliceAction openTempActivity = new SliceAction(pendingIntent, IconCompat.createWithResource(context, R.drawable.ic_launcher_background), ListBuilder.ICON_IMAGE, "open activity");
        ListBuilder listBuilder = new ListBuilder(context, sliceUri, ListBuilder.INFINITY);
        listBuilder.addRow(new ListBuilder.RowBuilder(listBuilder).setTitle(Integer.toString(MainActivity.mCount))/*.addEndItem(countUp)*//*.addEndItem(countDown)*/.setPrimaryAction(openTempActivity));
        return listBuilder.build();
        /*return  null;*/
    }


}
