package com.example.immoloc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.immoloc.database.AppDatabase;
import com.example.immoloc.database.User;
import com.example.immoloc.database.UserDao;

public class Login extends AppCompatActivity {

    TextView mdpOublié, inscription;
    EditText mail, password;
    AppDatabase locImmoDatabase;
    Button connexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Au clic sur le bouton connexion ...
        connexion = findViewById(R.id.connectionBtnLogin);
        connexion.setOnClickListener(view -> {

            // On récupère l'input de l'utilisateur
            mail = findViewById(R.id.editTextTextEmailAddress);
            password = findViewById(R.id.editTextTextPassword);
            String mailText = mail.getText().toString();
            String passwordText = password.getText().toString();

            if(mailText.isEmpty() || passwordText.isEmpty()){
                Toast.makeText(this, "Vous devez remplir les champs pour vous connecter" +
                        "", Toast.LENGTH_LONG).show();

            // On performe notre requête (login)
            } else {
                locImmoDatabase = AppDatabase.getInstance(this);
                UserDao userDao = locImmoDatabase.userDao();
                new Thread(() -> {
                    User user = userDao.login(mailText, passwordText);
                    if(user == null){
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Identifiants incorrects", Toast.LENGTH_LONG).show());
                    } else {
                        String userName = userDao.findName(mailText, passwordText);
                        Intent i = new Intent(Login.this, HomeActivity.class);
                        i.putExtra("getUN",userName);
                        i.putExtra("getUserId", user.getId());
                        //runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Id: "+user.getId(), Toast.LENGTH_LONG).show());
                        startActivity(i);
                    }
                }).start();
            }
        });

        // Au clic sur mot de passe oublié
        mdpOublié = findViewById(R.id.passwordForgotter);
        mdpOublié.setOnClickListener(view -> {
            Intent redirection = new Intent(this, ForgotPassword.class);
            startActivity(redirection);
        });

        // Au clic sur s'inscrire
        inscription = findViewById(R.id.signupBtn);
        inscription.setOnClickListener(view -> {
            Intent redirection = new Intent(this, Signup.class);
            startActivity(redirection);
        });
    }
}