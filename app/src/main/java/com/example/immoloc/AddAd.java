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
import com.example.immoloc.database.Category;
import com.example.immoloc.database.CategoryDao;
import com.example.immoloc.database.City;
import com.example.immoloc.database.CityDao;
import com.example.immoloc.database.ImageDao;
import com.example.immoloc.database.ImageTable;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.InputStream;


public class AddAd extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageButton ajouterPhotoCamera, ajouterPhotoGallerie;
    final int CAMERA_INTENT = 51;
    private final int GALLERY_CODE = 1000;
    MaterialButton uploadAdd;
    Bitmap bmpImg;
    public Uri uri;
    ImageView imView;
    TextInputEditText surface, prix, nbWaterRooms, nbRooms, nbBedrooms, adresse,
                      ville, zipCode, owner, description, dateDebut, dateFin, title;
    String getUserName;
    int getUserId;
    String getType; // Type d'appartement choisi avec le spinner et qui est intialisé par onItemSelected()
    AppDatabase locImmoDatabase; // instance de la bdd
    // Dao's
    ImageDao imgDao;
    AdDao adDao;
    CategoryDao catDao;
    CityDao cityDao;


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
        title = findViewById(R.id.titleAd);

        /* On récupère le nom de l'utilisateur que l'Activité HomeActivity nous a envoyé
        pour l'autofill dans le champ du formulaire adéquat. L'utilisateur pourra le modifier au choix. */
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            getUserName = extras.getString("getUN");
            Intent mIntent = getIntent();
            getUserId = mIntent.getIntExtra("userId", 0);
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
    } // fin onCreate

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
            if (uri != null) { // galerie
                try {
                    ImageTable img = new ImageTable();
                    InputStream iStream = getContentResolver().openInputStream(uri);
                    byte[] inputData = DataConverter.getBytes(iStream);
                    inputData = DataConverter.imageResize(inputData); // on resize et compresse l'image avant
                    img.setImage(inputData);
                    imgDao.insert(img);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else { // cam
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

            String getSurface = surface.getText().toString();
            String codePostal = zipCode.getText().toString();
            String getPrix = prix.getText().toString();
            String getNbRooms = nbRooms.getText().toString();
            String getNbWR = nbWaterRooms.getText().toString();
            String getNbBedrooms = nbBedrooms.getText().toString();
            String getAdr = adresse.getText().toString();
            String getCity = ville.getText().toString();
            String getDateDebut = dateDebut.getText().toString();
            String getDateFin = dateFin.getText().toString();
            String getDesc = description.getText().toString();
            String getTitle = title.getText().toString();

            // Pour faire des tests plus rapidement, après on va requérir plus de champs, voire la totalité.
            // Et de toute façon, comme on cast les champs surface et prix en entier, on  a une exception si on
            // ne remplit pas ces champs (ça sera une chaîne vide), donc gérer cela
            if (getSurface.isEmpty() | getPrix.isEmpty() | getCity.isEmpty() | getDateDebut.isEmpty()
                | getDateFin.isEmpty() | getDesc.isEmpty() | getTitle.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir au moins les champs suivants:\n-Surface\n-Prix" +
                        "\n-Ville\n-Date début\n-Date fin\n-Description\n-Title", Toast.LENGTH_LONG).show();
            } else {
                catDao = locImmoDatabase.catDao();
                cityDao = locImmoDatabase.cityDao();

                /* Category table */
                Category cat = new Category();
                cat.setCategoryType(getType);
                catDao.insert(cat);
                int idCat = catDao.getIdByType(getType);

                /* City table */
                City city = new City();
                city.setName(getCity);
                city.setPriceLoc(Integer.parseInt(getPrix));
                city.setZipCode(codePostal);
                cityDao.insert(city);
                int idCity = cityDao.getIdByName(getCity);


                /* Ad table */
                AdTable adTable = new AdTable();
                adTable.setUserId(getUserId);
                adTable.setCategoryId(idCat);
                adTable.setCityId(idCity);
                adTable.setNbSalleDeau(getNbWR);
                adTable.setNbChambres(getNbBedrooms);
                adTable.setNbPieces(getNbRooms);
                adTable.setAdresse(getAdr);
                adTable.setText(getDesc);
                adTable.setDateDebut(getDateDebut);
                adTable.setDateFin(getDateFin);
                adTable.setPrice(Integer.parseInt(getPrix));
                adTable.setSurface(Integer.parseInt(getSurface));
                adTable.setDateDebut(dateDebut.getText().toString());
                adTable.setTitle(getTitle);
                adDao.insert(adTable);

                // Ferme l'activité et renvoi à l'activité précédente
                Toast.makeText(this, "Annonce uploadée!", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Veuillez ajouter une image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        getType = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
