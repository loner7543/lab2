package com.lab_2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Александр on 24.10.2016.
 */

public class ParticipantAdapter extends ArrayAdapter<Participant> {
    private Context ctx;
    private int LayResId;
    private List<Participant> Data;
    private LayoutInflater inflater;

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            for (Participant participant:Data) {

                row = inflater.inflate(LayResId, parent, false);
                // row.setOnClickListener(this);
                TextView name = (TextView) row.findViewById(R.id.par_nameVal);
                name.setText(participant.getFio());

                TextView pos = (TextView) row.findViewById(R.id.par_descVal);
                pos.setText(participant.getPosition());
            }
        }
        return row;
    }

    public ParticipantAdapter(Context context, int resource, List<Participant> objects) {
        super(context, resource, objects);
        this.ctx =context;
        this.LayResId = resource;
        this.Data = objects;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
}
