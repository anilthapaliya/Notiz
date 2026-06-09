package com.bca.notiz;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.Fragment;

import com.bca.notiz.fragments.FeaturesFragment;
import com.bca.notiz.fragments.PrivacyFragment;
import com.bca.notiz.fragments.WelcomeFragment;
import com.bca.notiz.utils.Caching;
import com.google.android.material.button.MaterialButton;

public class OnBoardingActivity extends AppCompatActivity {

    MaterialButton btnPrevious, btnNext;
    private int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SplashScreen splash = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // layout inflation
        setContentView(R.layout.activity_on_boarding);

        decideRoutes();
        viewBinding();
        registerEvents();
        loadFragment(current);
    }

    void decideRoutes() {

        // check if the app is run for the first time or not.
        // if first time launch, show on boarding screen and mark the flag as false.
        // if not show main screen.
        boolean isFirstLaunch = Caching.isFirstLaunch(this);
        if (!isFirstLaunch) {
            Intent intent = new Intent(OnBoardingActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else Caching.setFirstLaunch(this, false);
    }

    void viewBinding() {

        // view binding
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
    }

    void registerEvents() {

        btnPrevious.setOnClickListener(v -> {
            if (current > 0) loadFragment(--current);
        });

        btnNext.setOnClickListener(v -> {
            loadFragment(++current);
        });
    }

    void loadFragment(int position) {

        Fragment fragment;
        if (position == 0) fragment = new WelcomeFragment();
        else if (position == 1) fragment = new FeaturesFragment();
        else if (position == 2) fragment = new PrivacyFragment();
        else {
            loadMain();
            return;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

    void loadMain() {

        Intent intent = new Intent(OnBoardingActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}