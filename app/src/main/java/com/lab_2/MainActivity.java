package com.lab_2;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lab_2.adapters.MeetingAdaper;
import com.lab_2.domain.Meeting;
import com.lab_2.domain.Participant;
import com.lab_2.recivers.AlarmManagerReciver;
import com.lab_2.service.MyService;
import com.lab_2.service.NetworkService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

//BroadcastResiver он будет слушать системеык вызовы и стартовать до старта самой активити
//Аларм менеджер пинимает интент с твоим классом интент сервиса и каждые 10 мин шлет интент интент сервису
public class MainActivity extends ActionBarActivity implements  ChildEventListener, AdapterView.OnItemClickListener {
    public static final int REQUEST_CODE_REFRESH = 1;
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
    private MeetingAdaper adapter;
    private Random random;
    private AlertDialog.Builder ad;

    private AlarmManager alarmManager;
    private Intent alarmIntent;
    private PendingIntent alarmPendingIntent;

    private Intent networkIntent;
    private PendingIntent networkPendingIntent;
    private AlarmManager networkManager;

    private Calendar calendar;
    private Date currentDate;
    private String strDate;
    private DateFormat dfISO;

    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        mDatabase = FirebaseDatabase.getInstance().getReference();//все ок - подключает
        MeetingsList = (ListView) findViewById(R.id.meeting_list);
        dfISO = new SimpleDateFormat("dd.MM.yyyy");
        Data = new LinkedList<Meeting>();
        calendar = Calendar.getInstance();
        currentDate = calendar.getTime();
        strDate = dfISO.format(currentDate);
        adapter = new MeetingAdaper(this, R.layout.list_item, Data);
        MeetingsList.setAdapter(adapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = getApplicationContext();
        MeetingsList.setOnItemClickListener(this);

        random = new Random();
        mDatabase.addChildEventListener(this);

        alarmIntent = new Intent(this, AlarmManagerReciver.class);
        alarmPendingIntent = PendingIntent.getBroadcast(this,0,alarmIntent,0);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,50000,50000,alarmPendingIntent);// повторяет периодически действие направляя интент слушателю
       /* broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action  = intent.getAction();
                if(action.equals(Intent.ACTION_TIME_TICK)){
                    String data = intent.getExtras().getString("asd");
                    String d = "";
                }
            }
        };

        IntentFilter intFilt = new IntentFilter(Intent.ACTION_TIME_TICK);
        // регистрируем (включаем) BroadcastReceiver
        registerReceiver(broadcastReceiver, intFilt);*/

        //Пробуем еще раз
        OnUpdateReciver meetingListBroadcast = new OnUpdateReciver();
        IntentFilter intentFilterTime = new IntentFilter(
                MyService.ACTION_MYINTENTSERVICE);
        intentFilterTime.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(meetingListBroadcast, intentFilterTime);


        updateTimeMeetingList();

        networkIntent =  new Intent(this,NetworkService.class);
        networkPendingIntent = PendingIntent.getBroadcast(this,0,networkIntent,0);
        networkManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //networkManager.setRepeating(AlarmManager.RTC_WAKEUP,2000,1000,networkPendingIntent);// та же штука для контроля сети
        //можно не делать, но метод проверяющий не отвалилось ли чего придется вызывать перед каждым походом в сеть
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,SearchActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onCreateMeat(View view) {
        LaunchIntent = new Intent(this, CreateMeeting.class);
        startActivityForResult(LaunchIntent,REQUEST_CODE_REFRESH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG,"requestCode  "+requestCode+"resultCode  "+resultCode);
        if (resultCode==RESULT_OK){
            adapter.notifyDataSetChanged();
            onShowNotification("Добавлена встреча");
        }
    }

    public void getAllMeat(View view) {

    }

    public void exportToCSV(View view) throws IOException {
        OpenFileDialog fileDialog = new OpenFileDialog(this,Data);
        fileDialog.show();
    }

    public void onDeleteMeet(View view){
       Meeting meeting = adapter.getMeetingItemByName();
        mDatabase.child(meeting.getName()).removeValue();
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.d(TAG,"loadPost:onCancelled", databaseError.toException());
    }

    public void onShowNotification(String message){
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
                .setContentText(message); // Текст уведомления
         notification = builder.build();
        notification.vibrate = vibrate;

         manager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(random.nextInt(), notification);//если Id уведомления разные - то это разные уведомления*/
    }

    //методы для работы со списком участников
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        try{
            List<Participant> participants = null;
            Iterable<DataSnapshot> CHIterable;
            Iterator<DataSnapshot> CHIT;
            Meeting meeting = new Meeting();
            meeting.setKey(dataSnapshot.getKey());
            meeting.setName((String) dataSnapshot.child(Fields.NAME).getValue());
            meeting.setDescription((String) dataSnapshot.child(Fields.DESCRIPTION).getValue());
            meeting.setFromDate((String) dataSnapshot.child(Fields.FROM_DATE).getValue());
            meeting.setToDate((String) dataSnapshot.child(Fields.TO_DATE).getValue());
            meeting.setType((String) dataSnapshot.child(Fields.TYPE).getValue());
            String checked = (String) dataSnapshot.child(Fields.IS_GOING).getValue();
        /*if (checked.equals(Fields.YES)){
            meeting.setGoing(true);
        }
        else meeting.setGoing(false);*/
            if (dataSnapshot.hasChild(PArTICIPANTS_CHILD_KEY)) {
                DataSnapshot ps = dataSnapshot.child(Fields.PARTICIPANTS);
                CHIterable = ps.getChildren();
                CHIT = CHIterable.iterator();
                participants = new LinkedList<>();
                while (CHIT.hasNext()) {
                    DataSnapshot psk = CHIT.next();
                    Participant participant = psk.getValue(Participant.class);
                    participants.add(participant);
                }
            }
            meeting.setParticipants(participants);
         //   if (meeting.getFromDate().equals(strDate))
          //  {
                Data.add(meeting);
           // }
            adapter.notifyDataSetChanged();
            Log.d(TAG,"Data read sucs");
        }
        catch (NullPointerException e){
            for (StackTraceElement stackTraceElement:e.getStackTrace()){
                Log.d(TAG,stackTraceElement.getMethodName());
            }
        Log.d(TAG,e.getMessage());
        }

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Log.d(TAG,"On child changed");
        Meeting selectedMeet = null;
        for (Meeting m:Data){
            if (m.getKey().equals(dataSnapshot.getKey())){
                selectedMeet = m;
            }
        }
        String go;
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        Log.d(TAG,"Data size  "+Data.size());
        Meeting meeting = new Meeting();
        Meeting removed = null;
        meeting.setKey(dataSnapshot.getKey());
        for (Meeting d:Data){
            if (d.getKey().equals(meeting.getKey())){
                removed = d;
            }
        }
        Data.remove(removed);
        adapter.notifyDataSetChanged();
        Log.d(TAG,"Data size  "+Data.size());
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        Log.d(TAG,"On child moved method");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String s = "efefefefe";
    }

    public void updateTimeMeetingList()
    {
        Intent intent = new Intent(getApplicationContext(), MyService.class);
        startService(intent);
    }

    public class OnUpdateReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getStringExtra("NETWORK").equals("1")) {
                ArrayList<Meeting> data = (ArrayList<Meeting>) intent.getSerializableExtra(MyService.MEETINGS);
                LinkedList<Meeting> linked = new LinkedList<>();
                for (Meeting meeting:data){
                    linked.add(meeting);
                }
                MeetingAdaper adapter = new MeetingAdaper(getApplicationContext(),R.layout.list_item,linked);
                MeetingsList.setAdapter(adapter);
                Toast.makeText(getApplicationContext(), "Данные получены", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Сеть недоступна", Toast.LENGTH_LONG).show();
            }
        }
    }
}
