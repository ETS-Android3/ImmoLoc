package com.example.immoloc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.immoloc.database.AdDao;
import com.example.immoloc.database.AdTable;
import com.example.immoloc.database.AppDatabase;

public class DetailsAdActivity extends AppCompatActivity {

    int myAdId, myUserId;
    AdDao adDao;
    AppDatabase locImmoDatabase;
    TextView price, date_debut, date_fin, surface, nbRooms, nbWaterRooms, nbBedrooms, adress,
            description, cityName, zipcode, typeDeBien;
    AdTable ad;
    String getCityName, getZipCode, getTypeDeBien;
    Button contacter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_annonce);

        // Je récupère l'identifiant de l'annonce envoyé par l'activité AdsListAdapter
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            myAdId = extras.getInt("adId");
            myUserId = extras.getInt("userId");
        }

        locImmoDatabase = AppDatabase.getInstance(this);
        adDao = locImmoDatabase.adDao();

        nbRooms = findViewById(R.id.details_pieces);
        nbWaterRooms = findViewById(R.id.details_salleDeau);
        nbBedrooms = findViewById(R.id.details_bedrooms);
        price = findViewById(R.id.details_price);
        date_debut = findViewById(R.id.details_date_debut);
        date_fin = findViewById(R.id.details_date_fin);
        surface = findViewById(R.id.details_surface);
        adress = findViewById(R.id.details_address);
        description = findViewById(R.id.details_description);
        cityName = findViewById(R.id.details_city);
        zipcode = findViewById(R.id.details_zipcode);
        typeDeBien = findViewById(R.id.details_type);

        // On remplit les détails de l'annonce
        ad = AdTable.getAd(myAdId, this); // Je récupère toutes les informations d'une annonce à partir de son id

        price.setText(String.valueOf(ad.getPrice())+"€");
        date_debut.setText(ad.getDateDebut());
        date_fin.setText(ad.getDateFin());
        surface.setText(String.valueOf(ad.getSurface()+"m²"));
        nbRooms.setText(ad.getNbChambres());
        nbWaterRooms.setText(ad.getNbSalleDeau());
        nbBedrooms.setText(ad.getNbChambres());
        adress.setText(ad.getAdresse());
        description.setText(ad.getText());

        // On récupère d'autres champs à partir de requêtes (souvent liés à d'autres tables)
        getTypeDeBien = adDao.getTypeDeBien(myAdId);
        typeDeBien.setText(getTypeDeBien);
        getCityName = adDao.getCityName(myAdId);
        cityName.setText(getCityName);
        getZipCode = adDao.getZipCode(myAdId);
        zipcode.setText(getZipCode);

        String getPhoneNb = adDao.getPhoneNb(myUserId);

        // Au clic sur le bouton contacter...
        contacter = findViewById(R.id.buttonContactOwner);
        contacter.setOnClickListener(view -> {
                Uri uri = Uri.parse("tel:"+getPhoneNb); // string du phone
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
        });

    }
}
