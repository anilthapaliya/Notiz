package com.bca.notiz.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bca.notiz.utils.NotificationUtils;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationUtils.showAlarmNotification(context, "App Updating", "Please do not close the app while updating.");
    }

}
