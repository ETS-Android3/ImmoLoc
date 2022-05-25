package com.example.immoloc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.immoloc.adapter.AdsListAdapter;
import com.example.immoloc.adapter.AdsViewModel;
import com.example.immoloc.adapter.RecyclerAdapter;
import com.example.immoloc.database.AdDao;
import com.example.immoloc.database.AdTable;
import com.example.immoloc.database.AppDatabase;
import java.util.List;

public class RealEstateListing extends AppCompatActivity {

    private AdsViewModel myAdsViewModel;
    AppDatabase locImmoDatabase;
    AdDao adDao;
    List<AdTable> ads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realestate_listing);

        RecyclerView recyclerView = findViewById(R.id.myrecyclerview);

        locImmoDatabase = AppDatabase.getInstance(this);
        adDao = locImmoDatabase.adDao();


        // Je récupère toutes les annonces pour les passer à mon adapter
        ads = adDao.getAll();
        //final AdsListAdapter adapter = new AdsListAdapter(new AdsListAdapter.AdDiff(), ads);
        final RecyclerAdapter adapter = new RecyclerAdapter(new RealEstateListing.AdDiffTwo(), ads);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        myAdsViewModel = new ViewModelProvider(this).get(AdsViewModel.class);
        myAdsViewModel.getAllAds().observe(this, ads -> {
            adapter.submitList(ads);
        });


    }// fin onCreate

    public static class AdDiffTwo extends DiffUtil.ItemCallback<AdTable> {
        @Override
        public boolean areItemsTheSame(@NonNull AdTable oldItem, @NonNull AdTable newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull AdTable oldItem, @NonNull AdTable newItem) {
            return oldItem.getText().equals(newItem.getText());
        }
    }
}
