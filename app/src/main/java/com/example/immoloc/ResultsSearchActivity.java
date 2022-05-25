package com.example.immoloc;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.immoloc.adapter.AdsViewModel;
import com.example.immoloc.adapter.RecyclerAdapter;
import com.example.immoloc.database.AdDao;
import com.example.immoloc.database.AdTable;
import com.example.immoloc.database.AppDatabase;

import java.util.List;

public class ResultsSearchActivity extends AppCompatActivity {

    private AdsViewModel myAdsViewModel;
    public List<AdTable> filteredAds;
    public String prixMin, prixMax;
    public AppDatabase locImmoDatabase;
    public AdDao adDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_search);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewResultsSearch);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            prixMin = extras.getString("prixmin");
            prixMax = extras.getString("prixmax");
            //Log.d("petitdebug", "voir valeur : "+prixMin+"et"+prixMax);
        }

        locImmoDatabase = AppDatabase.getInstance(this);
        adDao = locImmoDatabase.adDao();
        filteredAds = adDao.searchAnAd(prixMin, prixMax);


        // mettre le résultat de l'intent dans filteredAd.
        final RecyclerAdapter adapter = new RecyclerAdapter(new RealEstateListing.AdDiffTwo(), filteredAds);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(null);

        myAdsViewModel = new ViewModelProvider(this).get(AdsViewModel.class);
        myAdsViewModel.getAllAds().observe(this, ads -> {
            adapter.submitList(ads);
        });


    }



}
