package com.lab_2;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Александр on 24.10.2016.
 */

public class MainAdapter extends BaseAdapter {
    private static final String TAG = "MainAdapter";
    private Context ctx;
    private int pos;
    private  int LayResId;
    private List<Meeting> Data;
    private LayoutInflater inflater;
    public MainAdapter(Context context, int resource, List<Meeting> objects) {
        this.ctx =context;
        this.LayResId = resource;
        this.Data = objects;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Data.size();
    }

    @Override
    public Object getItem(int i) {
        return  Data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row==null){
                row = inflater.inflate(LayResId,parent,false);
               // row.setOnClickListener(this);
                Meeting currMeet = getMeeting(position);
                Log.d(TAG,"Curr item name:"+currMeet.getName());
                TextView meetName = (TextView) row.findViewById(R.id.meet_name_val);
                meetName.setText(currMeet.getName());

                TextView meetDesc = (TextView) row.findViewById(R.id.meet_desc_val);
                meetDesc.setText(currMeet.getDescription());

                TextView fromDate = (TextView) row.findViewById(R.id.fromDate_desc);
                fromDate.setText(currMeet.getFromDate());

                TextView toDate = (TextView) row.findViewById(R.id.to_date_desc);
                toDate.setText(currMeet.getToDate());

                TextView Type = (TextView) row.findViewById(R.id.Priority_Item_Desc);
                Type.setText(currMeet.getType());

                ListView parList = (ListView) row.findViewById(R.id.persons_list);
                ParticipantAdapter participantAdapter = new ParticipantAdapter(ctx,R.layout.partcipant_item,currMeet.getParticipants());
                parList.setAdapter(participantAdapter);

        }
        return row;
    }

  /*  @Override
    public void onClick(View view) {

        View v = view;
        v.setBackgroundColor(Color.GRAY);
    }*/

    public Meeting getMeeting(int Position){
        return (Meeting) getItem(Position);
    }
}
