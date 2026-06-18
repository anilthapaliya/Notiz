package com.bca.notiz.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.bca.notiz.utils.NotificationUtils;

public class AirplaneModeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())) {
            boolean isModeOn = intent.getBooleanExtra("state", false);

            if (isModeOn) {
                NotificationUtils.showAppUpdates(context, "Airplane Mode", "Airplane mode is ON.");
            }
            else {
                Toast.makeText(context, "Airplane mode is now off", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
