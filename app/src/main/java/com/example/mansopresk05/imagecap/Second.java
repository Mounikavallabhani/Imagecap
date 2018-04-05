package com.example.mansopresk05.imagecap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.prefs.AbstractPreferences;

public class Second extends AppCompatActivity {
    Bitmap bt;
    ImageView iv2;
    Bitmap byteArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);

        iv2=(ImageView)findViewById(R.id.iv2);


        Bundle extras = getIntent().getExtras();
        byteArray = (Bitmap)extras.get("picture");

        iv2.setImageBitmap(byteArray);
//
//        byte[] byteArray = extras.getByteArray("PICTURE");
//        bt = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
//        iv2.setImageBitmap(bt);


    }
}
