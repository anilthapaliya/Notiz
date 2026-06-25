package com.bca.notiz.utils;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.bca.notiz.MainActivity;
import com.bca.notiz.NotesActivity;
import com.bca.notiz.ProfileActivity;
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
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.baseline_add))
                .setContentTitle(title)
                .setContentText(message);
                /*.setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.features)));*/
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(2, notification.build());
    }

    public static void showNotification(Context context, String title, String message) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
            return;

        Intent intent = new Intent(context, MainActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent =  PendingIntent.getActivity(context, 101, intent,
        PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, Constants.CHANNEL_UPDATES)
                .setSmallIcon(R.drawable.notiz_logo)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .addAction(1, "Goto Profile", pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(1, notification.build());
    }

    public static void showAlarmNotification(Context context, String title, String message) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
            return;

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, Constants.CHANNEL_UPDATES)
                .setSmallIcon(R.drawable.alarm)
                .setContentTitle(title)
                .setContentText(message);
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(3, notification.build());
    }

}
