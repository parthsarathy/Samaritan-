package com.example.samaritan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import  java.util.ArrayList;
import java.util.UUID;

public class profile extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private  FirebaseAuth mAuth;
   private String userid;
   private Uri filepath;
    private ListView mlistview;
    StorageReference mref;
    ImageView dp;
    String url=null;
    String key;
    DatabaseReference reference;
    User profilepic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        mlistview = (ListView) findViewById(R.id.listview);
        mref = FirebaseStorage.getInstance().getReference();
        profilepic = new User();

        userid = user.getUid();
        dp = (ImageView) findViewById(R.id.dp);
        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.orderByChild("userid").equalTo(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                    String name = datas.child("name").getValue().toString();
                    String address = datas.child("address").getValue().toString();

                    String city = datas.child("city").getValue().toString();
                    String state = datas.child("state").getValue().toString();
                    String email = datas.child("email").getValue().toString();
                    String phone = datas.child("mobile").getValue().toString();
                    url= datas.child("imageUrl").getValue().toString();
                    Picasso.get().load(url).placeholder(R.drawable.applogo).into(dp);
                    ArrayList<String> array = new ArrayList<String>();
                    array.add(name);
                    array.add(address);
                    array.add(city);
                    array.add(state);
                    array.add(email);
                    array.add(phone);
                    ArrayAdapter adapter;
                    adapter = new ArrayAdapter<String>(profile.this, android.R.layout.simple_list_item_1, array);
                    mlistview.setAdapter(adapter);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    }








