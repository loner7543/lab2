package com.lab_2.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Александр on 02.12.2016.
 */

public class MyIntentService extends IntentService {
    private static final String LOG_KEY  ="MyIntentService";
    public MyIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_KEY,"Intent service created");
    }
}
