package com.example.samaritan;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Users extends AppCompatActivity {
    TextView donor;
    Intent intent;
    TextView trust;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_user);
        this.trust = (TextView) findViewById(R.id.trustview);
        this.donor = (TextView) findViewById(R.id.donorview);
        this.trust.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                intent = new Intent(Users.this, MainActivity.class);

                startActivity(intent);
            }
        });
        this.donor.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                intent = new Intent(Users.this, donor_details.class);

                startActivity(intent);
            }
        });
    }
}
