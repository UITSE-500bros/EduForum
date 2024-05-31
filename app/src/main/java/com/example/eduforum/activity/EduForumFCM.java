package com.example.eduforum.activity;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.eduforum.R;
import com.example.eduforum.activity.ui.auth.LoginActivity;
import com.example.eduforum.activity.util.FlagsList;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class EduForumFCM extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(FlagsList.DEBUG_FCM, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
//        sendRegistrationToServer(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (shouldShowNotifications()) {
            // Handle your notification display here
            if (!remoteMessage.getData().isEmpty()) {
                String title = remoteMessage.getData().get("title");
                String body = remoteMessage.getData().get("body");
                // TODO: Extension / The payload include communityID, postID, find ways to intent directly to the source of the notification
                sendNotification(title, body);
            }
        } else {
            Log.d(FlagsList.DEBUG_FCM, "Notifications are disabled by the user.");
        }
    }

    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);


        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.eduforumlogo)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(0, builder.build()); // ID of notification
    }

    private boolean shouldShowNotifications() {
        SharedPreferences prefs = getSharedPreferences(FlagsList.PREF_FILE_NAME, MODE_PRIVATE);
        return prefs.getBoolean(FlagsList.PREF_FILE_NAME, true);
    }
}
