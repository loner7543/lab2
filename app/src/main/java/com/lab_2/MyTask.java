package com.lab_2;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by Александр on 24.10.2016.
 */

public class MyTask extends TimerTask{
    private static final String LOG_KEY= "TimerTask";
    private List<Meeting> NewData;
    private DatabaseReference databaseReference;

    public  MyTask(){
        if(databaseReference!=null){
            databaseReference = FirebaseDatabase.getInstance().getReference();//все ок - подключает
        }

    }

    @Override
    public void run() {
        Log.d(LOG_KEY,"TimerTask начал свое выполнение в:" + new Date());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(LOG_KEY,"TimerTask закончил свое выполнение в:" + new Date());
    }
}
