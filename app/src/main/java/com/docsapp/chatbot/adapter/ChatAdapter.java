package com.docsapp.chatbot.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.docsapp.chatbot.R;
import com.docsapp.chatbot.database.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sitaram on 2/22/16.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    List<Message> chatMessageList;
    Context context;


    public ChatAdapter(Context context) {
        this.chatMessageList = new ArrayList<>();
        this.context = context;

    }

    public void updateChatData(List<Message> chatList) {
        this.chatMessageList.addAll(chatList);
        notifyDataSetChanged();
    }

    public void addNewChat(Message chat) {
        this.chatMessageList.add(chat);
        notifyDataSetChanged();
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ChatViewHolder(LayoutInflater.from(context).inflate
                (R.layout.item_chat_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        final Message chat = chatMessageList.get(position);
        if (chat != null) {

            if (chat.getIsOwner()) {

                holder.userChat.setText(chat.getMessage());
                holder.botChat.setVisibility(View.GONE);
                holder.userChat.setVisibility(View.VISIBLE);
                holder.userChat.setBackgroundResource(R.drawable.msg_out);

            } else {
                holder.botChat.setText(chat.getMessage());
                holder.userChat.setVisibility(View.GONE);
                holder.botChat.setVisibility(View.VISIBLE);
                holder.botChat.setBackgroundResource(R.drawable.msg_in);
            }
        }

    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView userChat, botChat;


        public ChatViewHolder(View itemView) {
            super(itemView);

            userChat = (TextView) itemView.findViewById(R.id.userChat);
            botChat = (TextView) itemView.findViewById(R.id.boatChat);

        }
    }
}