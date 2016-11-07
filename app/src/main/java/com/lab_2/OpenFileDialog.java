package com.lab_2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Александр on 07.11.2016.
 */

public class OpenFileDialog extends AlertDialog.Builder {
    public static final String FILE_SEPARATOR ="/";
    private String currentPath = Environment.getExternalStorageDirectory().getPath();
    private List<File> files = new ArrayList<File>();
    private FileAdapter FileAdapter;
    private List<Meeting> ExportData;

    public OpenFileDialog(Context context,List<Meeting> exp) {
        super(context);
        this.ExportData =exp;
        files.addAll(getFiles(currentPath));
        FileAdapter = new FileAdapter(context,getFiles(currentPath));
        setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String s = FileAdapter.getDeltaPath()+FILE_SEPARATOR;//тут та папка которую вызерет пользователь
                currentPath = currentPath+s;
            }
        })
                .setNegativeButton(android.R.string.cancel, null);
        setAdapter(FileAdapter,null);
    }
    private List<File> getFiles(String directoryPath){
        File directory = new File(directoryPath);
        List<File> fileList = Arrays.asList(directory.listFiles());
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File file, File file2) {
                if (file.isDirectory() && file2.isFile())
                    return -1;
                else if (file.isFile() && file2.isDirectory())
                    return 1;
                else
                    return file.getPath().compareTo(file2.getPath());
            }
        });
        return fileList;
    }


}
