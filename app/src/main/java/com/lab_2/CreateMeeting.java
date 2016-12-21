package com.lab_2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lab_2.domain.Meeting;
import com.lab_2.domain.Participant;

import java.util.ArrayList;
import java.util.List;

public class CreateMeeting extends AppCompatActivity {
    private LinearLayout ParLayout;
    private ArrayAdapter<CharSequence> ParAdapter;
    private DatabaseReference mDatabase;
    private Spinner spinner;
    private EditText Name;
    private EditText Descrption;
    private EditText FromDate;
    private EditText ToDate;
    private EditText PartFio;
    private EditText PartPos;
    private List<Participant> newParticipants;//все созданные view
    private String NameText;
    private String DescText;
    private String FromDateText;
    private String ToDateText;
    private String Type;
    private String Fio;
    private String Position;
    private int i = 4;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        Name  = (EditText) findViewById(R.id.Name_Edit);
        Descrption = (EditText) findViewById(R.id.Desc_Edit);
        FromDate = (EditText) findViewById(R.id.FromDateEdit);
        ToDate = (EditText) findViewById(R.id.ToDateEdit);
        spinner = (Spinner) findViewById(R.id.Priority);
        PartFio = (EditText) findViewById(R.id.par_nameVal_new);
        PartPos = (EditText) findViewById(R.id.par_descVal_new);
        //spinner.setPrompt(res.getString(R.string.Priority_text));
        ParAdapter = ArrayAdapter.createFromResource(this,R.array.Priority_array, android.R.layout.simple_spinner_item);
        spinner.setAdapter(ParAdapter);
        newParticipants = new ArrayList<>();
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
        int j = 1;
        for (Participant participant:newParticipants){
            mDatabase.child(Fields.MEET_NAME+i).child(Fields.PARTICIPANTS).child(Fields.PARTICIPANT+j).child(Fields.FIO).setValue(participant.getFio());
            mDatabase.child(Fields.MEET_NAME+i).child(Fields.PARTICIPANTS).child(Fields.PARTICIPANT+j).child(Fields.POSITION).setValue(participant.getPosition());
            j++;
        }
        i++;
        j=0;
        intent = new Intent();
        Meeting newMeeting = new Meeting(NameText,DescText,FromDateText,ToDateText,null,Type);
        intent.putExtra("meeting",newMeeting);
        int pId = 1;
        for (Participant participant:newParticipants){
            intent.putExtra("part"+pId,participant);
            pId++;
        }
        intent.putExtra("count",newParticipants.size());
        setResult(RESULT_OK, intent);
        newParticipants.clear();
        finish();
    }

    public void onAddNewParticipant(View view){

        Fio = PartFio.getText().toString();
        Position = PartPos.getText().toString();
        Participant participant = new Participant(Fio,Position);
        newParticipants.add(participant);

        PartFio.setText("");
        PartPos.setText("");
    }

    public void onEnterFromDate(View view){
        ShowDialog(1);
    }

    public void onEnterToDate(View view){
        ShowDialog(2);
    }

    public void ShowDialog(final int action){
        LayoutInflater layoutInflater = LayoutInflater.from(CreateMeeting.this);
        View promptView = layoutInflater.inflate(R.layout.date_picker, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CreateMeeting.this);
        alertDialogBuilder.setView(promptView);

        final DatePicker dp = (DatePicker) promptView.findViewById(R.id.picker);
        alertDialogBuilder.setCancelable(false)
                .setNegativeButton("Ок",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (action==1){
                                    FromDate.setText(dp.getYear()+":"+dp.getMonth()+":"+dp.getDayOfMonth());
                                }
                                else {
                                    ToDate.setText(dp.getYear()+":"+dp.getMonth()+":"+dp.getDayOfMonth());
                                }
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
