package com.lab_2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainActivity extends ActionBarActivity {
    private Intent NotificationIntent;
    private long[] vibrate = new long[]{1000, 1000, 1000, 1000, 1000};
    private ListView MeetingsList;
    private Intent LaunchIntent;
    private Context context;
    private List<Meeting> Data;
    private MainAdapter adapter;
    private Random random;
    private AlertDialog.Builder ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = getApplicationContext();
        MeetingsList = (ListView) findViewById(R.id.meeting_list);
        Participant participant = new Participant("1", "1");
        List<Participant> participants = new LinkedList<>();
        participants.add(participant);
        Meeting meeting = new Meeting("someName", "someD", "d1", "d2", participants, "gold");
        Data = new LinkedList<>();
        Data.add(meeting);
        adapter = new MainAdapter(this, R.layout.list_item, Data);
        MeetingsList.setAdapter(adapter);
        random = new Random();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
                            //SoundValue.setText(editText.getText());
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
       /* Participant participant = new Participant("122","122");
        List<Participant> participants = new LinkedList<>();
        participants.add(participant);
        Meeting Newmeeting = new Meeting("1","1","1","1",participants,"normal");
        Data.add(Newmeeting);
        //  adapter.add(Newmeeting);
        adapter.notifyDataSetChanged();
        NotificationIntent = new Intent(context,MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, NotificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Resources res = context.getResources();
        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_action_about_white)
                .setTicker(res.getString(R.string.Notification_title))
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle(res.getString(R.string.from_date)) // Заголовок уведомления
                .setContentText(res.getString(R.string.New_Meet)); // Текст уведомления
        Notification notification = builder.build();
        notification.vibrate = vibrate;

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(random.nextInt(), notification);//если Id уведомления разные - то это разные уведомления*/
    }

    public void getAllMeat(View view) {

    }

    public void exportToCSV(View view) {

    }
}
