package com.lab_2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lab_2.adapters.MeetingAdaper;
import com.lab_2.domain.Meeting;
import com.lab_2.domain.Participant;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements ValueEventListener {
    private static final String KEY = "SearchLog";
    private EditText searchData;
    private Button startBtn;
    private ListView resultListView;
    private DatabaseReference mDatabase;
    private List<Meeting> data;
    private List<Meeting> searchResult;
    private MeetingAdaper meetingAdaper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_search);
        searchData = (EditText) findViewById(R.id.enter_desc);
        startBtn = (Button) findViewById(R.id.searchBtn);
        startBtn.setText("Искать");
        resultListView = (ListView) findViewById(R.id.res_list);
        mDatabase = FirebaseDatabase.getInstance().getReference();//все ок - подключает
        mDatabase.addValueEventListener(this);
        data = new LinkedList<>();
        searchResult = new LinkedList<>();
    }

    public void onStartSearch(View view){
        try{
            for (Meeting meeting: data){
                if (meeting.getDescription().equals(searchData.getText().toString())){
                    searchResult.add(meeting);
                }
                else {continue;}
            }
            meetingAdaper = new MeetingAdaper(getApplicationContext(),R.layout.list_item,searchResult);
            resultListView.setAdapter(meetingAdaper);
        }
        catch (NullPointerException e){
            Toast.makeText(getApplicationContext(),"У встречи отсутствует описание",Toast.LENGTH_LONG);
        }



    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        try{
            Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
            Iterator<DataSnapshot> chIter = iterable.iterator();
            Iterable<DataSnapshot> CHIterable;
            List<Participant> participants = null;
            Iterator<DataSnapshot> CHIT;
            while (chIter.hasNext()) {
                Meeting meeting = new Meeting();
                DataSnapshot snapshot = chIter.next();
                meeting.setName((String) snapshot.child(Fields.NAME).getValue());
                meeting.setDescription((String) snapshot.child(Fields.DESCRIPTION).getValue());
                meeting.setFromDate((String) snapshot.child(Fields.FROM_DATE).getValue());
                meeting.setToDate((String) snapshot.child(Fields.TO_DATE).getValue());
                meeting.setType((String) snapshot.child(Fields.TYPE).getValue());
                if (snapshot.hasChild(Fields.PARTICIPANTS)){
                    DataSnapshot ps = snapshot.child("Participants");
                    CHIterable = ps.getChildren();
                    CHIT = CHIterable.iterator();
                    participants =  new LinkedList<>();
                    while (CHIT.hasNext()){
                        DataSnapshot psk = CHIT.next();
                        Participant participant = psk.getValue(Participant.class);
                        participants.add(participant);
                    }
                }
                meeting.setParticipants(participants);
                data.add(meeting);
            }
        }
        catch (Exception e){

        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.d(KEY,databaseError.getMessage());
    }
}
