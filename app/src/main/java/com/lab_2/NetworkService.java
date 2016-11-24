package com.lab_2;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by alma0516 on 11/24/2016.
 */

public class NetworkService extends Service {
    private ConnectivityManager cm;
    private boolean isNetworkAvailable = false;
    private Context context;
    private NetworkInfo networkInfo;
    private static final String NETWORK_SERVICE_LOG = "Network service";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(NETWORK_SERVICE_LOG,"Network service created");
        context = getApplicationContext();
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                checkNetwork();
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /*
*Проверяет доступность сети со стороны телефона
 */
    public void checkNetwork() {
        cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo =cm.getActiveNetworkInfo();//null - сеть недоступна
        if (networkInfo==null){
            Log.d(NETWORK_SERVICE_LOG,"Сеть доступна");
            Intent networtCrach = new Intent(this,NetworkLostActivity.class);
            startActivity(networtCrach);

        }
        else {
            isNetworkAvailable = networkInfo.isConnectedOrConnecting();
            if (!isNetworkAvailable){
                Log.d(NETWORK_SERVICE_LOG,"Сеть доступна");
            }
        }
        boolean isWiFi = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }
}
