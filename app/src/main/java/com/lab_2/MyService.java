package com.lab_2;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by alma0516 on 11/18/2016.
 */

//посылать интент своему классу

public class MyService extends IntentService
{
    public static final String LOG_TAG = "MY_SERVICE";
    private Timer timer;
    private TimerTask task;

    public MyService(String name) {
        super(name);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind");
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public void onCreate() {
        Log.d(LOG_TAG, "onCreate");
        timer = new Timer(true);
        task = new MyTask();
        super.onCreate();
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");
        someTask();
        return super.onStartCommand(intent, flags, startId);
    }

    void someTask(){
        new Thread(new Runnable() {
            public void run() {
                Log.d(LOG_TAG,"Timer task STARTED");
                timer.scheduleAtFixedRate(task, 0, 10*1000);
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /*for (int i = 1; i<=5; i++) {
                    Log.d(LOG_TAG, "i = " + i);
                    Log.d(LOG_TAG,"onTaskMethod");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }*/
                //stopSelf();//грохает сам себя и вызывает дестрой
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        Log.d(LOG_TAG,"On destroy method");
        super.onDestroy();
        Log.d(LOG_TAG,"Сервис уничтожен");
    }
}
