package com.bca.notiz;

import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
        /*new Handler().postDelayed(() -> {
            // download something..
            // initialize something ..
            // routing logic
            isLoading = false;
        }, 5000);*/
    }

}