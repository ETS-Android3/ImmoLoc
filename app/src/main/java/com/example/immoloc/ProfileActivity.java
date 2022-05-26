package com.example.immoloc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;

import com.example.immoloc.adapter.AdsListAdapter;
import com.example.immoloc.adapter.AdsViewModel;
import com.example.immoloc.database.AdDao;
import com.example.immoloc.database.AdTable;
import com.example.immoloc.database.AppDatabase;
import com.example.immoloc.database.ImageDao;
import com.example.immoloc.database.ImageTable;
import com.example.immoloc.database.User;
import com.example.immoloc.database.UserDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    AppDatabase locImmoDatabase;
    ImageView imView, imViewBack;
    UserDao userDao;
    AdDao adDao;
    ImageDao imgDao;
    Bitmap bmpImg;
    Uri uri;
    int getUserId;
    private final int GALLERY_CODE = 75;
    public static final int DELETE_AD_ACTIVITY_REQUEST_CODE = 10;
    FloatingActionButton addPic;
    Button deleteAd, deleteMyAccount;
    TextView firstName, lastName, statut;
    List<AdTable> ads;
    List<ImageTable> imgs;
    String valUserName;


    User user = new User();
    private AdsViewModel mWordViewModel;
    public Button mesAnnonces;
    public TextView search, profile, home;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imView = findViewById(R.id.userImage);
        imViewBack = findViewById(R.id.blurredUserImage);
        bmpImg = null;
        locImmoDatabase = AppDatabase.getInstance(this);
        userDao = locImmoDatabase.userDao();
        adDao = locImmoDatabase.adDao();
        imgDao = locImmoDatabase.imgDao();
        ads = adDao.getAll();
        imgs = imgDao.getAllImage();


        final AdsListAdapter adapter = new AdsListAdapter(new AdsListAdapter.AdDiff(), ads, imgs);

        mWordViewModel = new ViewModelProvider(this).get(AdsViewModel.class);
        mWordViewModel.getAllAds().observe(this, words -> {
            adapter.submitList(words);
        });

        addPic = findViewById(R.id.ajouterPhoto);
        addPic.setOnClickListener(view -> {
            Intent iGallery = new Intent(Intent.ACTION_PICK);
            iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(iGallery, GALLERY_CODE);
        });

        // Au clic sur mes annonces
        mesAnnonces = findViewById(R.id.voirMesAnnonces5);
        mesAnnonces.setOnClickListener(view -> {
            Intent redir = new Intent(this, MyAdsActivity.class);
            redir.putExtra("userId",getUserId);
            startActivity(redir);
        });

        // Supprimer mon compte
        deleteMyAccount = findViewById(R.id.btnDeleteMyAcc);
        deleteMyAccount.setOnClickListener(view -> {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage(R.string.deciderOui)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            // À définir si on valide
                            public void onClick(DialogInterface dialog, int id) {
                                // supprimer compte d'user courant
                                userDao.delete(user);
                                Toast.makeText(ProfileActivity.this, "Votre compte a bien été supprimé",
                                        Toast.LENGTH_SHORT).show();
                                Intent supr = new Intent(ProfileActivity.this, Welcome.class);
                                startActivity(supr);
                            }
                        });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
        });

        // Au clic sur le bouton recherche du footer
        search = findViewById(R.id.searchBtn);
        search.setOnClickListener(v -> {
            Intent redirection = new Intent(v.getContext(), SearchActivity.class);
            startActivity(redirection);
        });
        // Au clic sur le bouton accueil du footer
        home = findViewById(R.id.homeBtn);
        home.setOnClickListener(v -> {
            Intent redirection = new Intent(v.getContext(), HomeActivity.class);
            redirection.putExtra("getUN", valUserName);
            startActivity(redirection);
        });
        // Au clic sur le bouton profil du footer
        profile = findViewById(R.id.profileBtn);
        profile.setOnClickListener(v -> {
            Toast.makeText(this, "Vous êtes déjà sur votre profil", Toast.LENGTH_SHORT).show();
        });


        /*deleteAd = findViewById(R.id.deleteAdBtn);
        deleteAd.setOnClickListener(view -> {
            Intent redirection = new Intent(this, DeleteAdActivity.class);
            startActivityForResult(redirection, DELETE_AD_ACTIVITY_REQUEST_CODE);
        });*/


        /* Je récupère l'id de l'user connecté pour à partir de là extraire ses informations avec getUser()
        qui est définie dans la classe com.example.immoloc.database.User */
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Intent mIntent = getIntent();
            getUserId = mIntent.getIntExtra("userId", 0);
            valUserName = mIntent.getStringExtra("getUN");
            user = User.getUser(getUserId, this);
        }

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());

        // S'il en a une, on récupère l'image de l'user et on la compresse un peu
        if (user.getUserImg() != null) {
            byte[] userImage = DataConverter.imageResize(user.getUserImg());
            Bitmap bmp = BitmapFactory.decodeByteArray(userImage, 0, userImage.length);
            imView.setImageBitmap(Bitmap.createScaledBitmap(bmp, 500, 500, false));
        }

        // Affichage du type d'inscription effectué (pro ou particulier)
        statut = findViewById(R.id.statut);
        if (user.isProfesional()){
            statut.setText("Statut: Profesionnel");
        } else {
            statut.setText("Statut: Particulier");
        }

    } // fin onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
           /* if (requestCode == DELETE_AD_ACTIVITY_REQUEST_CODE) {
                addViewModel.delete(data.getStringExtra(DeleteAdActivity.EXTRA_REPLY));
                Toast.makeText(this, "annonce: "+data.getStringExtra(DeleteAdActivity.EXTRA_REPLY)+" bien supprimée.", Toast.LENGTH_SHORT).show();
            }*/
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

