package com.docsapp.chatbot;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.docsapp.chatbot.database.DaoMaster;
import com.docsapp.chatbot.database.DaoSession;

/**
 * Created by sitaram on 2/22/16.
 */
public class BotApplication extends Application {
    private static RequestQueue mRequestQueue;
    private static BotApplication mInstance;
    public DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        setupDatabase();
    }

    public static RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(BotApplication.getInstance());
        }

        return mRequestQueue;
    }

    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "boat-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public static synchronized BotApplication getInstance() {
        return mInstance;
    }
}
