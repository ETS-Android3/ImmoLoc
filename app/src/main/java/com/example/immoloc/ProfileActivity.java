package com.example.immoloc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.immoloc.database.AppDatabase;
import com.example.immoloc.database.ImageTable;
import com.example.immoloc.database.ImageDao;

public class ProfileActivity extends AppCompatActivity {

    AppDatabase locImmoDatabase;
    ImageView imView;
    ImageDao imgDao;
    Bitmap bmpImg;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imView = findViewById(R.id.userImage);
        bmpImg = null;

        locImmoDatabase = AppDatabase.getInstance(this);
        imgDao = locImmoDatabase.imgDao();


    }

    final int CAMERA_INTENT = 51;

    public void takePicture(View view){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(i.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(i, CAMERA_INTENT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CAMERA_INTENT:
                //if (requestCode == Activity.RESULT_OK){
                    bmpImg = (Bitmap) data.getExtras().get("data");
                    if (bmpImg != null) {
                        imView.setImageBitmap(bmpImg);
                    } else {
                        Toast.makeText(this, "Bitmap is null", Toast.LENGTH_SHORT).show();
                    }
                } //else {
               //     Toast.makeText(this, "Result not ok", Toast.LENGTH_SHORT).show();
             //   }
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


}

