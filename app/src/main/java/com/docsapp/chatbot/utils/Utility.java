package com.docsapp.chatbot.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.docsapp.chatbot.R;

/**
 * Created by sitaram on 2/22/16.
 */
public class Utility {

    public static boolean isNetwork(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {

            Toast.makeText(ctx, ctx.getString(R.string.newtork_not_available), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public static void saveDataToPreference(Context context, String data, String key) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFRENCE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, data);
        editor.commit();

    }

    public static boolean isPostPending(Context context) {
        SharedPreferences sharedP;
        sharedP = context.getSharedPreferences(Constants.SHARED_PREFRENCE, Context.MODE_PRIVATE);
        if (sharedP.contains(Constants.PENDING_POST_MESSAGE)) {
            return true;
        }
        return false;
    }

    public static String getDataFromPreference(Context context, String key) {

        SharedPreferences sharedP;
        sharedP = context.getSharedPreferences(Constants.SHARED_PREFRENCE, Context.MODE_PRIVATE);
        try {
            if (sharedP.contains(key)) {
                return sharedP.getString(key, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }
}
