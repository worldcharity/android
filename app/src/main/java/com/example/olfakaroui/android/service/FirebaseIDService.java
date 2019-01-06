package com.example.olfakaroui.android.service;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

public class FirebaseIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIDService";

    User user = new User();
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.getLogin(user);
        FirebaseMessaging.getInstance().subscribeToTopic("user_"+user.getId());
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
        SessionManager.setToken(refreshedToken, getApplicationContext());


    }

    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
    }

}

