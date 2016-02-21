package com.docsapp.chatbot.service;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.docsapp.chatbot.BotApplication;
import com.docsapp.chatbot.dao.ChatObject;
import com.docsapp.chatbot.utils.ChatListener;
import com.google.gson.Gson;

/**
 * Created by sitaram on 2/22/16.
 */
public class MessageTask {
    private RequestQueue mReqRequestQueue;
    private String baseUrl = "http://www.personalityforge.com/api/chat/?apiKey=6nt5d1nJHkqbkphe&chatBotID=63906&externalID=chirag1";
    private String Url;
    private ChatListener chatListener;

    public MessageTask(String message, ChatListener chatListener) {
        this.mReqRequestQueue = BotApplication.getRequestQueue();
        this.Url = baseUrl + "&message=" + message;
        this.chatListener = chatListener;
    }

    public void execute() {

        StringRequest req = new StringRequest(Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                ChatObject chatObject = gson.fromJson(response, ChatObject.class);
                if (chatObject != null && chatObject.getMessage() != null)
                    chatListener.onSuccess(chatObject.getMessage());
                else
                    chatListener.onError();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                chatListener.onError();
            }
        });

        mReqRequestQueue.add(req);
    }

}
