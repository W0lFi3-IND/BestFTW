package com.ibm.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class CleanUp extends AppCompatActivity {
TextView tv1,tv2,tv3;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean_up);
        tv1 = findViewById(R.id.v1);
        tv2 = findViewById(R.id.v2);
        tv3 = findViewById(R.id.v3);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Vouchers").child(mUser.getUid());

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             try {
                 tv1.setText("Name : "+dataSnapshot.child("Name").getValue().toString());
                 tv2.setText("Type : "+dataSnapshot.child("Type").getValue().toString());
                 tv3.setText("Url : "+dataSnapshot.child("url").getValue().toString());

             }
             catch (Exception e)
             {
                 tv1.setText("NO VOUCHERS");

             }
                            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        findViewById(R.id.b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),beforecleanup.class));
            }
        });
        findViewById(R.id.a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),aftercleanup.class));
            }
        });
    }
}