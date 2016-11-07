package com.lab_2;

import android.os.AsyncTask;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Александр on 24.10.2016.
 */

public class MyTask extends AsyncTask<Void,Void,List<Meeting>> implements ValueEventListener {//<Params, Progress, Result>
    private List<Meeting> NewData;
    private DatabaseReference databaseReference;

    public  MyTask(){
        if(databaseReference!=null){
            databaseReference = FirebaseDatabase.getInstance().getReference();//все ок - подключает
        }

    }
    @Override
    protected List<Meeting> doInBackground(Void... voids) {
        return null;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
