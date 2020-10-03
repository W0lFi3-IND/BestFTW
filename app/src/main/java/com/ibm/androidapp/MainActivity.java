package com.ibm.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiHandler db= new apiHandler(this);
        Log.d("Insert: ", "Inserting ");
        db.addApi(new apiStorage(1, "API 1"));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startact();
            }
        },3000);
    }

    void startact() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(this,Dashboard.class));
            finish();
        }
        else {
            startActivity(new Intent(this, login.class));
            finish();
        }
    }


}
