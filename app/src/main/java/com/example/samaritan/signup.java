package com.example.samaritan;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest.Builder;

public class signup extends AppCompatActivity {
    EditText email;

    /* renamed from: id */
    String id;
    String location;
    FirebaseAuth mAuth;
    EditText name;
    String names;
    EditText pass;
    ProgressBar progress;
    Button register;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_signup);
        this.name = (EditText) findViewById(R.id.name);
        this.progress = (ProgressBar) findViewById(R.id.progess);
        this.email = (EditText) findViewById(R.id.email);
        this.pass = (EditText) findViewById(R.id.pass);
        this.register = (Button) findViewById(R.id.register);
        this.mAuth = FirebaseAuth.getInstance();
        this.register.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                signup.this.signinuser();
            }
        });
    }

    /* access modifiers changed from: private */
    public void signinuser() {
        this.names = this.name.getText().toString().trim();
        this.progress.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().getCurrentUser().updateProfile(new Builder().setDisplayName(this.names).build()).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    signup.this.progress.setVisibility(View.GONE);
                    Toast.makeText(signup.this, "Name Updated successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
