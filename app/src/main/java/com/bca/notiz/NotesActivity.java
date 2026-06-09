package com.bca.notiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NotesActivity extends AppCompatActivity {

    ImageView imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notes);

        viewBinding();
        registerEvents();
    }

    void viewBinding() {

        imgProfile = findViewById(R.id.imgProfile);
    }

    void registerEvents() {

        imgProfile.setOnClickListener(v -> {
            startActivity(new Intent(NotesActivity.this, ProfileActivity.class));
        });
    }

}