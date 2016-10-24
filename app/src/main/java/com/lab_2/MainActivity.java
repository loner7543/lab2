package com.lab_2;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Intent NotificationIntent;
    private ListView MeetingsList;
    private Intent LaunchIntent;
    private Context context;
    private List<Meeting> Data;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = MainActivity.this;
        MeetingsList = (ListView) findViewById(R.id.meeting_list);
        Participant participant = new Participant("1","1");
        List<Participant> participants = new LinkedList<>();
        participants.add(participant);
        Meeting meeting = new Meeting("someName","someD","d1","d2",participants,"gold");
        Data = new LinkedList<>();
        Data.add(meeting);
        adapter = new MainAdapter(this,R.layout.list_item,Data);
        MeetingsList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onCreateMeat(View view){
        Participant participant = new Participant("122","122");
        List<Participant> participants = new LinkedList<>();
        participants.add(participant);
        Meeting Newmeeting = new Meeting("1","1","1","1",participants,"normal");
        Data.add(Newmeeting);
        //  adapter.add(Newmeeting);
        adapter.notifyDataSetChanged();
    }

    public void getAllMeat(View view){

    }


}
