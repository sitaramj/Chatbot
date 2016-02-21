package com.docsapp.chatbot.utils;

import com.docsapp.chatbot.database.Message;

/**
 * Created by sitaram on 2/22/16.
 */
public interface ChatListener {
    void onSuccess(Message message);

    void onError();
}
