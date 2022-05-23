package com.example.immoloc;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.immoloc.database.AdDao;
import com.example.immoloc.database.AdTable;
import com.example.immoloc.database.AppDatabase;

public class DetailsAdActivity extends AppCompatActivity {

    int myAdId;
    AdDao adDao;
    AppDatabase locImmoDatabase;
    TextView price, date_debut, date_fin;
    AdTable ad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_annonce);

        // Je récupère l'identifiant de l'annonce envoyé par l'activité AdsListAdapter
        Bundle extras = getIntent().getExtras();
        if (extras != null) { myAdId = extras.getInt("adId"); }


        locImmoDatabase = AppDatabase.getInstance(this);
        adDao = locImmoDatabase.adDao();

        price = findViewById(R.id.details_price);
        date_debut = findViewById(R.id.details_date_debut);
        date_fin = findViewById(R.id.details_date_fin);

        // On remplit les détails de l'annonce
        ad = AdTable.getAd(myAdId, this); // Je récupère toutes les informations d'une annonce à partir de son id
        price.setText(String.valueOf(ad.getPrice())+"€");
        date_debut.setText(ad.getDateDebut());
        date_fin.setText(ad.getDateFin());
        

    }
}
