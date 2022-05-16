package com.example.immoloc;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.example.immoloc.database.AppDatabase;
import com.example.immoloc.database.ImageDao;
import com.example.immoloc.database.ImageTable;
import com.google.android.material.button.MaterialButton;


public class AddAd extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageButton ajouterPhotoCamera, ajouterPhotoGallerie;
    final int CAMERA_INTENT = 51;
    private final int GALLERY_CODE = 1000;
    MaterialButton uploadAdd;
    Bitmap bmpImg;
    AppDatabase locImmoDatabase;
    ImageDao imgDao;
    ImageView imView;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ad);

        bmpImg = null;
        locImmoDatabase = AppDatabase.getInstance(this);
        imgDao = locImmoDatabase.imgDao();
        imView = findViewById(R.id.userImage);

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
            savePhoto(view);
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

    public void savePhoto(View view) {
        if (bmpImg == null) {
            Toast.makeText(this, "Image manquante", Toast.LENGTH_SHORT).show();
        } else {
            ImageTable img = new ImageTable();
            img.setImage(DataConverter.convertImg2ByteArray(bmpImg));
            imgDao.insert(img);
            Toast.makeText(this, "Image uploadée!", Toast.LENGTH_SHORT).show();
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
