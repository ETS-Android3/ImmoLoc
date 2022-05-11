package com.example.immoloc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


public class RegistrationType extends AppCompatActivity {

    Button registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_type);

        registration = findViewById(R.id.partiBtn);
        registration.setOnClickListener(view -> {
            Intent redirection = new Intent(this, Signup.class);
            startActivity(redirection);
        });
    }


}