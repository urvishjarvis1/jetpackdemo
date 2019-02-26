package com.example.slicedemo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static int mCount=0;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        if (savedInstanceState != null)
            textView.setText(Integer.toString(savedInstanceState.getInt("temp")));
        else textView.setText(Integer.toString(mCount));
        ((Button)findViewById(R.id.plus)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCount+=1;
                textView.setText(Integer.toString(mCount));
            }
        });
        ((Button)findViewById(R.id.minus)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCount-=1;
                textView.setText(Integer.toString(mCount));
            }
        });
    }
    public static void updateTemp(Context context,int newValue){
        if(newValue!=mCount){
            mCount=newValue;
            Uri uri=Uri.parse("content://com.example.slicedemo/count");
            context.getContentResolver().notifyChange(uri,null);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("temp", mCount);
    }
}
