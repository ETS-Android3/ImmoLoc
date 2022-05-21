package com.example.immoloc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.immoloc.adapter.AdsListAdapter;
import com.example.immoloc.adapter.AdsViewModel;
import com.example.immoloc.adapter.DeleteAdActivity;
import com.example.immoloc.database.AdDao;
import com.example.immoloc.database.AdTable;
import com.example.immoloc.database.AppDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MyAdsActivity extends AppCompatActivity {

    public static final int DELETE_AD_ACTIVITY_REQUEST_CODE = 2;
    private AdsViewModel myAdsViewModel;
    AppDatabase locImmoDatabase;
    AdDao adDao;
    List<AdTable> ads;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ads);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        locImmoDatabase = AppDatabase.getInstance(this);
        adDao = locImmoDatabase.adDao();

        // Je récupère toutes les annonces pour les passer à mon adapter
        ads = adDao.getAll();
        final AdsListAdapter adapter = new AdsListAdapter(new AdsListAdapter.AdDiff(), ads);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdsViewModel = new ViewModelProvider(this).get(AdsViewModel.class);
        myAdsViewModel.getAllAds().observe(this, ads -> {
            adapter.submitList(ads);
        });


        // Au clic sur la supression d'un mot
        FloatingActionButton fab2 = findViewById(R.id.fabb);
        fab2.setOnClickListener(view -> {
            Intent intent = new Intent(this, DeleteAdActivity.class);
            startActivityForResult(intent, DELETE_AD_ACTIVITY_REQUEST_CODE);
        });


    }// fin onCreate

        public void onActivityResult(int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == DELETE_AD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
                myAdsViewModel.delete(data.getStringExtra(DeleteAdActivity.EXTRA_REPLY));
                Toast.makeText(getApplicationContext(), "Annonce supprimée", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
            }
        }

}
