package com.example.immoloc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.immoloc.database.AppDatabase;
import com.example.immoloc.database.User;
import com.example.immoloc.database.UserDao;

public class ForgotPassword extends AppCompatActivity {

    EditText mail, mail2, pass1, pass2, firstETmail;
    AppDatabase locImmoDatabase;
    Button resetPassword, confirmReset;
    TextView email, mdp, confirm, firstMail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_forgotten);

        // Premier bouton pour le changement de mot de passe
        resetPassword = findViewById(R.id.btnResetPass);

        // Au clic sur le bouton de réinitialisation du mot de passe
        resetPassword.setOnClickListener(view -> {

            firstETmail = findViewById(R.id.textEmailMdpForgotten);

            String mailText = firstETmail.getText().toString();

            if(mailText.isEmpty()){
                Toast.makeText(this, "Vous devez saisir votre email"
                        , Toast.LENGTH_LONG).show();
            } else {
                locImmoDatabase = AppDatabase.getInstance(this);
                UserDao userDao = locImmoDatabase.userDao();
                new Thread(() -> {
                    User user = userDao.findEmail(mailText);
                    if(user == null){
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Cet email n'existe pas", Toast.LENGTH_SHORT).show());
                    } else {
                        email = findViewById(R.id.text2EmailFgt);
                        mail2 = findViewById(R.id.secEmailTextFgt);
                        mdp = findViewById(R.id.passwordTxtFgt);
                        pass1 = findViewById(R.id.firstPasswordFgt);
                        pass2 = findViewById(R.id.secondPasswordFgt);
                        confirm = findViewById(R.id.passwordTxtFgt2);
                        confirmReset = findViewById(R.id.btnResetPass2);
                        firstMail = findViewById(R.id.text1EmailFgt);

                        // Thread original qui touche à ses vues
                        runOnUiThread(() -> {

                            // Nouveau formulaire apparaît
                            email.setVisibility(View.VISIBLE);
                            mail2.setVisibility(View.VISIBLE);
                            mdp.setVisibility(View.VISIBLE);
                            pass1.setVisibility(View.VISIBLE);
                            confirm.setVisibility(View.VISIBLE);
                            pass2.setVisibility(View.VISIBLE);
                            confirmReset.setVisibility(View.VISIBLE);

                            // Premier formulaire disparaît
                            resetPassword.setVisibility(View.INVISIBLE);
                            firstMail.setVisibility(View.INVISIBLE);
                            firstETmail.setVisibility(View.INVISIBLE);

                            // Bouton final pour la modification du mot de passe
                            confirmReset.setOnClickListener(view1 -> {

                                String password1 = pass1.getText().toString();
                                String confirm_password =  pass2.getText().toString();
                                String mailSecondForm = mail2.getText().toString();
                                User user1 = userDao.findEmail(mailSecondForm);
                                if (password1.isEmpty() | confirm_password.isEmpty() | mailSecondForm.isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Vous devez remplir tous les champs", Toast.LENGTH_SHORT).show();
                                } else {
                                    // si l'email existe
                                    if (user1 != null) {
                                        if (password1.equals(confirm_password)) {
                                            userDao.updatePassword(password1, mailSecondForm);
                                            Toast.makeText(getApplicationContext(), "Votre mot de passe a bien été changé.", Toast.LENGTH_LONG).show();
                                            Intent redir = new Intent(ForgotPassword.this, Login.class);
                                            startActivity(redir);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Les mots de passe ne correspondent pas. Veuillez rentrer les mêmes mots de passe.", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Cet email n'existe pas", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        });
                    }
                }).start();
            }
        });
    }

}

