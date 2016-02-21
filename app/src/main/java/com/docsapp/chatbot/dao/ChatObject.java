package com.docsapp.chatbot.dao;

import com.docsapp.chatbot.database.Message;

import java.io.Serializable;

/**
 * Created by sitaram on 2/22/16.
 */
public class ChatObject implements Serializable {
    private String errorMessage;
    private int success ;
    private Message message;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
