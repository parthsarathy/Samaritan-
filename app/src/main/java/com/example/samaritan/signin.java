package com.example.samaritan;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/* renamed from: com.example.helpinghands.signin */
public class signin extends AppCompatActivity {
    EditText email;
    String email_name;
    TextView frgtpass;
    Intent intent;
    Button login;
    static final String preference="loginpref";
    /* access modifiers changed from: private */
    public FirebaseAuth mAuth;
    EditText pass;
    String password;
    ProgressBar progress;
    TextView user;


    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.signin);
        this.email = (EditText) findViewById(R.id.email);
        this.pass = (EditText) findViewById(R.id.pass);
        this.login = (Button) findViewById(R.id.login);
        this.user = (TextView) findViewById(R.id.newuser);
        this.frgtpass = (TextView) findViewById(R.id.frgrtpass);
        this.progress = (ProgressBar) findViewById(R.id.progress);
        this.mAuth = FirebaseAuth.getInstance();
        this.login.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                registerUser();
            }
        });

        user.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                intent = new Intent(signin.this, Users.class);
                startActivity(intent);
            }
        });
        this.frgtpass.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                intent = new Intent(signin.this, Reset_password.class);
                startActivity(intent);
            }
        });
    }

    /* access modifiers changed from: private */
    public void registerUser() {
        this.email_name = this.email.getText().toString();
        this.password = this.pass.getText().toString();
        this.progress.setVisibility(View.VISIBLE);
        if (this.email_name.length() < 0) {
            this.email.setError("Please enter the email");
            this.email.requestFocus();
        } else if (this.password.length() < 6 || this.pass.length() > 16) {
            this.pass.setError("password must be 8 to 16 character");
            this.pass.requestFocus();
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(this.email_name).matches()) {
                Toast.makeText(this, "INVALID EMAIL", Toast.LENGTH_SHORT).show();
            }
            this.mAuth.signInWithEmailAndPassword(this.email_name, this.password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                        public void onComplete(Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        SharedPreferences preferences=getSharedPreferences(preference,0);
                        SharedPreferences.Editor editor= preferences.edit();
                        editor.putBoolean("loggedin",true);
                        editor.commit();
                        signin.this.progress.setVisibility(View.GONE);
                        if (signin.this.mAuth.getCurrentUser().isEmailVerified()) {
                            Toast.makeText(signin.this, "Authentication sucess.", Toast.LENGTH_SHORT).show();
                            intent = new Intent(signin.this, homepage.class);
                              email.setText("");
                              pass.setText("");
                            startActivity(intent);

                            return;
                        }
                        Toast.makeText(signin.this, "verify the email link to signin", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    progress.setVisibility(View.GONE);
                    Toast.makeText(signin.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    pass.setText("");
                }
            });
        }
    }
    public void onBackPressed() {
        {
            ActivityCompat.finishAffinity(signin.this);
            super.onBackPressed();

        }
    }
}
