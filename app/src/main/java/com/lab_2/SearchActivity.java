package com.lab_2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lab_2.domain.Meeting;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class SearchActivity extends AppCompatActivity {
    private static final String KEY = "SearchLog";
    private EditText searchData;
    private Button startBtn;
    private ListView resultListView;
    private DatabaseReference mDatabase;
    private List<Meeting> result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_search);
        searchData = (EditText) findViewById(R.id.enter_desc);
        startBtn = (Button) findViewById(R.id.searchBtn);
        resultListView = (ListView) findViewById(R.id.res_list);
    }

    public void onStartSearch(View view){
        result = new LinkedList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();//все ок - подключает
        //mDatabase = new Firebase("https://meetingapp-2f339.firebaseio.com/meetingapp-2f339/");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Meeting meeting = new Meeting();
                meeting.setKey(dataSnapshot.getKey());
                meeting.setName((String) dataSnapshot.child(Fields.NAME).getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
