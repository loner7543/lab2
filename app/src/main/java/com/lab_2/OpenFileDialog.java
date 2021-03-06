package com.lab_2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.lab_2.adapters.FileAdapter;
import com.lab_2.domain.Meeting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * Created by Александр on 07.11.2016.
 */

public class OpenFileDialog extends AlertDialog.Builder {
    public static final String FILE_SEPARATOR ="/";
    private String currentPath = Environment.getExternalStorageDirectory().getPath();
    private List<File> files = new ArrayList<File>();
    private com.lab_2.adapters.FileAdapter FileAdapter;
    private List<Meeting> ExportData;
    private String exportedFileName = "MyFile";
    private List<String[]> exportedData;

    public OpenFileDialog(Context context, final List<Meeting> exp) {
        super(context);
        try {
            this.ExportData =exp;
            files.addAll(getFiles(currentPath));
            FileAdapter = new FileAdapter(context,getFiles(currentPath));
            setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    exportedData = new ArrayList<String[]>();
                    // готовим данные для экспорта
                    for (Meeting m:exp){
                        exportedData.add(new String[]{m.toString()});
                    }
                    String s = FILE_SEPARATOR+FileAdapter.getDeltaPath()+FILE_SEPARATOR;//тут та папка которую вызерет пользователь
                    currentPath = currentPath+s;
                    File resExport = new File(currentPath,exportedFileName);//создает директорию а не файл !!!!!
                    try {
                        if(resExport.createNewFile())
                        {
                            CSVWriter csvWriter = new CSVWriter(new FileWriter(resExport));
                            csvWriter.writeAll(exportedData);
                            csvWriter.close();


                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            })
                    .setNegativeButton(android.R.string.cancel, null);
            setAdapter(FileAdapter,null);
        }
        catch (Exception e){
            Log.d("OpenFileDialog","Ошибка экспорта");
        }

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
