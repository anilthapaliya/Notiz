package com.bca.notiz.utils;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.bca.notiz.R;

public class NotificationUtils {

    public static void showAppUpdates(Context context, String title, String message) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
            return;

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, Constants.CHANNEL_UPDATES)
                .setSmallIcon(R.drawable.notiz_logo)
                .setContentTitle(title)
                .setContentText(message);
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(1, notification.build());
    }

    public static void showNotesUpdates(Context context, String title, String message) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
            return;

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, Constants.CHANNEL_NOTES)
                .setSmallIcon(R.drawable.notiz_logo)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.features)));
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(2, notification.build());
    }

}
