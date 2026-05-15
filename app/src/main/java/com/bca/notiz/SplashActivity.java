package com.bca.notiz;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

public class SplashActivity extends AppCompatActivity {

    private boolean isLoading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SplashScreen splash = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        splash.setKeepOnScreenCondition(() -> {
            return isLoading;
        });
        loadingLogic();
    }

    private void loadingLogic() {

        isLoading = false;
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}