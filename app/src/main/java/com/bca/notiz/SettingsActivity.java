package com.bca.notiz;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bca.notiz.broadcasts.AlarmReceiver;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

public class SettingsActivity extends AppCompatActivity {

    ImageView imgBack;
    TextView tvCheck, tvClear, tvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        viewBinding();
        registerEvents();
    }

    private void viewBinding() {

        imgBack = findViewById(R.id.imgBack);
        tvCheck = findViewById(R.id.tvCheck);
        tvClear = findViewById(R.id.tvClear);
        tvLogout = findViewById(R.id.tvLogout);
    }

    private void registerEvents() {

        imgBack.setOnClickListener(v -> finish());

        tvCheck.setOnClickListener(v -> {
            checkForUpdate();
        });
    }

    void checkForUpdate() {

        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_check_updates, null);
        dialog.setContentView(view);

        MaterialCardView imgClose = view.findViewById(R.id.imgClose);
        MaterialButton btnSchedule = view.findViewById(R.id.btnSchedule);
        TextInputEditText etInterval = view.findViewById(R.id.etInterval);

        btnSchedule.setOnClickListener(v -> {
            String value = etInterval.getText().toString();
            int interval;
            try {
                interval = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                interval = 30; // seconds
            }

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && alarmManager.canScheduleExactAlarms()) {
                long triggerTime = System.currentTimeMillis() + interval * 1000L;
                Intent intent = new Intent(this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 123,
                        intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
            }
            else {
                startActivity(new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM));
            }
            dialog.dismiss();
        });
        imgClose.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

}