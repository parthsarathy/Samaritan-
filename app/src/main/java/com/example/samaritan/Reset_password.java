package com.example.samaritan;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Reset_password extends AppCompatActivity {
    EditText email;
    Button reset;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_reset_password);
        this.reset = (Button) findViewById(R.id.reset);
        this.email = (EditText) findViewById(R.id.email);
        this.reset.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(Reset_password.this.email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Reset_password.this.getApplicationContext(), "password reset email sent sucessfully", Toast.LENGTH_SHORT).show();
                            Reset_password.this.startActivity(new Intent(Reset_password.this, signin.class));
                            return;
                        }
                        Toast.makeText(Reset_password.this.getApplicationContext(), "password reset email sent failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
