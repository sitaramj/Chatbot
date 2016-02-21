package com.docsapp.chatbot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.docsapp.chatbot.adapter.ChatAdapter;
import com.docsapp.chatbot.database.Message;
import com.docsapp.chatbot.database.MessageRepository;
import com.docsapp.chatbot.service.MessageTask;
import com.docsapp.chatbot.utils.ChatListener;
import com.docsapp.chatbot.utils.Constants;
import com.docsapp.chatbot.utils.Utility;

import java.util.List;

/**
 * Created by sitaram on 2/22/16.
 */
public class ChatFragment extends Fragment implements View.OnClickListener {
    private RecyclerView chatList;
    private ChatAdapter adapter;
    private EditText messageText;
    private ImageView sendButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        chatList = (RecyclerView) view.findViewById(R.id.chatList);
        messageText = (EditText) view.findViewById(R.id.messageText);
        sendButton = (ImageView) view.findViewById(R.id.send);
        sendButton.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        chatList.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(getContext());
        chatList.setAdapter(adapter);
        loadMessages();
        return view;
    }

    private void loadMessages() {
        List<Message> messageList = MessageRepository.getAllMessages(getActivity());
        if (messageList != null && messageList.size() > 0) {
            adapter.updateChatData(messageList);
            chatList.scrollToPosition(adapter.getItemCount() - 1);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                if (Utility.isNetwork(getActivity()))
                    postMessage();
                else {
                    Utility.saveDataToPreference(getActivity(), messageText.getText().toString(), Constants.PENDING_POST_MESSAGE);
                }
                break;
            default:
                break;
        }
    }

    private void postMessage() {
        String message = messageText.getText().toString();
        if (message.length() == 0)
            return;
        Message m = new Message();
        m.setIsOwner(true);
        m.setMessage(message);
        adapter.addNewChat(m);
        messageText.setText("");
        MessageRepository.insertOrUpdate(getActivity(), m);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemInserted(adapter.getItemCount() - 1);
                chatList.scrollToPosition(adapter.getItemCount() - 1);
            }
        });
        new MessageTask(message, chatListener).execute();
    }

    ChatListener chatListener = new ChatListener() {
        @Override
        public void onSuccess(Message message) {
            adapter.addNewChat(message);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyItemInserted(adapter.getItemCount() - 1);
                    chatList.scrollToPosition(adapter.getItemCount() - 1);
                }
            });
            MessageRepository.insertOrUpdate(getActivity(), message);
        }

        @Override
        public void onError() {
            // Toast.makeText(getContext(), "Message could not be posted ", Toast.LENGTH_LONG).show();
        }
    };

}
