package com.example.samaritan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import  android.os.Handler;

import com.google.firebase.database.Transaction;

public class Splashscreen extends AppCompatActivity {
    private  static int SPLASH_SCREEN_TIME_OUT=4000;
    static final String preference="loginpref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            SharedPreferences preferences=getSharedPreferences(preference,0);
            boolean loggedin= preferences.getBoolean("loggedin",false);
            if(loggedin)
            {
                Intent intent= new Intent(Splashscreen.this,homepage.class);
                startActivity(intent);
                finish();
            }
            else
            {
               Intent intent= new Intent(Splashscreen.this,signin.class);
                startActivity(intent);
            }

            }
        },SPLASH_SCREEN_TIME_OUT);





    }
}
