package com.example.samaritan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE =1 ;
    User Trust;
    String add;
    EditText address;
    EditText city;
    String cityname;
    String confpass;
    EditText conpass;
    EditText email;
    String email_name;

    /* renamed from: id */
    String id;
    Intent intent;
    private Uri filepath;
    /* access modifiers changed from: private */
    public FirebaseAuth mAuth;
    DatabaseReference mRef;
    EditText mobile;
    EditText name;
    String name1;
    EditText pass;
    String password;
    String phone;
    String pin;
    EditText pincode;
    ProgressBar progress;
    Button signup;
    EditText state;
    String statename;
    EditText trustid;
    String userid;
    ImageView trustdp;
    StorageReference mref;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        this.email = (EditText) findViewById(R.id.email);
        this.name = (EditText) findViewById(R.id.name);
        this.address = (EditText) findViewById(R.id.address);
        this.trustid = (EditText) findViewById(R.id.trustid);
        this.city = (EditText) findViewById(R.id.city);
        this.state = (EditText) findViewById(R.id.state);
        this.pincode = (EditText) findViewById(R.id.pincode);
        this.conpass = (EditText) findViewById(R.id.conpass);
        this.mobile = (EditText) findViewById(R.id.mobile);
        this.pass = (EditText) findViewById(R.id.pass);
        trustdp=(ImageView)findViewById(R.id.trustdp);
        this.signup = (Button) findViewById(R.id.signup);
        this.progress = (ProgressBar) findViewById(R.id.progress);
        this.mAuth = FirebaseAuth.getInstance();
        mref=FirebaseStorage.getInstance().getReference();
        this.mRef = FirebaseDatabase.getInstance().getReference("users");
        this.Trust = new User();
        this.signup.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.registerUser();
            }
        });

        trustdp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });


    }

    /* access modifiers changed from: private */
    public void registerUser() {
        this.email_name = this.email.getText().toString();
        this.password = this.pass.getText().toString();
        this.name1 = this.name.getText().toString();
        this.add = this.address.getText().toString();
        this.phone = this.mobile.getText().toString();
        this.id = this.trustid.getText().toString();
        this.cityname = this.city.getText().toString();
        this.statename = this.state.getText().toString();
        this.pin = this.pincode.getText().toString();
        this.confpass = this.conpass.getText().toString();
        if (this.name.length() < 0) {
            this.name.setError("Name is required");
            this.name.requestFocus();
        }
        if (this.address.length() < 0) {
            this.address.setError("Address is required");
            this.address.requestFocus();
        }
        if (this.trustid.length() < 0) {
            this.trustid.setError("Trust id is required");
            this.trustid.requestFocus();
        }
        if (this.city.length() < 0) {
            this.city.setError("city is required");
            this.city.requestFocus();
        }
        if (this.state.length() < 0) {
            this.state.setError("state id is required");
            this.state.requestFocus();
        }
        if (this.pincode.length() < 0) {
            this.pincode.setError("pincode id is required");
            this.pincode.requestFocus();
        }
        if (this.mobile.length() > 10 || this.mobile.length() < 10) {
            this.mobile.setError("phone number must be 10 digit");
            this.mobile.requestFocus();
        }
        if (this.email.length() < 0) {
            this.email.setError("Email is required");
            this.email.requestFocus();
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(this.email_name).matches()) {
            this.email.setError("Invalid Email");
            this.email.requestFocus();
        }
        if (this.pass.length() < 0) {
            this.pass.setError("password id required");
            this.pass.requestFocus();
        }
        if (this.conpass.length() < 0) {
            this.conpass.setError("confirm password is required");
            this.conpass.requestFocus();
        }
        if (!this.confpass.matches(this.password)) {
            this.conpass.setError("password and confirm password should be same");
            this.conpass.requestFocus();
        }
        this.progress.setVisibility(View.VISIBLE);
        this.mAuth.createUserWithEmailAndPassword(this.email_name, this.password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final FirebaseUser user = MainActivity.this.mAuth.getCurrentUser();
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(Task<Void> task) {
                            if (task.isSuccessful()) {
                                MainActivity.this.userid = user.getUid().toString();
                                MainActivity.this.Trust.setName(MainActivity.this.name1);
                                MainActivity.this.Trust.setTrustid(MainActivity.this.id);
                                MainActivity.this.Trust.setAddress(MainActivity.this.add);
                                MainActivity.this.Trust.setCity(MainActivity.this.cityname);
                                MainActivity.this.Trust.setState(MainActivity.this.statename);
                                MainActivity.this.Trust.setPincode(MainActivity.this.pin);
                                MainActivity.this.Trust.setMobile(MainActivity.this.phone);
                                MainActivity.this.Trust.setEmail(MainActivity.this.email_name);
                                MainActivity.this.Trust.setPass(MainActivity.this.password);
                                MainActivity.this.Trust.setUserid(MainActivity.this.userid);
                                uploadimage();

                            }
                        }
                    });
                    return;
                }
                MainActivity.this.progress.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Registration Failed.",Toast.LENGTH_SHORT).show();
                MainActivity.this.pass.setText("");
            }
        });
    }
    private void OpenGallery() {

        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, RESULT_LOAD_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RESULT_LOAD_IMAGE) {

            filepath = data.getData();

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                trustdp.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public  void uploadimage()
    {
        if(filepath!=null)
        {

            final StorageReference ref = mref.child("profilepic/" + UUID.randomUUID().toString());
            UploadTask uploadTask=  ref.putFile(filepath);
            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress1=(100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    progress.setProgress((int)progress1);

                }
            })
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri image=uri;
                                    Trust.setImageUrl(image.toString());
                                    mRef.push().setValue(Trust);
                                    MainActivity.this.progress.setVisibility(View.GONE);
                                    Toast.makeText(MainActivity.this, "verify email to finish  trust sign up.", Toast.LENGTH_SHORT).show();
                                    MainActivity.this.intent = new Intent(MainActivity.this, signin.class);
                                    MainActivity.this.startActivity(MainActivity.this.intent);

                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                        }
                    });

        }



    }
}
