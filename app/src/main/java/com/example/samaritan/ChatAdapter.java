package com.example.samaritan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    Context context;
    ArrayList<User> chatlist;
    private static final int MSG_LEFT=0;
    private static final int MSG_RIGHT=1;
  FirebaseUser user;

    public ChatAdapter(Context context, ArrayList<User> chatlist) {
        this.context = context;
        this.chatlist = chatlist;

    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MSG_RIGHT)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.rightchat,parent,false);
            return new ViewHolder(view);
        }
        else
        {
            View view= LayoutInflater.from(context).inflate(R.layout.leftchat,parent,false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {

        String message=chatlist.get(position).getMessage();

        holder.message1.setText(message);

    }

    @Override
    public int getItemCount() {
        return chatlist.size();
    }

    @Override
    public int getItemViewType(int position) {
        user= FirebaseAuth.getInstance().getCurrentUser();
        if(chatlist.get(position).getSender().equals(user.getUid()))
        {
            return MSG_RIGHT;
        }
        else
        {
            return  MSG_LEFT;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView message1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            message1=itemView.findViewById(R.id.chatmessage);
        }
    }
}
