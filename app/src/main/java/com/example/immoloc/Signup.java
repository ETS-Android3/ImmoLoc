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

public class Signup extends AppCompatActivity {

    TextView alreadyHaveAcc;
    AppDatabase locImmoDatabase;
    EditText email, password, firstName, lastName, phone;
    Button signup;
    boolean pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Intent mIntent = getIntent();
            pro = mIntent.getBooleanExtra("getUserTypeReg", false);
        }

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

            String getEmail = email.getText().toString();
            String getPass = password.getText().toString();
            String getFn = firstName.getText().toString();
            String getLn = lastName.getText().toString();
            String getPhone = phone.getText().toString();

            User user = new User();
            boolean mailExists = userDao.isRecordExistsMail(email.getText().toString());

            if(mailExists){
                Toast.makeText(this, "Un compte avec cet email existe déjà", Toast.LENGTH_SHORT).show();
            }
            if (getEmail.isEmpty() | getPass.isEmpty() | getFn.isEmpty() | getLn.isEmpty() | getPhone.isEmpty()){
                Toast.makeText(this, "Tous les champs sont obligatoires. Merci de les remplir", Toast.LENGTH_LONG).show();
                //Toast.makeText(this, "voir: " +getEmail + getPass + getFn +  getLn + getPhone, Toast.LENGTH_LONG).show();

            }
            else {
                user.setEmail(getEmail);
                user.setPassword(getPass);
                user.setFirstName(getFn);
                user.setLastName(getLn);
                user.setPhone(getPhone);
                user.setProfesional(pro);
                userDao.insert(user);
                Toast.makeText(this, "Votre compte vient d'être créé", Toast.LENGTH_LONG).show();
                Intent redir = new Intent(this, Login.class);
                startActivity(redir);
            }
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