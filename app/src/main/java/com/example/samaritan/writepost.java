package com.example.samaritan;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
public class writepost extends AppCompatActivity {
    private static final String CHANNEL_ID = "samaritan";
    int RESULT_LOAD_IMAGE = 1;
    User Name;
    FirebaseAuth mAuth;
    DatabaseReference mref;
    TextView pic;
    ImageView picture;
    Button post;
    User stories;
    ProgressBar progrss;
    private StorageReference mStorageRef;
    public String datetime;
    // FirebaseUser user;
    EditText write;
    private Uri filepath;
    private int notificationId=10;
     NotificationManagerCompat notificationManager;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_writepost);
        createNotificationChannel();
        this.post = (Button) findViewById(R.id.post);
        this.write = (EditText) findViewById(R.id.write);
        this.pic = (TextView) findViewById(R.id.pic);
        this.mAuth = FirebaseAuth.getInstance();
        picture = (ImageView) findViewById(R.id.picture);
        progrss = (ProgressBar) findViewById(R.id.progrss1);
        Name = new User();
        mref = FirebaseDatabase.getInstance().getReference("stories");
       notificationManager = NotificationManagerCompat.from(writepost.this);

        stories = new User();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        SimpleDateFormat date = new SimpleDateFormat("dd.MM.yy  HH.mm");
        datetime = date.format(new Date());
        final String timestamp = String.valueOf(System.currentTimeMillis());


        this.pic.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OpenGallery();
            }
        });

        post.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                progrss.setVisibility(View.VISIBLE);
                //progrss.setIndeterminate(false);
                String userid = mAuth.getCurrentUser().getUid();
                String writestory = write.getText().toString();
                Bundle name1 = getIntent().getExtras();
                if (name1 != null) {

                    String usrname = name1.getString("key");
                    String dpurl = name1.getString("dpurl");
                    Name.setName(usrname);
                    Name.setWrite(writestory);
                    Name.setUserid(userid);
                    Name.setPid(timestamp);
                    Name.setDatetime(datetime);
                    Name.setDpurl(dpurl);
                    Name.setImageUrl("");
                    if (picture.getDrawable() == null) {
                        mref.push().setValue(Name);
                        progrss.setVisibility(View.GONE);
                        Toast.makeText(writepost.this, "uploaded sucessfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(writepost.this, homepage.class);
                        startActivity(intent);
                        notificationManager.notify(notificationId,builder.build());


                    } else {
                        uploadimage();
                    }


                }


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
            picture.setVisibility(View.VISIBLE);
            filepath = data.getData();

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                picture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public void uploadimage() {
        if (filepath != null) {

            final StorageReference ref = mStorageRef.child("images/" + UUID.randomUUID().toString());


            UploadTask uploadTask = ref.putFile(filepath);
            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    progrss.setProgress((int) progress);
                    builder.setContentTitle("uploading post....");
                    builder.setContentText(""+(int)progress+"");
                    builder.setProgress(100,(int)progress,false);
                    notificationManager.notify(notificationId,builder.build());



                }
            })
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content

                            // Uri imageurl=  mStorageRef.child("images").child(mAuth.getCurrentUser().getUid()).getDownloadUrl().getResult();


                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri image = uri;

                                    Name.setImageUrl(image.toString());
                                    mref.push().setValue(Name);
                                    progrss.setVisibility(View.GONE);
                                    builder.setContentText("Uploaded suceesfully");
                                    notificationManager.notify(1,builder.build());
                                    Toast.makeText(writepost.this, "uploaded sucessfully", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(writepost.this, homepage.class);
                                    startActivity(intent);



                                }
                            });
                            //Toast.makeText(writepost.this,"Image Added successfully",Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Toast.makeText(writepost.this, "Image Added failed", Toast.LENGTH_SHORT).show();

                            // ...
                        }
                    });

        }


    }


    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.notify_24dp)
            .setContentTitle("New Post")
            .setContentText("You added a post Successfully")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);




    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                channel.setDescription(description);
            }
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



}





