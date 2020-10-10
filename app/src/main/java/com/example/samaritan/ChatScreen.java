package com.example.samaritan;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import  java.util.Map;
import java.util.*;
public class ChatScreen extends AppCompatActivity {


   // TextView usrname;
    EditText msg;
    ImageButton sendbtn;
    RecyclerView chatrecycler;
    String receiverid;
    String userid;
    DatabaseReference ref;
    FirebaseAuth mAuth;
    ArrayList<User> chatlist;
    ChatAdapter adapterchat;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        //usrname=(TextView)findViewById(R.id.username);

        msg=(EditText)findViewById(R.id.msg);
        sendbtn=(ImageButton)findViewById(R.id.sendbtn);
        chatrecycler=(RecyclerView)findViewById(R.id.chatrecycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        chatrecycler.setLayoutManager(linearLayoutManager);
        chatrecycler.setHasFixedSize(true);


        Bundle usrname=getIntent().getExtras();
        Bundle id=getIntent().getExtras();
        Intent intent=getIntent();
        receiverid=intent.getStringExtra("receiverid");
        String titlename=usrname.getString("usrname");
        getSupportActionBar().setTitle(titlename);

        mAuth=FirebaseAuth.getInstance();
        userid=mAuth.getUid();
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String message=msg.getText().toString().trim();

                if(TextUtils.isEmpty(message))
                {
                    Toast.makeText(ChatScreen.this,"write something",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sendmessage();
                }
            }
        });

         readmessage();


    }

    private void readmessage() {

        chatlist=new ArrayList<>();
        ref=FirebaseDatabase.getInstance().getReference("chat");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatlist.clear();
                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    User chats=data.getValue(User.class);
                    if(chats.getReceiver().equals(userid)&&chats.getSender().equals(receiverid)||
                            chats.getReceiver().equals(receiverid)&&chats.getSender().equals(userid))
                   {
                        chatlist.add(chats);
                    }
                    adapterchat=new ChatAdapter(ChatScreen.this,chatlist);
                    adapterchat.notifyDataSetChanged();
                    chatrecycler.setAdapter(adapterchat);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendmessage() {
        String message=msg.getText().toString().trim();
        ref=FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> map=new HashMap<>();
        map.put("sender",userid);
        map.put("receiver",receiverid);
        map.put("message",message);
        ref.child("chat").push().setValue(map);
        msg.setText("");


    }
}
