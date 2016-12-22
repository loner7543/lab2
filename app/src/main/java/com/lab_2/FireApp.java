package com.lab_2;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Александр on 21.12.2016.
 */

public class FireApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
