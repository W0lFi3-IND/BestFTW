package com.ibm.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class uploadPhoto extends AppCompatActivity {
    String lang,lat;
    private static final int PICK_IMAGE = 100;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    ProgressBar mProgressBar;
    Uri imageUri;
    TextView uploadingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);
        Intent i = getIntent();
        lang = String.valueOf(i.getDoubleExtra("Long",0));
        lat = String.valueOf(i.getDoubleExtra("Lat",0));
        mProgressBar = findViewById(R.id.progressBar);
        uploadingText  = findViewById(R.id.textViewv);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("DATA");
        openGallery();

    }
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            firebase(imageUri);

        }
    }
    private void firebase(Uri contentUri) {
        final StorageReference image = mStorageRef.child("Photos"+ "/" + mUser.getUid());
        mProgressBar.setVisibility(View.VISIBLE);
        uploadingText.setVisibility(View.VISIBLE);
        image.putFile(contentUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                DateFormat df = new SimpleDateFormat("d/M/yyyy");
                                Date date = new Date();
                                String d = df.format(date);
                                mDatabase = mDatabase.child(mUser.getUid());
                                HashMap<String, String> map = new HashMap<>();
                                map.put("url", uri.toString());
                                map.put("date",d);
                                map.put("latitude",lat);
                                map.put("longitude",lang);
                                map.put("PhoneNumber",FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                                mDatabase.setValue(map);
                                // change due crash
                                mProgressBar.setVisibility(View.GONE);
                                //
                                uploadingText.setVisibility(View.INVISIBLE);


                                Toast.makeText(getApplicationContext(),"Entry added!!",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(uploadPhoto.this,complaintText.class);
                                startActivity(i);
                                finish();

                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }
}