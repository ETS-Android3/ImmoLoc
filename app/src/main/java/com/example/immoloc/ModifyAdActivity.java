package com.example.immoloc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.immoloc.database.AdDao;
import com.example.immoloc.database.AdTable;
import com.example.immoloc.database.AppDatabase;
import com.example.immoloc.database.User;
import com.google.android.material.textfield.TextInputEditText;

public class ModifyAdActivity extends AppCompatActivity {

    TextInputEditText surface, prix, nbWaterRooms, nbRooms, nbBedrooms, adresse,
            ville, zipCode, owner, description, dateDebut, dateFin;
    Button modifyTheAd;
    AdDao adDao;
    AppDatabase locImmoDatabase;
    int myAdId;
    AdTable ad;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_ad);

        // Je récupère l'identifiant de l'annonce envoyé par l'activité AdsListAdapter
        Bundle extras = getIntent().getExtras();
        if (extras != null) { myAdId = extras.getInt("adId"); }

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

        // Préfill le formulaire avec ce qu'il y a déjà dans la base de données
        ad = AdTable.getAd(myAdId, this); // Je récupère toutes les informations d'une annonce à partir de son id
        description.setText(ad.getText());
        dateDebut.setText(ad.getDateDebut());
        dateFin.setText(ad.getDateFin());
        prix.setText(String.valueOf(ad.getPrice()));
        surface.setText(String.valueOf(ad.getSurface()));
        // Ville: on a que l'id donc faire le lien avec l'autre table
        // Adresse: n'existe pas encore dans la bdd


        // Au clic sur la modification de l'annonce
        modifyTheAd = findViewById(R.id.add_the_estate_confirm_btn);
        modifyTheAd.setOnClickListener(view -> {

            // On récupère les champs modifiés (ou non) par l'utilisateur
            String getDateDebut = dateDebut.getText().toString();
            String getDateFin = dateFin.getText().toString();
            String getDescription = description.getText().toString();
            String getPrix = prix.getText().toString();
            String getSurface = surface.getText().toString();

            // Mise à jour de l'annonce
            adDao.updateMyAd(String.valueOf(myAdId), getDateDebut, getDateFin, getDescription,
                    getPrix, getSurface);

            Toast.makeText(this, "Votre annonce a bien été modifiée", Toast.LENGTH_SHORT).show();
            finish();
        });

    }

}