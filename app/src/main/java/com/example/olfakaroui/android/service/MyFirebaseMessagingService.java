package com.example.olfakaroui.android.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UI.MainActivity;
import com.example.olfakaroui.android.UI.MyProfileFragment;
import com.example.olfakaroui.android.UI.events.EventDetailActivity;
import com.example.olfakaroui.android.UI.users.UserProfileActivity;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;
import java.util.Map;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FirebaseMessagingServic";
    public static final String EVENT_CHANNEL_ID = "event_notif_id";
    public static final String EVENT_CHANNEL_NAME = "event_notification";
    public static final String FOLLOW_CHANNEL_ID = "follow_notif_id";
    public static final String FOLLOW_CHANNEL_NAME = "follow_notification";

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        final Map<String, String> data = remoteMessage.getData();
        if (Integer.valueOf(data.get("notif_type")) == 1) {

            Intent intent = new Intent(MyFirebaseMessagingService.this, UserProfileActivity.class);
            intent.putExtra("user", Integer.valueOf(data.get("notif_id")));
            Log.d("trah 1", Integer.valueOf(data.get("notif_id"))+ " ");
            sendNotification(FOLLOW_CHANNEL_ID, FOLLOW_CHANNEL_NAME, data.get("title"), data.get("body"), intent);
        }
        if (Integer.valueOf(data.get("notif_type")) == 2) {

            Intent intent = new Intent(MyFirebaseMessagingService.this, EventDetailActivity.class);
            //intent.putExtra(MainActivity.WHICH_FRAGMENT, data.get("notif_id"));
            sendNotification(EVENT_CHANNEL_ID, EVENT_CHANNEL_NAME, data.get("title"), data.get("body"), intent);
        }
    }


    @Override
    public void onDeletedMessages() {

    }

    private void sendNotification(String channelId, String channelName, String title, String messageBody, Intent actionIntent) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat
                .Builder(this, channelId)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setSound(defaultSoundUri)
                .setVibrate(new long[]{0, 100, 200, 100});

        if (actionIntent != null) {
            builder.setContentIntent(PendingIntent.getActivity(this, 0, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Log.d(TAG, "sendNotification: onMessageReceived >26");
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        } else {
            Log.d(TAG, "sendNotification: onMessageReceived <26");
            builder.setPriority(NotificationManager.IMPORTANCE_HIGH);
        }

        notificationManager.notify((int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE), builder.build());
    }
}