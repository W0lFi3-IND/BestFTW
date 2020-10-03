package com.ibm.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class login extends AppCompatActivity {
    private Button sendOtp;
    private EditText phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sendOtp = findViewById(R.id.sendOTP);
        phoneNumber = findViewById(R.id.editTextPhone);

        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneNumber.getText().toString().isEmpty()){
                    phoneNumber.setError("Please enter");
                    phoneNumber.requestFocus();
                    return;
                }

                String userPhoneNumber = phoneNumber.getText().toString();
                if(!userPhoneNumber.startsWith("+91")){
                    userPhoneNumber = "+91"+userPhoneNumber;
                }

                Intent intent = new Intent(login.this,OtpConfirmation.class);
                intent.putExtra("phonenumber", userPhoneNumber);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }
}