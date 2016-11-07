package com.lab_2;

import android.content.Context;
import android.widget.Toast;
import java.util.TimerTask;

/**
 * Created by Александр on 07.11.2016.
 */

public class MyTimerTask extends TimerTask {
    int i = 0;

    public  MyTimerTask(){
    }
    @Override
    public void run() {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                i++;
            }
        });
    }

    private void runOnUiThread(Runnable runnable) {
    }

}

