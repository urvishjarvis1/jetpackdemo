package com.example.palletdemo;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button mBtnChangeDrawable;
    private ImageView mImageView;
    private TextView mTextView;
    private Palette palette;
    private ArrayList<Drawable> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnChangeDrawable = findViewById(R.id.btnchangedrawable);
        mImageView = findViewById(R.id.imageview);
        mTextView = findViewById(R.id.resultantcolor);
        arrayList = new ArrayList<Drawable>();
        arrayList.add(getDrawable(R.drawable.i2));
        arrayList.add(getDrawable(R.drawable.i5));
        arrayList.add(getDrawable(R.drawable.i7));
        arrayList.add(getDrawable(R.drawable.i8));
        arrayList.add(getDrawable(R.drawable.i9));
        arrayList.add(getDrawable(R.drawable.i10));
        arrayList.add(getDrawable(R.drawable.i11));
        arrayList.add(getDrawable(R.drawable.i12));
        arrayList.add(getDrawable(R.drawable.i13));
        arrayList.add(getDrawable(R.drawable.i14));
        arrayList.add(getDrawable(R.drawable.i15));
        arrayList.add(getDrawable(R.drawable.i16));
        arrayList.add(getDrawable(R.drawable.i17));

        final Random random = new Random();
        int drawableId = random.nextInt(12);

        mImageView.setImageDrawable(arrayList.get(drawableId));
        palette = getPalette(arrayList.get(drawableId));
        mTextView.setBackgroundColor(palette.getVibrantSwatch().getRgb());
        mBtnChangeDrawable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int drawableId = random.nextInt(12);
                mImageView.setImageDrawable(arrayList.get(drawableId));
                palette = getPalette(arrayList.get(drawableId));
                if (palette.getVibrantSwatch() != null) {
                    Log.d(TAG, "onClick: " + palette.getVibrantColor(Color.RED));
                    mTextView.setText("");
                    mTextView.setBackgroundColor(palette.getVibrantSwatch().getRgb());
                }else{
                    mTextView.setText("Not able to extract color");
                }
            }
        });

    }

    private Palette getPalette(Drawable drawable) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        return Palette.generate(bitmapDrawable.getBitmap());
    }

}
