package com.example.downloadmanagerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private DownloadManager mDownloadManager;
    long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDownloadManager=(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        ((Button)findViewById(R.id.downloadimg)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri downloadUri=Uri.parse("https://speed.hetzner.de/100MB.bin");
                DownloadManager.Request request=new DownloadManager.Request(downloadUri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                request.setAllowedOverRoaming(false);
                request.setTitle("GadgetSaint Downloading " + "Sample" + ".png");
                request.setDescription("Downloading " + "Sample" + ".png");
                request.setVisibleInDownloadsUi(true);
                request.setShowRunningNotification(true);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/GadgetSaint/"  + "/" + "Sample" + ".png");


                 id= mDownloadManager.enqueue(request);

            }
        });
        registerReceiver(mDownloadBroadcastReceiver,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }
    private BroadcastReceiver mDownloadBroadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(id==intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1)){
                Toast.makeText(context, "Download completed!", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mDownloadBroadcastReceiver);
    }
}
