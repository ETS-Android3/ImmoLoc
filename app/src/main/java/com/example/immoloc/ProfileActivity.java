package com.example.immoloc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.immoloc.database.AppDatabase;
import com.example.immoloc.database.User;
import com.example.immoloc.database.UserDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity {

    AppDatabase locImmoDatabase;
    ImageView imView, imViewBack;
    UserDao userDao;
    Bitmap bmpImg;
    Uri uri;
    int getUserId;
    private final int GALLERY_CODE = 75;
    FloatingActionButton addPic;
    TextView firstName, lastName;

    User user = new User();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imView = findViewById(R.id.userImage);
        imViewBack = findViewById(R.id.blurredUserImage);

        bmpImg = null;

        locImmoDatabase = AppDatabase.getInstance(this);
        userDao = locImmoDatabase.userDao();

        addPic = findViewById(R.id.ajouterPhoto);
        addPic.setOnClickListener(view -> {
            Intent iGallery = new Intent(Intent.ACTION_PICK);
            iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(iGallery, GALLERY_CODE);
        });

        /* Je récupère l'id de l'user connecté pour à partir de là extraire ses informations avec getUser()
        qui est définie dans la classe com.example.immoloc.database.User */
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Intent mIntent = getIntent();
            getUserId = mIntent.getIntExtra("userId", 0);
            user = User.getUser(getUserId, this);
        }

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());

        // On récupère l'image que l'user a déjà et on la compresse un peu
        byte[] userImage = DataConverter.imageResize(user.getUserImg());
        Bitmap bmp = BitmapFactory.decodeByteArray(userImage, 0, userImage.length);
        imView.setImageBitmap(Bitmap.createScaledBitmap(bmp, 500, 500, false));


    } // fin onCreate


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_CODE) {
                uri = data.getData();
                imView.setImageURI(data.getData());

                checkAndUploadImages();
            } else {
                Toast.makeText(this, "Bitmap is null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void checkAndUploadImages() {
        if (uri != null) {
            try {
                InputStream iStream = getContentResolver().openInputStream(uri);
                byte[] inputData = DataConverter.getBytes(iStream);
                inputData = DataConverter.imageResize(inputData);
                user.setUserImg(inputData);
                userDao.addPicture(inputData, user.getId());
                Toast.makeText(this, "Votre photo a bien été uploadée.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

