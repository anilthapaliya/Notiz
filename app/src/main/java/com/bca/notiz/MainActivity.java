package com.bca.notiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private boolean isLoading = true;
    private MaterialButton btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle b) {

        //SplashScreen splash = SplashScreen.installSplashScreen(this);

        super.onCreate(b);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize views
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        registerEvents();
        /*splash.setKeepOnScreenCondition(() -> {
            return isLoading;
        });*/
        Log.i("MainActivity", "Activity Created ..");
        if (b != null) {
            String user = b.getString("username");
            Log.w("MainActivity", "username: " + user);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("username", "user@sth.com");
        outState.putString("password", "some_pwd");
    }

    private void registerEvents() {

        btnLogin.setOnClickListener((v) -> {
            // show login activity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        btnRegister.setOnClickListener(view -> {
            // Show register activity using explicit intent.
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w("MainActivity", "Activity Started ..");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("MainActivity", "Activity Resumed ..");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("MainActivity", "Activity Paused ..");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.wtf("MainActivity", "Activity Stopped ..");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "Activity Destroyed ..");
    }

}
