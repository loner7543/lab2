package com.lab_2.recivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lab_2.domain.Participant;

/**
 * Created by Александр on 05.12.2016.
 */
//Этот класс принимает интент и является глобальным системным слушателем. Ему прилетает интент оот аларм менеджера. Менеджер уже настроен на срабатывание раз в 10 мин
    //отсюда надо идти в сеть
public class AlarmManagerReciver extends BroadcastReceiver {
    private final static String BROADCAST_KEY = "AlarmManagerReciver";
    private final static String MY_ACTION="MyAction";
    private DatabaseReference databaseReference;
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        databaseReference = FirebaseDatabase.getInstance().getReference();//все ок - подключает
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d(BROADCAST_KEY, "какое то действие");
        Intent newI = new Intent(Intent.ACTION_TIME_TICK);
        Participant participant = new Participant("efff","efefef");
        newI.putExtra("asd","ded");
        newI.addCategory(Intent.CATEGORY_DEFAULT);
        context.sendBroadcast(newI);
    }
}
