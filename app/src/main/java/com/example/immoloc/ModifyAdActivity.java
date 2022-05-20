package com.example.immoloc;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.immoloc.adapter.AdsListAdapter;
import com.example.immoloc.database.AdDao;
import com.example.immoloc.database.AdTable;
import com.example.immoloc.database.AppDatabase;
import com.google.android.material.textfield.TextInputEditText;

public class ModifyAdActivity extends AppCompatActivity implements AdsListAdapter.onAdListener {

    TextInputEditText surface, prix, nbWaterRooms, nbRooms, nbBedrooms, adresse,
            ville, zipCode, owner, description, dateDebut, dateFin;
    Button modifyTheAd;
    AdDao adDao;
    AppDatabase locImmoDatabase;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_ad);

        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        //String getIdCurrentUser =(mSharedPreference.getString("TheIdOfUser", "defaultValue"));
        String getIdAd = (mSharedPreference.getString("hey", "defaultValue"));
        Toast.makeText(this, "val getidad: "+getIdAd, Toast.LENGTH_SHORT).show();

        locImmoDatabase = AppDatabase.getInstance(this);
        adDao = locImmoDatabase.adDao();



        description = findViewById(R.id.descriptionAd);
        dateDebut = findViewById(R.id.dateDebut);
        dateFin = findViewById(R.id.dateFin);
        prix = findViewById(R.id.price);
        surface = findViewById(R.id.area);
        ville = findViewById(R.id.city);
        adresse = findViewById(R.id.adresse);
        zipCode = findViewById(R.id.zipCode);
        nbRooms = findViewById(R.id.nbBedrooms);
        nbWaterRooms = findViewById(R.id.nbWaterRooms);
        nbBedrooms = findViewById(R.id.nbBedrooms);
        owner = findViewById(R.id.owner);



        // Au clic sur la modification de l'annonce
        modifyTheAd = findViewById(R.id.add_the_estate_confirm_btn);
        modifyTheAd.setOnClickListener(view -> {
            String getDateDebut = dateDebut.getText().toString();
            //adDao.updateDateDebut(getDateDebut, getIdCurrentUser);
            // id de l'annonce et pas de l'user mais getid user peut etre utilisé pr pas qu'un autre modifie une annonce
            Toast.makeText(this, "Votre annonce a bien été modifiée", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public void onAdClick(int position) {
        // à définir
    }
}