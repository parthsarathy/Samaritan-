package com.example.samaritan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OthersProfile extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String userid;
    private ListView mlistview;
    ImageView dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.others_profile);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        dp=(ImageView)findViewById(R.id.dp);
        mlistview = (ListView) findViewById(R.id.listview);
        userid = user.getUid();
         Bundle usrname= getIntent().getExtras();
         String username=usrname.getString("usrname");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.orderByChild("name").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                    String name = datas.child("name").getValue().toString();
                    String address = datas.child("address").getValue().toString();
                    String city = datas.child("city").getValue().toString();
                    String state = datas.child("state").getValue().toString();
                    String email = datas.child("email").getValue().toString();
                    String phone = datas.child("mobile").getValue().toString();
                    String url=datas.child("imageUrl").getValue().toString();
                    Picasso.get().load(url).placeholder(R.drawable.applogo).into(dp);
                   // String Trustid=datas.child("trustid").getValue().toString();

                    ArrayList<String> array = new ArrayList<String>();
                    array.add(name);
                    array.add(address);
                    array.add(city);
                    array.add(state);
                    array.add(email);
                    array.add(phone);
                   // array.add(Trustid);
                    ArrayAdapter adapter;
                    adapter = new ArrayAdapter<String>(OthersProfile.this, android.R.layout.simple_list_item_1, array);
                    mlistview.setAdapter(adapter);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}

