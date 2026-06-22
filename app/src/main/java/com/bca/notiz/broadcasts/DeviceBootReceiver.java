package com.bca.notiz.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bca.notiz.utils.NotificationUtils;

public class DeviceBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            /*NotificationUtils.showAppUpdates(context, "Rebooted",
                    "The device has been rebooted.");*/
        }
    }

}
