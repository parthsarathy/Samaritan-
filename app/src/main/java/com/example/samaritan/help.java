package com.example.samaritan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class help extends AppCompatActivity {

    EditText query;
    Button sendquery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        query=(EditText)findViewById(R.id.query);
        sendquery=(Button)findViewById(R.id.sendquery);
        sendquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] TO = {"neelanmaila@gmail.com"};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");


                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Help query");
                emailIntent.putExtra(Intent.EXTRA_TEXT, query.toString());

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();
                   // Toast.makeText(help.this,"query sended You will get response email as soon as possible",Toast.LENGTH_SHORT).show();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(help.this,
                            "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
