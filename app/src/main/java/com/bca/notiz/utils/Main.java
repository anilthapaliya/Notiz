package com.bca.notiz.utils;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class Main extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        createChannels();
    }

    private void createChannels() {

        // if the device is greater than or equal to Oreo.
        NotificationChannel channelUpdates = new NotificationChannel(Constants.CHANNEL_UPDATES,
                Constants.CHANNEL_UPDATES_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        NotificationChannel channelNotes = new NotificationChannel(Constants.CHANNEL_NOTES,
                Constants.CHANNEL_NOTES_NAME, NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channelUpdates);
        manager.createNotificationChannel(channelNotes);
    }

}
