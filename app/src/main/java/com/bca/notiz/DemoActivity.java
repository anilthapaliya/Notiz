package com.bca.notiz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_demo);

        registerEvents();
    }

    private void registerEvents() {

        // View Binding
        Toolbar toolbar = findViewById(R.id.toolbar);
        CardView cardView1 = findViewById(R.id.cardView1);
        CardView cardView2 = findViewById(R.id.cardView2);
        CardView cardView3 = findViewById(R.id.cardView3);
        CardView cardView4 = findViewById(R.id.cardView4);
        CardView cardView5 = findViewById(R.id.cardView5);
        CardView cardView6 = findViewById(R.id.cardView6);

        String email = getIntent().getStringExtra("user_email");
        if (email != null && !email.isEmpty()) {
            toolbar.setTitle(email);
        }

        cardView1.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_SENDTO);
            i.setData(Uri.parse("smsto:9876543219"));
            i.putExtra("sms_body", "Hello, I love you so much, please accept gara na ke..");
            startActivity(i);
        });

        cardView2.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_DIAL);
            i.setData(Uri.parse("tel:9876543219"));
            startActivity(i);
        });

        cardView3.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://google.com"));
            startActivity(i);
        });

        cardView4.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("geo:25.44,87.98"));
            startActivity(i);
        });

        cardView5.setOnClickListener(v -> {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(i);
        });

        cardView6.setOnClickListener(v -> {
            Intent i = new Intent(Settings.ACTION_WIFI_SETTINGS);
            startActivity(i);
        });
    }

}