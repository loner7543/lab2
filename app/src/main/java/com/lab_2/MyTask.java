package com.lab_2;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Александр on 24.10.2016.
 */

public class MyTask extends AsyncTask<Void,Void,Meeting> {//<Params, Progress, Result>
    private static HttpsURLConnection connection;
    @Override
    protected Meeting doInBackground(Void... voids) {
        return null;
    }

    public static HttpURLConnection getConnection(String path,String method) throws IOException {
        if (connection==null){
            URL url = new URL(path);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setReadTimeout(10000);
            connection.connect();
        }
        return connection;
    }


}
