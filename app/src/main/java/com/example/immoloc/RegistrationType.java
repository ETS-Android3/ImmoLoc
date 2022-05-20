package com.example.immoloc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


public class RegistrationType extends AppCompatActivity {

    public Button registrationPart, registrationPro;
    public boolean pro = false; // l'utilisateur s'inscrit t-il en tant que profesionnel ?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_type);

        // Profesionnel
        registrationPro = findViewById(R.id.professionalBtn);
        registrationPro.setOnClickListener(view -> {
            Intent redirection = new Intent(this, Signup.class);
            pro = true;
            redirection.putExtra("getUserTypeReg",pro);
            startActivity(redirection);
        });

        // Particulier
        registrationPart = findViewById(R.id.partiBtn);
        registrationPart.setOnClickListener(view -> {
            Intent redirection = new Intent(this, Signup.class);
            startActivity(redirection);
        });


    }


}