package com.example.slicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static int mCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView=findViewById(R.id.textView);
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
}
