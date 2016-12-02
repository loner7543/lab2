package com.lab_2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lab_2.domain.Participant;

import java.util.List;

/**
 * Created by Александр on 24.10.2016.
 */

public class ParticipantAdapter extends BaseAdapter{
    private Context ctx;
    private int LayResId;
    private List<Participant> Data;
    private LayoutInflater inflater;

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
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
                Participant currPart = getItemOnPosition(position);
                row = inflater.inflate(LayResId, parent, false);

                TextView name = (TextView) row.findViewById(R.id.par_nameVal);
                name.setText(currPart.getFio());

                TextView pos = (TextView) row.findViewById(R.id.par_descVal);
                pos.setText(currPart.getPosition());
            }
        return row;
    }

    public ParticipantAdapter(Context context, int resource, List<Participant> objects) {
        this.ctx =context;
        this.LayResId = resource;
        this.Data = objects;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public Participant getItemOnPosition(int position){
        return (Participant) getItem(position);
    }
}
