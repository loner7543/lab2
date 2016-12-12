package com.lab_2.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Александр on 05.12.2016.
 */
//Этот класс принимает интент и является глобальным системным слушателем. Ему прилетает интент оот аларм менеджера. Менеджер уже настроен на срабатывание раз в 10 мин
    //отсюда надо идти в сеть
public class MyReciver extends BroadcastReceiver {
    private final static String BROADCAST_KEY = "MyReciver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(BROADCAST_KEY, "какое то действие");
    }
}
