package com.example.immoloc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.immoloc.database.AppDatabase;
import com.example.immoloc.database.User;
import com.example.immoloc.database.UserDao;

public class Signup extends AppCompatActivity {

    TextView alreadyHaveAcc;
    AppDatabase locImmoDatabase;
    EditText email, password, firstName, lastName, phone;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // We create an instance of our database
        locImmoDatabase = AppDatabase.getInstance(this);

        /* We use the abstract method from the AppDatabase to get an instance of DAO.
        / We can use the methods from the DAO instance to interact with the database */
        UserDao userDao = locImmoDatabase.userDao();

        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        firstName = findViewById(R.id.editTextTextPersonFirstName);
        lastName = findViewById(R.id.editTextTextPersonLastName);
        phone = findViewById(R.id.editTextPhoneNb);

        // On sign up user click ...
        signup = findViewById(R.id.connectionBtnLogin);
        signup.setOnClickListener(view -> {
            User user = new User();

            user.setEmail(email.getText().toString());
            user.setPassword(password.getText().toString());
            user.setFirstName(firstName.getText().toString());
            user.setLastName(lastName.getText().toString());
            user.setPhone(phone.getText().toString());

            Log.d("debug", "check : "+email.getText().toString());
            Log.d("debug", "get email by method: "+ user.getEmail());

            userDao.insert(user);

            Toast.makeText(this, "Votre compte vient d'être créé", Toast.LENGTH_LONG).show();

            Intent redir = new Intent(this, Login.class);
            startActivity(redir);
            /* if one of the fields not given dont access to other page
             when created account, user will be redirected directly to home page
             but for now just a toast */

            // add functionality for valid password and valid email as well
        });

        // Redirection sur la page de Connexion si utilisateur déjà détenteur d'un compte
        alreadyHaveAcc = findViewById(R.id.passwordForgotter);
        alreadyHaveAcc.setOnClickListener(view -> {
            Intent redirection = new Intent(this, Login.class);
            startActivity(redirection);
        });
    }


}