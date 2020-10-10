package com.example.samaritan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.xml.namespace.QName;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyviewHolder> {

    private Context context;
    private ArrayList<User> user;
    private  CardView cv;
    FirebaseAuth mAuth;
    DatabaseReference mref;
    String key;
    String pid;


    public MyAdapter(Context context, ArrayList<User> user) {
        this.context = context;
        this.user = user;
    }

    @NonNull
    @Override
    public MyAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapter.MyviewHolder holder, int position) {
          final User user= this.user.get(position);
          holder.name.setText(user.getName());
          holder.story.setText(user.getWrite());
          holder.datetime.setText(user.getDatetime());
          pid=user.getPid();
          

          Picasso.get().load(user.getDpurl()).into(holder.dp);
          if (user.getImageUrl().isEmpty())
          {
              holder.image1.setVisibility(View.GONE);
          }
          else
          {
              Picasso.get().load(user.getImageUrl()).into(holder.image1);
          }


         holder.name.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(context,OthersProfile.class);
                 intent.putExtra("usrname",user.name);
                 context.startActivity(intent);
             }
         });

         holder.sendmsg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(context,ChatScreen.class);
                 intent.putExtra("usrname",user.name);
                 intent.putExtra("receiverid",user.userid);
                 context.startActivity(intent);
             }
         });
        holder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,payment.class);
                intent.putExtra("usrname",user.name);
                context.startActivity(intent);
            }
        });
        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup=new PopupMenu(context,v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId())
                        {
                            case R.id.delete:
                                Toast.makeText(context,"delete",Toast.LENGTH_SHORT).show();
                                // deleteStory(pid);
                        }


                        return false;
                    }
                });
                popup.inflate(R.menu.cardmenu);
                popup.show();
            }
        });



    }

    /*private void deleteStory(String pid) {


         Query mquery=FirebaseDatabase.getInstance().getReference("stories").orderByChild("pid").equalTo(pid);
          mquery.addListenerForSingleValueEvent(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  for(DataSnapshot data:dataSnapshot.getChildren());
                  dataSnapshot.getRef().removeValue();
              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
          });







    }*/




    @Override
    public int getItemCount() {
        return user.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView name,story,datetime;
        ImageView image1,menu;
        CircularImageView dp;
        Button sendmsg,pay;
        public MyviewHolder(@NonNull final View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            story=itemView.findViewById(R.id.story);
            datetime=itemView.findViewById(R.id.datetime);
            image1=itemView.findViewById(R.id.image1);
            dp=itemView.findViewById(R.id.dp);
            menu=itemView.findViewById(R.id.cardmenu);
            sendmsg=itemView.findViewById(R.id.sendbtn);
            pay=itemView.findViewById(R.id.pay);
            cv=(CardView)itemView.findViewById(R.id.cardview);

        }
    }
}
