package com.lab_2;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Александр on 24.10.2016.
 */

public class MainAdapter extends ArrayAdapter<Meeting> implements AdapterView.OnClickListener {
    private Context ctx;
    private  int LayResId;
    private List<Meeting> Data;
    private LayoutInflater inflater;
    public MainAdapter(Context context, int resource, List<Meeting> objects) {
        super(context, resource, objects);
        this.ctx =context;
        this.LayResId = resource;
        this.Data = objects;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row==null){
            for (Meeting meeting:Data){

                row = inflater.inflate(LayResId,parent,false);
               row.setOnClickListener(this);
                TextView meetName = (TextView) row.findViewById(R.id.meet_name_val);
                meetName.setText(meeting.getName());

                TextView meetDesc = (TextView) row.findViewById(R.id.meet_desc_val);
                meetDesc.setText(meeting.getType());

                TextView fromDate = (TextView) row.findViewById(R.id.fromDate_desc);
                fromDate.setText(meeting.getFromDate());

                TextView toDate = (TextView) row.findViewById(R.id.to_date_desc);
                toDate.setText(meeting.getToDate());

                ListView parList = (ListView) row.findViewById(R.id.persons_list);
                ParticipantAdapter participantAdapter = new ParticipantAdapter(ctx,R.layout.partcipant_item,meeting.getParticipants());
                parList.setAdapter(participantAdapter);
            }

        }
        return row;
    }

    @Override
    public void onClick(View view) {

        View v = view;
        v.setBackgroundColor(Color.GRAY);
    }
}
