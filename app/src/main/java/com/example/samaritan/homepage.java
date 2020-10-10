package com.example.samaritan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import  android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class homepage extends AppCompatActivity {
    TextView aboutus;
    TextView chat;
    Intent intent;
    TextView logout;
    FirebaseAuth mAuth;
    TextView search;
    TextView timeline;
    FirebaseUser user;
    TextView write,username;
    boolean backpress=false;
    static final String preference="loginpref";
     Menu menu;
     BottomNavigationView navigationView;
    private RecyclerView recyclerView;
    private ArrayList<User> user1;
    private MyAdapter adapter;
    String dpurl;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_homepage);

        mAuth=FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        FirebaseUser crntuser=mAuth.getCurrentUser();
        username=(TextView)findViewById(R.id.nameview);
        search = (TextView) findViewById(R.id.search);
        navigationView=findViewById(R.id.navigation);
        //chat = (TextView) findViewById(R.id.chat);
        //aboutus = (TextView) findViewById(R.id.about);
        //logout = (TextView) findViewById(R.id.logout);
         crntuser = FirebaseAuth.getInstance().getCurrentUser();
          String userid=mAuth.getCurrentUser().getUid();
          user1=new ArrayList<>();

        recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);


        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("stories");;
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    User datas=data.getValue(User.class);
                    user1.add(datas);
                }
                adapter=new MyAdapter(homepage.this,user1);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.orderByChild("userid").equalTo(userid).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    String name=datas.child("name").getValue().toString();
                    dpurl=datas.child("imageUrl").getValue().toString();

                    username.setText(name);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
               switch (menuItem.getItemId())
               {
                   case R.id.writestory :
                      String name= username.getText().toString();
                       intent = new Intent(homepage.this, writepost.class);
                       intent.putExtra("key",name);
                       intent.putExtra("dpurl",dpurl);
                       startActivity(intent);
                       break;
               }

               return true;

           }
       });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.usermenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id== R.id.search)
        {
            Intent intent=new Intent(homepage.this,Searchbar.class);
            startActivity(intent);
        }
        if(id== R.id.profile)
        {
            Intent intent=new Intent(homepage.this,profile.class);
            startActivity(intent);

        }
        if(id== R.id.userchat)
        {
            Intent intent=new Intent(homepage.this,ChatActivity.class);
            startActivity(intent);

        }
        if(id==R.id.about)
        {
               Intent intent=new Intent(homepage.this, aboutus.class);
               startActivity(intent);

        }
        if(id==R.id.help)
        {
            Intent intent=new Intent(homepage.this, help.class);
            startActivity(intent);
        }
        if(id==R.id.sahreapp)
        {
            Intent intent=new Intent(homepage.this, Shareapp.class);
            startActivity(intent);
        }

        if(id==R.id.signout)
        {
          SharedPreferences preferences=getSharedPreferences(preference,0);
          SharedPreferences.Editor editor= preferences.edit();
          editor.remove("loggedin");
          editor.commit();
          finish();

        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(backpress) {
            super.onBackPressed();
            return;
        }
        this.backpress=true;
        Toast.makeText(homepage.this,"please click back again to exit",Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backpress=false;
            }
        },2000);

    }
}
