package com.example.mansopresk05.imagecap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;



import java.io.ByteArrayOutputStream;
import java.io.IOException;



public class RegisterActivity extends Activity {
    Bitmap bit = null;
    private static final String TAG = "RegisterActivity";
    private static final int REQUEST_SIGNUP = 0;
    private ProgressDialog pDialog;

    RadioGroup rg_gender;

    ImageView mIVprofilePic;
    EditText mInputUsername, mInputEmail, mInputMobile, mInputPass, mInputConfirmPass, mInputName;
    RadioButton mRBMale, mRBFemale;
    public static final String REF_NAME = "RegisterValues";
    public static final int CAM_REQ_CODE=123;
    public static final int GAL_REQ_CODE=321;
    public static final int CAM_PERMISSION_ACCESS_CODE=111;
    public static final String CAM_PERMISSION_NAME[]= {android.Manifest.permission.CAMERA};
    public static final int GAL_PERMISSION_ACCESS_CODE=222;
    public static final String GAL_PERMISSION_NAME[]={android.Manifest.permission.READ_EXTERNAL_STORAGE};
    String choice[]={"CAMERA","GALLERY"};
    String name,mobile,password,image,email,username;
    String profil_gender;
    String regId = "asdf1234";
    private Button bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register);

        mIVprofilePic = (ImageView) findViewById(R.id.profilePic_iv);

        bt2 =(Button)findViewById(R.id.saveBtn);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(RegisterActivity.this, Second.class);
                intent.putExtra("picture", bit);
                startActivity(intent);

            }
        });

    }

    public void registerUser(View v) {

     if (bit == null) {
            Toast.makeText(this, "Add Image First !!!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bit.compress(Bitmap.CompressFormat.JPEG, 100, bout);
            byte img[] = bout.toByteArray();
            image = Base64.encodeToString(img, Base64.DEFAULT);



        }
    }
    public void takephoto(View v)
    {

        AlertDialog.Builder adb=new AlertDialog.Builder(this);
        adb.setIcon(R.mipmap.ic_launcher);
        adb.setTitle(" Select One ");
        adb.setItems(choice, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                switch (i)
                {
                    case 0:
                        int res= ContextCompat.checkSelfPermission(RegisterActivity.this, android.Manifest.permission.CAMERA);
                        if(res== PackageManager.PERMISSION_GRANTED)
                        {
                            Intent cam=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cam,CAM_REQ_CODE);
                        }
                        else
                        {
                            ActivityCompat.requestPermissions(RegisterActivity.this,CAM_PERMISSION_NAME,CAM_PERMISSION_ACCESS_CODE);
                        }
                        break;
                    case 1:
                        int res1=ContextCompat.checkSelfPermission(RegisterActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
                        if(res1==PackageManager.PERMISSION_GRANTED)
                        {
                            Intent gal=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(gal,GAL_REQ_CODE);
                        }
                        else
                        {
                            ActivityCompat.requestPermissions(RegisterActivity.this,GAL_PERMISSION_NAME,GAL_PERMISSION_ACCESS_CODE);
                        }

                        break;
                }
            }
        });
        adb.create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case CAM_PERMISSION_ACCESS_CODE:
                if(CAM_PERMISSION_NAME.equals(permissions[0]) && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    Intent cam=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cam,CAM_REQ_CODE);
                }
                break;

            case GAL_PERMISSION_ACCESS_CODE:
                if(GAL_PERMISSION_NAME.equals(permissions[0]) && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    Intent gal=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(gal,GAL_REQ_CODE);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAM_REQ_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle b = data.getExtras();
                    bit = (Bitmap) b.get("data");
                    mIVprofilePic.setImageBitmap(bit);
                }
                break;

            case GAL_REQ_CODE:
                if (resultCode == RESULT_OK) {
                    Uri img = data.getData();
                    try {
                        bit = MediaStore.Images.Media.getBitmap(this.getContentResolver(), img);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mIVprofilePic.setImageBitmap(bit);
                }
                break;
        }
    }


    private void showpDialog()
    {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private void hidepDialog()
    {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
