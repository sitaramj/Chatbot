package com.docsapp.chatbot.database;

import android.content.Context;

import com.docsapp.chatbot.BotApplication;

import java.util.List;

/**
 * Created by sitaram on 2/22/16.
 */
public class MessageRepository {
    public static void insertOrUpdate(Context context, Message picture) {
        getMessageDao(context).insertOrReplace(picture);
    }

    public static void clearAllMessages(Context context) {
        getMessageDao(context).deleteAll();
    }

    public static List<Message> getAllMessages(Context context) {
        return getMessageDao(context).loadAll();
    }


    private static MessageDao getMessageDao(Context c) {
        return ((BotApplication) c.getApplicationContext()).getDaoSession().getMessageDao();
    }
}
