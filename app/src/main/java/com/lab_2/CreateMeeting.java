package com.lab_2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateMeeting extends AppCompatActivity {
    private ArrayAdapter<CharSequence> ParAdapter;
    private DatabaseReference mDatabase;
    private Spinner spinner;
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
        Name  = (EditText) findViewById(R.id.Name_Edit);
        Descrption = (EditText) findViewById(R.id.Desc_Edit);
        FromDate = (EditText) findViewById(R.id.FromDateEdit);
        ToDate = (EditText) findViewById(R.id.ToDateEdit);
        spinner = (Spinner) findViewById(R.id.Priority);
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
        //i++;
        ScrollView scrollView = new ScrollView(this);
    }
}
