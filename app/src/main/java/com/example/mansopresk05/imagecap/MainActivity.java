package com.example.mansopresk05.imagecap;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    public static final int CAM_REQ_CODE = 123;
    public static final int GAL_REQ_CODE = 321;

    public static final int CAM_PERMISSION_ACCESS_CODE = 111;
    public static final String CAM_PERMISSION_NAME[] = {android.Manifest.permission.CAMERA};
    public static final int GAL_PERMISSION_ACCESS_CODE = 222;
    public static final String GAL_PERMISSION_NAME[] = {android.Manifest.permission.READ_EXTERNAL_STORAGE};

    Button bt1;
    ImageView iv1;
    Button bt2;
    String choice[] = {"CAMERA", "GALLERY"};

    Bitmap bit = null;
    private int[] layouts;
    private BookPageAdapter myViewPagerAdapter;
    ViewPager viewPager;
    Timer timer;
    View view;
    RadioButton washfold,washiron,iron;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.indicator_image);
//        washfold = (RadioButton) findViewById(R.id.washfold_button);
//        washiron = (RadioButton) findViewById(R.id.washiron_button);
//        iron = (RadioButton) findViewById(R.id.iron_button);
//
//        iv1 = (ImageView) findViewById(R.id.iv1);
//        bt1 = (Button) findViewById(R.id.bt1);
//        bt2 =(Button)findViewById(R.id.bt2);

        layouts = new int[]{
                R.layout.wash_fold,
                R.layout.wash_iron,
                R.layout.iron};
        myViewPagerAdapter = new BookPageAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                viewPager.post(new Runnable(){

                    @Override
                    public void run() {
                        viewPager.setCurrentItem((viewPager.getCurrentItem()+1)%layouts.length);
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 3000, 3000);


//        bt2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });
//        if (bit == null) {
//            Toast.makeText(this, "Add Image First !!!", Toast.LENGTH_SHORT).show();
//        } else {
//            ByteArrayOutputStream bout = new ByteArrayOutputStream();
//            bit.compress(Bitmap.CompressFormat.JPEG, 100, bout);
//            byte img[] = bout.toByteArray();
//            String image = Base64.encodeToString(img, Base64.DEFAULT);
//        }
    }


    public class BookPageAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public BookPageAdapter() {
        }
        @Nullable
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//OR
            layoutInflater = LayoutInflater.from(getApplicationContext());
            //layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //view= View.inflate(getApplicationContext(), R.layout.activity_main, null);
              view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }



//    public void camera(View v) {
//
//        AlertDialog.Builder adb = new AlertDialog.Builder(this);
//        adb.setIcon(R.drawable.photo);
//        adb.setTitle(" Select One ");
//        adb.setItems(choice, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                switch (i) {
//                    case 0:
//                        int res = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA);
//                        if (res == PackageManager.PERMISSION_GRANTED) {
//                            Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            startActivityForResult(cam, CAM_REQ_CODE);
//                        } else {
//                            ActivityCompat.requestPermissions(MainActivity.this, CAM_PERMISSION_NAME, CAM_PERMISSION_ACCESS_CODE);
//                        }
//                        break;
//                    case 1:
//                        int res1 = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
//
//                        if (res1 == PackageManager.PERMISSION_GRANTED) {
//                            Intent gal = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                            startActivityForResult(gal, GAL_REQ_CODE);
//                        } else {
//                            ActivityCompat.requestPermissions(MainActivity.this, GAL_PERMISSION_NAME, GAL_PERMISSION_ACCESS_CODE);
//                        }
//
//                        break;
//                }
//            }
//        });
//        adb.create().show();
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case CAM_PERMISSION_ACCESS_CODE:
//                if (CAM_PERMISSION_NAME.equals(permissions[0]) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(cam, CAM_REQ_CODE);
//                }
//                break;
//
//            case GAL_PERMISSION_ACCESS_CODE:
//                if (GAL_PERMISSION_NAME.equals(permissions[0]) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Intent gal = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(gal, GAL_REQ_CODE);
//                }
//                break;
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case CAM_REQ_CODE:
//                if (resultCode == RESULT_OK) {
//                    Bundle b = data.getExtras();
//                    bit = (Bitmap) b.get("data");
//                    iv1.setImageBitmap(bit);
//                }
//                break;
//
//            case GAL_REQ_CODE:
//                if (resultCode == RESULT_OK) {
//                    Uri img = data.getData();
//                    try {
//                        bit = MediaStore.Images.Media.getBitmap(this.getContentResolver(), img);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    iv1.setImageBitmap(bit);
//                }
//                break;
//        }
//    }

}