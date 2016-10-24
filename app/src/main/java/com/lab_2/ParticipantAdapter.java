package com.lab_2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Александр on 24.10.2016.
 */

public class ParticipantAdapter extends ArrayAdapter<Participant> {
    private Context ctx;
    private  int LayResId;
    private List<Participant> Data;
    private LayoutInflater inflater;

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    public ParticipantAdapter(Context context, int resource, List<Participant> objects) {
        super(context, resource, objects);

    }
}
