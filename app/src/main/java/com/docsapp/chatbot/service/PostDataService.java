package com.docsapp.chatbot.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.docsapp.chatbot.R;
import com.docsapp.chatbot.database.Message;
import com.docsapp.chatbot.database.MessageRepository;
import com.docsapp.chatbot.utils.ChatListener;
import com.docsapp.chatbot.utils.Constants;
import com.docsapp.chatbot.utils.Utility;

/**
 * Created by sitaram on 2/22/16.
 */
public class PostDataService extends IntentService {
    private static final int MY_NOTIFICATION_ID = 2;
    private NotificationManager notificationManager;
    private Notification myNotification;

    public PostDataService() {
        super(PostDataService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String message = Utility.getDataFromPreference(this, Constants.PENDING_POST_MESSAGE);
        if (message != null) {
            new MessageTask(message, listener).execute();
            Message m = new Message();
            m.setIsOwner(true);
            m.setMessage(message);
            MessageRepository.insertOrUpdate(PostDataService.this, m);
        }
    }

    ChatListener listener = new ChatListener() {
        @Override
        public void onSuccess(Message message) {
            String notificationText = "Your message has been posted";
            myNotification = new NotificationCompat.Builder(getApplicationContext()).setContentTitle("Message sent")
                    .setContentText(notificationText).setTicker("Notification!").setWhen(System.currentTimeMillis())
                    .setDefaults(Notification.DEFAULT_SOUND).setAutoCancel(true).setSmallIcon(R.drawable.notification)
                    .build();
            notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
            MessageRepository.insertOrUpdate(PostDataService.this, message);
        }

        @Override
        public void onError() {

        }
    };
}
