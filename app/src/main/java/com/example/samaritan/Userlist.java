
package com.example.samaritan;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samaritan.R;
import com.example.samaritan.Searchbar;
import com.example.samaritan.User;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Userlist extends RecyclerView.Adapter<Userlist.ViewHolder> {

    private ArrayList<User>user;
    Context context;

    public Userlist(ArrayList<User> user, Context context) {
        this.user = user;
        this.context = context;
    }

    @NonNull
    @Override
    public Userlist.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatactivity, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Userlist.ViewHolder holder, int position) {
      final  User user=this.user.get(position);
      holder.usrname.setText(user.getName());
        Picasso.get().load(user.getImageUrl()).into(holder.dp);
       holder.usrname.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


                   Intent intent=new Intent(context,ChatScreen.class);
                   intent.putExtra("usrname",user.name);
                   intent.putExtra("receiverid",user.userid);
                  context.startActivity(intent);


           }
       });



    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView usrname;
        CircularImageView dp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             usrname=itemView.findViewById(R.id.username);
             dp=itemView.findViewById(R.id.chatdp);
        }
    }
}
