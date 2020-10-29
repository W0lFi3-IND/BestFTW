package com.ibm.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class complaintText extends AppCompatActivity {
DatabaseReference mdatabase;
    private TextInputEditText textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_text);
        textView = findViewById(R.id.textview22);
   findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           mdatabase = FirebaseDatabase.getInstance().getReference().child("DATA").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
           String tv = textView.getText().toString();
           mdatabase.child("complaint").setValue(tv);
           Toast.makeText(getApplicationContext(),"Complaint Registered",Toast.LENGTH_SHORT).show();
           startActivity(new Intent(getApplicationContext(),Dashboard.class));
           finish();
       }
   });

    }
}