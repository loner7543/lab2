package com.lab_2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainActivity extends ActionBarActivity implements ValueEventListener, ChildEventListener, AdapterView.OnItemLongClickListener {
    private static final String NAME = "Name";
    private static final String DESCrPTION = "Description";
    private static final String FrOM_DATE = "FromDate";
    private static final String TO_DATE = "ToDate";
    private static final String TYPE = "Type";
    private static final String PArTICIPANTS_CHILD_KEY = "Participants";
    private static final String TAG = "MainActivityLog";

    private DatabaseReference mDatabase;
    private Intent NotificationIntent;
    private PendingIntent pendingIntent;
    private NotificationManager manager;
    private Resources resources;
    private Notification notification;
    private long[] vibrate = new long[]{1000, 1000, 1000, 1000, 1000};
    private ListView MeetingsList;
    private Intent LaunchIntent;
    private Context context;
    private List<Meeting> Data;
    private MainAdapter adapter;
    private Random random;
    private AlertDialog.Builder ad;
    private String SearchText;
    private Query query;
    private  MyTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance().getReference();//все ок - подключает
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = getApplicationContext();
        MeetingsList = (ListView) findViewById(R.id.meeting_list);

        MeetingsList.setOnItemLongClickListener(this);
        task = new MyTask();
        random = new Random();
        mDatabase.addValueEventListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

  /*  @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return this.adapter.getItems();
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
            View promptView = layoutInflater.inflate(R.layout.serch_dialog, null);
            ad = new AlertDialog.Builder(MainActivity.this);
            ad.setView(promptView);
            final EditText editText = (EditText) promptView.findViewById(R.id.FindMeet);
            ad.setCancelable(false)
                    .setPositiveButton(R.string.find_btn, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            SearchText = editText.getText().toString();
                            query = mDatabase.equalTo(SearchText);
                        }
                    })
                    .setNegativeButton(R.string.btn_cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = ad.create();
            alert.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onCreateMeat(View view) {
        LaunchIntent = new Intent(this, CreateMeeting.class);
        startActivity(LaunchIntent);
    }

    public void getAllMeat(View view) {
        //mDatabase.child("MyObj").child("name").setValue("1");//создет новый узел и пишет данные
       // mDatabase.child("MyObj").child("desc").setValue("1");//создет новый узел и пишет данные
        mDatabase.child("NewChildBundle").child("pat").setValue("12");//создет новый узел и пишет данные
    }

    public void exportToCSV(View view) {

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {// вызывается при привязке данных и каждый раз когда данные меняются
        Log.d(TAG,"Data read start");
        List<Meeting> allMeatings = new LinkedList<>();
        List<Participant> participants = null;
        Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
        Iterator<DataSnapshot> chIter = iterable.iterator();

        Iterable<DataSnapshot> CHIterable;
        Iterator<DataSnapshot> CHIT;

        while (chIter.hasNext()){
            DataSnapshot snapshot = chIter.next();
            Meeting meeting = new Meeting();
            meeting.setName((String) snapshot.child(NAME).getValue());
            meeting.setDescription((String) snapshot.child(DESCrPTION).getValue());
            meeting.setFromDate((String) snapshot.child(FrOM_DATE).getValue());
            meeting.setToDate((String) snapshot.child(TO_DATE).getValue());
            meeting.setType((String) snapshot.child(TYPE).getValue());
            if (snapshot.hasChild(PArTICIPANTS_CHILD_KEY)){
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
            allMeatings.add(meeting);
        }
        this.Data = allMeatings;
        adapter = new MainAdapter(this, R.layout.list_item, Data);
        MeetingsList.setAdapter(adapter);
        adapter.notifyDataSetChanged();// не меняет
        Log.d(TAG,"Data read sucs");
        onShowNotification();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.d(TAG,"loadPost:onCancelled", databaseError.toException());
    }

    public void onShowNotification(){
        /*Participant participant = new Participant("122","122");
        List<Participant> participants = new LinkedList<>();
        participants.add(participant);
        Meeting Newmeeting = new Meeting("1","1","1","1",participants,"normal");
        Data.add(Newmeeting);
        //  adapter.add(Newmeeting);
        adapter.notifyDataSetChanged();*/
        NotificationIntent = new Intent(context,MainActivity.class);
        pendingIntent = PendingIntent.getActivity(context,
                0, NotificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        resources = context.getResources();
        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_action_about_white)
                .setTicker(resources.getString(R.string.Notification_title))
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle(resources.getString(R.string.from_date)) // Заголовок уведомления
                .setContentText(resources.getString(R.string.New_Meet)); // Текст уведомления
         notification = builder.build();
        notification.vibrate = vibrate;

         manager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(random.nextInt(), notification);//если Id уведомления разные - то это разные уведомления*/
    }

    //методы для работы со списком участников
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        int f = 2;
        return false;
    }
}
