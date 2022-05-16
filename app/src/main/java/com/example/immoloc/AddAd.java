package com.example.immoloc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.immoloc.database.AdTable;
import com.example.immoloc.database.AdDao;
import com.example.immoloc.database.AppDatabase;
import com.example.immoloc.database.ImageDao;
import com.example.immoloc.database.ImageTable;
import com.example.immoloc.database.User;
import com.example.immoloc.database.UserDao;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.InputStream;


public class AddAd extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageButton ajouterPhotoCamera, ajouterPhotoGallerie;
    final int CAMERA_INTENT = 51;
    private final int GALLERY_CODE = 1000;
    MaterialButton uploadAdd;
    Bitmap bmpImg;
    Uri uri;
    AppDatabase locImmoDatabase; // instance de la bdd
    ImageDao imgDao;
    AdDao adDao;
    ImageView imView;
    TextInputEditText surface, prix, nbWaterRooms, nbRooms, nbBedrooms, adresse,
                      ville, zipCode, owner, description, dateDebut, dateFin;
    String getUserName, getUserId;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ad);

        bmpImg = null;
        locImmoDatabase = AppDatabase.getInstance(this);
        imgDao = locImmoDatabase.imgDao();
        adDao = locImmoDatabase.adDao();
        imView = findViewById(R.id.userImage);

        // On récupère quelques champs de la page d'ajout d'annonce
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

        /* On récupère le nom de l'utilisateur que l'Activité HomeActivity nous a envoyé
        pour l'autofill dans le champ du formulaire adéquat. L'utilisateur pourra le modifier au choix. */
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            getUserName = extras.getString("getUN");
            getUserId = extras.getString("userId");
            owner.setText(getUserName);
        }


        // Au clic sur le bouton-icône gallerie
        ajouterPhotoGallerie = findViewById(R.id.add_estate_load_from_gallery_btn);
        ajouterPhotoGallerie.setOnClickListener(view -> {
            Intent iGallery = new Intent(Intent.ACTION_PICK);
            iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(iGallery, GALLERY_CODE);
        });

        // Déposer l'annonce entière
        uploadAdd = findViewById(R.id.add_the_estate_confirm_btn);
        uploadAdd.setOnClickListener(view -> {
            saveAd(view);
            Toast.makeText(this, "Annonce uploadée!", Toast.LENGTH_SHORT).show();
        });


        // Spinner (menu déroulant) pour choisir le type de bien
        Spinner spinner = (Spinner) findViewById(R.id.typeBien);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.typeDeBien, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        // Au clic sur le bouton-icône caméra
        ajouterPhotoCamera = findViewById(R.id.add_estate_load_from_camera_btn);
        ajouterPhotoCamera.setOnClickListener(view -> {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(i.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(i, CAMERA_INTENT);
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if (requestCode == GALLERY_CODE) {
                uri = data.getData();
                imView.setImageURI(data.getData());
            } if (requestCode == CAMERA_INTENT){
                bmpImg = (Bitmap) data.getExtras().get("data");
                if (bmpImg != null) {
                    imView.setImageBitmap(bmpImg);
                } else {
                    Toast.makeText(this, "Bitmap is null", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public boolean checkAndUploadImages(View view) {
        if (bmpImg == null) {
            //Toast.makeText(this, "Image manquante", Toast.LENGTH_SHORT).show();
            if (uri != null) {
                try {
                    ImageTable img = new ImageTable();
                    InputStream iStream = getContentResolver().openInputStream(uri);
                    byte[] inputData = DataConverter.getBytes(iStream);
                    img.setImage(inputData);
                    imgDao.insert(img);
                    return true;
                    //Toast.makeText(this, "URI SUCCEED", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            ImageTable img = new ImageTable();
            img.setImage(DataConverter.convertImg2ByteArray(bmpImg));
            imgDao.insert(img);
            return true;
            //Toast.makeText(this, "BMP IMG SUCCEED", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    public void saveAd(View view) {
        boolean correct = checkAndUploadImages(view);
        if (correct == true) {
            //save les champs de l'ad
            AdTable adTable = new AdTable();
            //prendre en cpt les FK
            //adTable.setUserId(getUserId);
            //adTable.setDateDebut(dateDebut.getText().toString());
            //adDao.insert(adTable);
            // Ferme l'activité et renvoi à l'activité précédente
            finish();
        } else {
            Toast.makeText(this, "Errors with the images", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        parent.getItemAtPosition(0);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
