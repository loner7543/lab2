package com.lab_2.manager;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lab_2.NetworkLostActivity;

/**
 * Created by alma0516 on 11/24/2016.
 */

public class NetworkService extends IntentService {
    private ConnectivityManager cm;
    private boolean isNetworkAvailable = false;
    private Context context;
    private NetworkInfo networkInfo;
    private static final String NETWORK_SERVICE_LOG = "Network service";

    public NetworkService(String name) {
        super(name);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        checkNetwork();
    }

//Проверяет включена ли сеть. Берем станд сервис у системы и проверяем. Если сети нет - показываем активити
    public void checkNetwork() {
        cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo =cm.getActiveNetworkInfo();//null - сеть недоступна
        if (networkInfo==null){
            Log.d(NETWORK_SERVICE_LOG,"Сеть недоступна");
            Intent networtCrach = new Intent(this,NetworkLostActivity.class);
            startActivity(networtCrach);

        }
        else {
            isNetworkAvailable = networkInfo.isConnectedOrConnecting();
            if (!isNetworkAvailable){
                Log.d(NETWORK_SERVICE_LOG,"Сеть доступна");
            }
        }
    }
}
