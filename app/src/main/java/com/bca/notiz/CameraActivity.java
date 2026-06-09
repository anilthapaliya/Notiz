package com.bca.notiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;

public class CameraActivity extends AppCompatActivity {

    PreviewView cameraPreview;
    ImageView imgClose, imgClick;
    ImageCapture imageCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_camera);

        viewBinding();
        registerEvents();
        startCamera();
    }

    void viewBinding() {

        imgClose = findViewById(R.id.imgClose);
        cameraPreview = findViewById(R.id.cameraPreview);
        imgClick = findViewById(R.id.imgClick);
    }

    void registerEvents() {

        imgClose.setOnClickListener(v -> finish());

        imgClick.setOnClickListener(v -> takePhoto());
    }

    void startCamera() {

        ListenableFuture<ProcessCameraProvider> future =
                ProcessCameraProvider.getInstance(this);
        future.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = future.get();
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(cameraPreview.getSurfaceProvider());

                imageCapture = new ImageCapture.Builder()
                        .build();

                CameraSelector selector =
                        CameraSelector.DEFAULT_BACK_CAMERA;
                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, selector,
                        preview, imageCapture);
            } catch (Exception e) {
                Toast.makeText(this, "Could not initialize camera.", Toast.LENGTH_LONG).show();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void takePhoto() {

        File file = new File(getExternalFilesDir(null), System.currentTimeMillis() + ".jpg");
        ImageCapture.OutputFileOptions options =
                new ImageCapture.OutputFileOptions.Builder(file)
                        .build();

        imageCapture.takePicture(
                options,
                ContextCompat.getMainExecutor(this),

                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(
                            @NonNull ImageCapture.OutputFileResults output) {
                        Intent intent = new Intent();
                        intent.putExtra("imagePath", file.getAbsolutePath());
                        setResult(RESULT_OK, intent);

                        finish();
                    }

                    @Override
                    public void onError(
                            @NonNull ImageCaptureException exception) {
                        Toast.makeText(CameraActivity.this, "Capture failed", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

}