package com.lab_2;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/**
 * Created by Александр on 07.11.2016.
 */

public class FileAdapter extends ArrayAdapter<File> implements View.OnClickListener {
    private String deltaPath;
    public FileAdapter(Context context, List<File> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Nullable
    @Override
    public File getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setOnClickListener(this);
        File file = getItem(position);
        view.setText(file.getName());
        return view;
    }


    @Override
    public void onClick(View view) {
        TextView textView = (TextView) view;//сделать приписку к пути
        deltaPath = textView.getText().toString();//проверить что тут лежит
        view.setBackgroundColor(Color.GRAY);
    }

    public String getDeltaPath() {
        return deltaPath;
    }
}
