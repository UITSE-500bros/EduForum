package com.example.eduforum.activity;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.eduforum.activity.util.FlagsList;
import com.google.firebase.messaging.FirebaseMessagingService;

public class EduForumFCM extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(FlagsList.DEBUG_FCM, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
//        sendRegistrationToServer(token);
    }
}
