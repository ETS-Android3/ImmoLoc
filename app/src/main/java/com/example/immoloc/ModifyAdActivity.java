package com.example.immoloc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.immoloc.database.AdDao;
import com.example.immoloc.database.AdTable;
import com.example.immoloc.database.AppDatabase;
import com.example.immoloc.database.User;
import com.google.android.material.textfield.TextInputEditText;

public class ModifyAdActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextInputEditText surface, prix, nbWaterRooms, nbRooms, nbBedrooms, adresse,
            ville, zipCode, owner, description, dateDebut, dateFin, title;
    Button modifyTheAd;
    AdDao adDao;
    AppDatabase locImmoDatabase;
    int myAdId;
    AdTable ad;
    String getCityName, getZipCode;
    // Type d'appartement choisi avec le spinner et qui est intialisé par onItemSelected()
    String getType;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_ad);

        // Je récupère l'identifiant de l'annonce envoyé par l'activité AdsListAdapter
        Bundle extras = getIntent().getExtras();
        if (extras != null) { myAdId = extras.getInt("adId"); }

        Spinner spinner = (Spinner) findViewById(R.id.typeBien);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.typeDeBien, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

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
        nbRooms = findViewById(R.id.nbRooms);
        nbWaterRooms = findViewById(R.id.nbWaterRooms);
        nbBedrooms = findViewById(R.id.nbBedrooms);
        title = findViewById(R.id.titleAd_modify);
        //owner = findViewById(R.id.owner);

        getCityName = adDao.getCityName(myAdId);
        ville.setText(getCityName);
        getZipCode = adDao.getZipCode(myAdId);
        zipCode.setText(getZipCode);

        // Préfill le formulaire avec ce qu'il y a déjà dans la base de données
        ad = AdTable.getAd(myAdId, this); // Je récupère toutes les informations d'une annonce à partir de son id
        description.setText(ad.getText());
        dateDebut.setText(ad.getDateDebut());
        dateFin.setText(ad.getDateFin());
        prix.setText(String.valueOf(ad.getPrice()));
        surface.setText(String.valueOf(ad.getSurface()));
        ville.setText(getCityName);
        zipCode.setText(getZipCode);
        nbRooms.setText(ad.getNbPieces());
        nbWaterRooms.setText(ad.getNbSalleDeau());
        nbBedrooms.setText(ad.getNbChambres());
        adresse.setText(ad.getAdresse());
        title.setText(ad.getTitle());

        // Au clic sur la modification de l'annonce
        modifyTheAd = findViewById(R.id.add_the_estate_confirm_btn);
        modifyTheAd.setOnClickListener(view -> {

            // On récupère les champs modifiés (ou non) par l'utilisateur
            String getDateDebut = dateDebut.getText().toString();
            String getDateFin = dateFin.getText().toString();
            String getDescription = description.getText().toString();
            String getPrix = prix.getText().toString();
            String getSurface = surface.getText().toString();
            String getNbRooms = nbRooms.getText().toString();
            String getNbWaterRooms = nbWaterRooms.getText().toString();
            String getNbBedrooms = nbBedrooms.getText().toString();
            String getAdresse = adresse.getText().toString();
            // faire jointure dans la req d'update pour update ville et code postal

            // Mise à jour de l'annonce
            adDao.updateMyAd(String.valueOf(myAdId), getDateDebut, getDateFin, getDescription,
                    getPrix, getSurface, getNbRooms, getAdresse, getNbWaterRooms, getNbBedrooms);

            Toast.makeText(this, "Votre annonce a bien été modifiée", Toast.LENGTH_SHORT).show();
            finish();
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        getType = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}