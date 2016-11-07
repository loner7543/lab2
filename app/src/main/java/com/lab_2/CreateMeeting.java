package com.lab_2;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateMeeting extends AppCompatActivity {
    private RelativeLayout MainLayout;
    private ArrayAdapter<CharSequence> ParAdapter;
    private DatabaseReference mDatabase;
    private Spinner spinner;
    private ListView ParticipantsListView;
    private ParticipantAdapter participantAdapter;
    private ScrollView scrollView;
    private EditText Name;
    private EditText Descrption;
    private EditText FromDate;
    private EditText ToDate;

    private String NameText;
    private String DescText;
    private String FromDateText;
    private String ToDateText;
    private String Type;
    private int i = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        MainLayout = (RelativeLayout) findViewById(R.id.activity_create_meeting);
        Name  = (EditText) findViewById(R.id.Name_Edit);
        Descrption = (EditText) findViewById(R.id.Desc_Edit);
        FromDate = (EditText) findViewById(R.id.FromDateEdit);
        ToDate = (EditText) findViewById(R.id.ToDateEdit);
        spinner = (Spinner) findViewById(R.id.Priority);
        ParticipantsListView = (ListView) findViewById(R.id.new_Participants);
        scrollView = (ScrollView) findViewById(R.id.newPar_Scroll);
        //spinner.setPrompt(res.getString(R.string.Priority_text));
        ParAdapter = ArrayAdapter.createFromResource(this,R.array.Priority_array, android.R.layout.simple_spinner_item);
        spinner.setAdapter(ParAdapter);
        mDatabase = FirebaseDatabase.getInstance().getReference();//все ок - подключает
    }

    public void onSendData(View view){
        NameText = Name.getText().toString();
        DescText = Descrption.getText().toString();
        FromDateText = FromDate.getText().toString();
        ToDateText = ToDate.getText().toString();
        Type = spinner.getSelectedItem().toString();
        mDatabase.child(Fields.MEET_NAME+i).child(Fields.NAME).setValue(NameText);
        mDatabase.child(Fields.MEET_NAME+i).child(Fields.DESCRIPTION).setValue(DescText);
        mDatabase.child(Fields.MEET_NAME+i).child(Fields.FROM_DATE).setValue(FromDateText);
        mDatabase.child(Fields.MEET_NAME+i).child(Fields.TO_DATE).setValue(ToDateText);
        mDatabase.child(Fields.MEET_NAME+i).child(Fields.TYPE).setValue(Type);
        String key = mDatabase.child("posts").push().getKey();

        int j = 1;
        //Participant post = new Participant("newFio","NewDesc");
        //Map<String, String> postValues = post.toMap();
        //mDatabase.child("Meet4").child(Fields.PARTICIPANTS).child(Fields.PARTICIPANT+j).push().setValue(postValues);//
       // childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        //mDatabase.updateChildren(childUpdates);
        //i++;
    }

    public void onAddNewParticipant(View view){
        EditText fio = new EditText(this);
        //scrollView.addView(fio);
        //MainLayout.addView(fio);
        fio.setText("New Item");
        //RelativeLayout.RIGHT_OF
    }
}
