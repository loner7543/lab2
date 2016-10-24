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

public class MainAdapter extends ArrayAdapter<Meeting> {
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
            for (int i = 0;i<Data.size();i++){

                row = inflater.inflate(LayResId,parent,false);
                TextView meetName = (TextView) row.findViewById(R.id.meet_name_val);
                meetName.setText(Data.get(i).getName());

                TextView meetDesc = (TextView) row.findViewById(R.id.meet_desc_val);
                meetDesc.setText(Data.get(i).getType());

                TextView fromDate = (TextView) row.findViewById(R.id.fromDate_desc);
                fromDate.setText(Data.get(i).getFromDate());

                TextView toDate = (TextView) row.findViewById(R.id.to_date_desc);
                toDate.setText(Data.get(i).getToDate());

                ListView parList = (ListView) row.findViewById(R.id.persons_list);


            }

        }
        return row;
    }
}
