package com.lab_2.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lab_2.Fields;
import com.lab_2.MainActivity;
import com.lab_2.R;
import com.lab_2.domain.Meeting;
import com.lab_2.domain.Participant;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by alma0516 on 11/18/2016.
 */

//посылать интент своему классу

public class MyService extends IntentService
{
    public static final String LOG_TAG = "MY_SERVICE";
    public static final String ACTION_MYINTENTSERVICE = "com.meetingroom.RESPONSE";
    public static final String NETWORK = "NETWORK";
    public static final String MEETINGS = "MEETINGS";
    private List<Meeting> meetings;
    private MeetingListListenerTime meetingListener;
    private Intent NotificationIntent;

    public MyService(String name) {
        super(name);
    }

    public MyService(){
        super("");

    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Intent responseIntent = new Intent();

        ConnectivityManager connMan = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = connMan.getActiveNetworkInfo();
        if(ni!=null&&ni.isConnected()) {
            try
            {
                while (true) {

                    Thread.sleep(600000);
                    meetings = new LinkedList<>();
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                    meetingListener = new MeetingListListenerTime(db);
                    db.addValueEventListener(meetingListener);
                }
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }

        }
        else {
            responseIntent.setAction(ACTION_MYINTENTSERVICE);
            responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
            responseIntent.putExtra(NETWORK,"0");//сети нет
            responseIntent.putExtra(MEETINGS, (Serializable) meetings);
            sendBroadcast(responseIntent);
            stopSelf();
        }

    }

    public class MeetingListListenerTime implements ValueEventListener{
        private DatabaseReference Db;
        public MeetingListListenerTime(DatabaseReference ref)
        {
            this.Db = ref;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            try {
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
                        meeting.setParticipants(participants);
                    }
                    meetings.add(meeting);
                }
            }
            catch (Exception e)
            {
                Log.e("UpdateMeeting_E", e.getMessage());
            }
            Db.removeEventListener(this);
            onShowNotification();

            Intent responseIntent = new Intent();
            responseIntent.setAction(ACTION_MYINTENTSERVICE);
            responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
            responseIntent.putExtra(NETWORK, "1");
            responseIntent.putExtra(MEETINGS, (Serializable) meetings);
            sendBroadcast(responseIntent);
            stopSelf();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e("ERRRRRRR:", databaseError.getMessage());
        }
    }

    public void onShowNotification(){
        NotificationIntent = new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent  pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0, NotificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

       Resources resources = getApplicationContext().getResources();
        Notification.Builder builder = new Notification.Builder(getApplicationContext());

        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_action_about_white)
                .setTicker(resources.getString(R.string.Notification_title))
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle(resources.getString(R.string.from_date)) // Заголовок уведомления
                .setContentText(resources.getString(R.string.New_Meet)); // Текст уведомления
         Notification notification = builder.build();

        NotificationManager manager = (NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(new Random().nextInt(), notification);//если Id уведомления разные - то это разные уведомления*/
    }
}
