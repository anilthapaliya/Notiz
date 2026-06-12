package com.bca.notiz;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bca.notiz.utils.NotificationUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NotesActivity extends AppCompatActivity {

    ImageView imgProfile;
    FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notes);

        viewBinding();
        registerEvents();
        checkNotificationPermission();

        NotificationUtils.showAppUpdates(this, "Welcome", "Enjoy this app!");
    }

    private final int NOTIFICATION_CODE = 102;
    private void checkNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_CODE);
            }
        }
    }

    void viewBinding() {

        imgProfile = findViewById(R.id.imgProfile);
        btnAdd = findViewById(R.id.btnAdd);
    }

    void registerEvents() {

        imgProfile.setOnClickListener(v -> {
            startActivity(new Intent(NotesActivity.this, ProfileActivity.class));
        });

        btnAdd.setOnClickListener(v -> addNote());
    }

    void addNote() {

        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_add_note, null);
        dialog.setContentView(view);

        MaterialCardView imgClose = view.findViewById(R.id.imgClose);
        MaterialButton btnAdd = view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(v -> {
            NotificationUtils.showNotesUpdates(this, "Notes Added", "");
            dialog.dismiss();
        });
        imgClose.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

}