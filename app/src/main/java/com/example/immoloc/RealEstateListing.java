package com.example.immoloc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.immoloc.adapter.AdsListAdapter;
import com.example.immoloc.adapter.AdsViewModel;
import com.example.immoloc.database.AdDao;
import com.example.immoloc.database.AdTable;
import com.example.immoloc.database.AppDatabase;
import java.util.List;

public class RealEstateListing extends AppCompatActivity {

    private AdsViewModel myAdsViewModel;
    AppDatabase locImmoDatabase;
    AdDao adDao;
    List<AdTable> ads;
    ImageView btnModif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realestate_listing);

        RecyclerView recyclerView = findViewById(R.id.myrecyclerview);

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


    }// fin onCreate

}
