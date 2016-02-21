package com.docsapp.chatbot.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.docsapp.chatbot.service.PostDataService;

/**
 * Created by sitaram on 2/22/16.
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Log.d("app", "Network connectivity change");
        if (intent.getExtras() != null) {
            NetworkInfo ni = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
                if (Utility.isPostPending(context)) ;
                {
                    Intent intentService = new Intent(context, PostDataService.class);
                    context.startService(intentService);
                }
            }
        }
        if (intent.getExtras().getBoolean(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
            Log.d("app", "There's no network connectivity");
        }
    }
}