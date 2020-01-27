//作成者：金在愚
package com.example.a2170188.navigationdrawractivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import java.util.ArrayList;
import java.io.File;

import android.graphics.*;
import android.os.*;

import android.util.Log;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CameraMainActivity extends AppCompatActivity {

    private Boolean isPermission = true;
    private static final int CAMERA_REQUEST = 4321;

    private ImageView imageView;

    private static final String TAG = "aaaaaaaaaaaaaaaaaaaaa";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cameramain);

        tedPermission();
        this.imageView = (ImageView)this.findViewById(R.id.imageView1);

        findViewById(R.id.btnGetImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity(GetImageActivity.class);
            }
        });

        findViewById(R.id.btnGetCropImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity(GetCropImageActivity.class);
            }
        });
    }

    private void tedPermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
                isPermission = true;

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
                isPermission = false;
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }

    private void goToActivity(Class activityClass) {
        if(isPermission) {
            Intent intent = new Intent(this, activityClass);
            startActivity(intent);
        } else {
            Toast.makeText(this, getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");

            imageView.setImageBitmap(photo);

            File picture = (new File(Environment.getExternalStorageDirectory() + "/dcim/camera/").listFiles())[0];

            String filePath = picture.getPath();

            Log.d(TAG, "사진파일경로:" + filePath);

        }

    }


}
