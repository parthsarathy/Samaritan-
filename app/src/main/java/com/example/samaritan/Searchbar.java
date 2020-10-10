package com.example.samaritan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.core.Context;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class Searchbar extends AppCompatActivity {


    private EditText searchtext;
    private Button search;
    private RecyclerView recyler;
    DatabaseReference ref;
    FirebaseRecyclerAdapter<User, UserViewHolder> recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbar);

        searchtext = (EditText) findViewById(R.id.search);
        search = (Button) findViewById(R.id.srchbtn);
        recyler = (RecyclerView) findViewById(R.id.recyclerView2);
        recyler.setLayoutManager(new LinearLayoutManager(this));
        recyler.setHasFixedSize(true);

        ref = FirebaseDatabase.getInstance().getReference("users");


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String searchitem = searchtext.getText().toString().toUpperCase();

                searchuser(searchitem);
            }
        });


    }


    private void searchuser(String searchitem) {

       // Toast.makeText(Searchbar.this, "searching" + searchitem, Toast.LENGTH_SHORT).show();
        Query searchquery = ref.orderByChild("name").startAt(searchitem).endAt(searchitem + "\uf8ff");

        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>().setQuery(searchquery, User.class).build();
        recyclerAdapter = new FirebaseRecyclerAdapter<User, UserViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull User model) {
                holder.setName(model.getName());



            }

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userlist, parent, false);
                return new UserViewHolder(view);
            }
        };


        recyler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyler.setAdapter(recyclerAdapter);
        recyclerAdapter.startListening();
        recyclerAdapter.notifyDataSetChanged();



    }


    public class UserViewHolder extends RecyclerView.ViewHolder {
        View mview;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            mview = itemView;
        }
            public void setName(final String name)
            {
                TextView usrname=itemView.findViewById(R.id.username);
                usrname.setText(name);
              //  Toast.makeText(Searchbar.this,"name" +name,Toast.LENGTH_SHORT).show();


        }

    }




}
