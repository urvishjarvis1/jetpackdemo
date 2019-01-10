package com.example.workmanagerdemo;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkStatus;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private UUID uuid;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner = findViewById(R.id.spinner);
        final HashMap<String, UUID> requests = new HashMap<>();

        final Constraints.Builder constraints = new Constraints.Builder();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            constraints.setRequiresDeviceIdle(true);
        }
        constraints.setRequiresCharging(true);
        Data data = new Data.Builder().putString("title", "MainActivity").putString("msg", "Notification From MainActivity").build();
        final OneTimeWorkRequest oneTimeWorkRequest2 = new OneTimeWorkRequest.Builder(MyWorker.class).build();
        final OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyWorker.class).setInputData(data).setConstraints(constraints.build()).build();
        requests.put("onetime", oneTimeWorkRequest.getId());
        //minimum reperatinterval time should be TIME<=900000milis which is equvivelent to 15 min
        final PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 15, TimeUnit.MINUTES).setInputData(data).build();
        requests.put("peroidic", periodicWorkRequest.getId());
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, requests.keySet().toArray());
        spinner.setAdapter(arrayAdapter);
        ((Button) findViewById(R.id.simpleWorkButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkManager.getInstance().enqueue(oneTimeWorkRequest);

            }
        });
        ((Button) findViewById(R.id.periodic)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkManager.getInstance().enqueue(periodicWorkRequest);

            }
        });
        WorkManager.getInstance().getStatusById(oneTimeWorkRequest.getId()).observe(this, new Observer<WorkStatus>() {
            @Override
            public void onChanged(WorkStatus workStatus) {
                if (workStatus != null) {
                    ((TextView) findViewById(R.id.textView)).append("Status" + workStatus.getState().name() + "\n");
                }
                if (workStatus != null && workStatus.getState().isFinished()) {
                    String output = workStatus.getOutputData().getString("output");
                    ((TextView) findViewById(R.id.textView)).append("data:" + output + "\n");
                }
            }
        });
        WorkManager.getInstance().getStatusById(oneTimeWorkRequest2.getId()).observe(this, new Observer<WorkStatus>() {
            @Override
            public void onChanged(WorkStatus workStatus) {
                if (workStatus != null) {
                    ((TextView) findViewById(R.id.textView)).append("Status of 2nd" + workStatus.getState().name() + "\n");
                }
                if (workStatus != null && workStatus.getState().isFinished()) {
                    String output = workStatus.getOutputData().getString("output");
                    ((TextView) findViewById(R.id.textView)).append("data:" + output + "\n");
                }
            }
        });
        WorkManager.getInstance().getStatusById(periodicWorkRequest.getId()).observe(this, new Observer<WorkStatus>() {
            @Override
            public void onChanged(WorkStatus workStatus) {
                if (workStatus != null) {
                    ((TextView) findViewById(R.id.textView)).append("Periodic Status:" + workStatus.getState().name() + "\n");
                }
                if (workStatus != null && workStatus.getState().isFinished()) {
                    String output = workStatus.getOutputData().getString("output");
                    ((TextView) findViewById(R.id.textView)).append("data:" + output + "\n");
                }
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                uuid = requests.get(parent.getItemAtPosition(position).toString());
                key = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ((Button) findViewById(R.id.cancle)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uuid != null) {
                    Log.d(TAG, "onClick:get data " + uuid + "\t actual data" + oneTimeWorkRequest.getId() + "\t periodic data" + periodicWorkRequest.getId());
                    Log.d(TAG, "onClick:get data " + key + "\t actual data" + oneTimeWorkRequest.getId() + "\t periodic data" + periodicWorkRequest.getId());
                    WorkManager.getInstance().cancelWorkById(uuid);
                    requests.remove(key);
                    arrayAdapter.notifyDataSetChanged();
                } else
                    Toast.makeText(MainActivity.this, "Please select the req from spinner!", Toast.LENGTH_SHORT).show();
            }
        });
        ((Button) findViewById(R.id.chainwork)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WorkManager.getInstance().beginWith(oneTimeWorkRequest).then(oneTimeWorkRequest2).enqueue();
            }
        });
        ((Button) findViewById(R.id.parallel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkManager.getInstance().beginWith(oneTimeWorkRequest, oneTimeWorkRequest2).enqueue();
            }
        });
    }
}
