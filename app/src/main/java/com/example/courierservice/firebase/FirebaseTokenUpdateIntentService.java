package com.example.courierservice.firebase;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

public class FirebaseTokenUpdateIntentService extends IntentService {

    public  FirebaseTokenUpdateIntentService(){
        super("FirebaseTokenUpdateIntentService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String token = intent.getStringExtra("firebase_token");

        Log.d("DEBUG", token);

    }
}
