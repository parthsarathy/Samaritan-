package com.example.samaritan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class payment extends AppCompatActivity {
         TextView username;
         EditText amount;
         Button pay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        username=findViewById(R.id.trustname);
        amount=findViewById(R.id.amount);
        pay=findViewById(R.id.pay);

        Bundle intent=getIntent().getExtras();
        String trustname=intent.getString("usrname");
        username.setText(trustname);




        AlertDialog.Builder builder=new AlertDialog.Builder(payment.this);
        LayoutInflater layoutInflater=getLayoutInflater();
        View dialogueinflater=layoutInflater.inflate(R.layout.activity_payment,null);
        builder.setPositiveButton(R.string.pay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(payment.this,"payment Successfully",Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                    Toast.makeText(payment.this, "payment canceled", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

            }
        });
        builder.setView(dialogueinflater);
        builder.show();







    }

}


