package com.example.immoloc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.immoloc.database.AppDatabase;

public class MainActivity extends AppCompatActivity {

    Button connection, registration, showProperties;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*
        Logique du type d'inscription à définir (profesionnel ou particulier***
         */


        /* Home part */

        // Voir les biens immobiliers
        showProperties = findViewById(R.id.showPropertiesBtn);
        showProperties.setOnClickListener(view -> {
            Intent redirection = new Intent(this, RealEstateListing.class);
            startActivity(redirection);
        });

        // Se connecter
        connection = findViewById(R.id.connectionBtnHome);
        connection.setOnClickListener(view -> {
            Intent redirection = new Intent(this, Login.class);
            startActivity(redirection);
        });

        // S'inscrire
        registration = findViewById(R.id.signUpBtnHome);
        registration.setOnClickListener(view -> {
            Intent redirection = new Intent(this, RegistrationType.class);
            startActivity(redirection);
        });

    }
}