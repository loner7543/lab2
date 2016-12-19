package com.lab_2.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lab_2.R;
import com.lab_2.domain.Meeting;
import com.lab_2.domain.Participant;

import java.util.List;

/**
 * Created by Александр on 24.10.2016.
 */

public class MeetingAdaper extends BaseAdapter implements View.OnClickListener {
    private static final String TAG = "MeetingAdaper";
    private Context ctx;
    private int pos;
    private  int LayResId;
    private List<Meeting> Data;
    private LayoutInflater inflater;
    private String selectedMeetingName;
    private View selectedView;
    private LinearLayout linearLayout;
    public MeetingAdaper(Context context, int resource, List<Meeting> objects) {
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
                row = inflater.inflate(LayResId,parent,false);
                row.setOnClickListener(this);
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

            CheckBox isVisit = (CheckBox) row.findViewById(R.id.check_user);
            if (currMeet.isGoing()){
                isVisit.setChecked(true);
            }
            else isVisit.setChecked(false);
        if (currMeet.getParticipants()!=null){
            for (Participant participant:currMeet.getParticipants()){
                linearLayout = (LinearLayout) row.findViewById(R.id.partisipantsLayout);
                TextView textView = new TextView(ctx);
                textView.setText("Имя  "+participant.getFio());
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));

                TextView devider = new TextView(ctx);
                devider.setText("-------------");
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.addView(devider);

                TextView positionText = new TextView(ctx);
                positionText.setText("Должность  "+participant.getPosition());
                linearLayout.addView(textView);
                linearLayout.addView(positionText);

            }
        }

        return row;
    }


    public Meeting getMeeting(int Position){
        return (Meeting) getItem(Position);
    }

    //переписать нижние 2 метода так чтобы они работали с ключами встреч
    @Override
    public void onClick(View v) {
        selectedView = v;
        TextView textView = (TextView) v.findViewById(R.id.meet_name_val);
       selectedMeetingName = textView.getText().toString();
        v.setBackgroundColor(Color.GRAY);
    }
    public Meeting getMeetingItemByName(){
        Meeting res = null;
        for (Meeting m:Data){
            if (m.getName().equals(selectedMeetingName)){
                res = m;
            }
        }
        selectedMeetingName = "";
        selectedView.setBackgroundColor(Color.WHITE);
        return res;
    }
}
