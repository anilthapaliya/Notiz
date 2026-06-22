package com.bca.notiz;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

public class ProfileActivity extends AppCompatActivity {

    ImageView imgProfile, imgCamera, imgBack;
    private final int CAMERA_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        viewBinding();
        registerEvents();
    }

    void viewBinding() {

        imgProfile = findViewById(R.id.imgProfile);
        imgCamera = findViewById(R.id.imgCamera);
        imgBack = findViewById(R.id.imgBack);
    }

    private ActivityResultLauncher<Intent> camLauncher;
    void registerEvents() {

        camLauncher = registerForActivityResult
                (new ActivityResultContracts.StartActivityForResult(),
                        result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String path = result.getData().getStringExtra("imagePath");
                        Bitmap bitmap = BitmapFactory.decodeFile(path);
                        Glide.with(this).load(bitmap)
                                .circleCrop()
                                .into(imgProfile);
                    }
                });

        imgCamera.setOnClickListener(v -> {
            // Check if the permission is granted.
            // If yes, show camera
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
                showCameraActivity();
            }
            else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                popUp();
            }
            else {
                // If no, request permission
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.CAMERA}, CAMERA_CODE);
            }
        });

        imgBack.setOnClickListener(v -> finish());
    }

    void popUp() {

        new AlertDialog.Builder(this)
                .setTitle("Camera Permission")
                .setMessage("This app needs camera permission to capture photos and upload on your profile.")
                .setPositiveButton("ASK PERMISSION", (d, w) -> {
                    ActivityCompat.requestPermissions(this,
                            new String[] {Manifest.permission.CAMERA}, CAMERA_CODE);
                })
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_CODE) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showCameraActivity();
                }
                else {
                    Toast.makeText(this, "Camera is needed for profile picture.",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    void showCameraActivity() {

        Intent intent = new Intent(ProfileActivity.this, CameraActivity.class);
        camLauncher.launch(intent);
    }

}