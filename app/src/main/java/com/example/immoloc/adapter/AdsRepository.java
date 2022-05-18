package com.example.immoloc.adapter;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.immoloc.database.AdDao;
import com.example.immoloc.database.AdTable;
import com.example.immoloc.database.AppDatabase;

import java.util.List;

public class AdsRepository {

    private AdDao myAdDao;
    private LiveData<List<AdTable>> allAds;


    AdsRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        myAdDao = db.adDao();
        allAds = myAdDao.getAllAds();
    }


    LiveData<List<AdTable>> getAllAds() {
        return allAds;
    }


    void insert(AdTable ad) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            myAdDao.insert(ad);
        });
    }

    /*
    void delete(Ad ad) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            myAdDao.deleteparticularad(ad);
        });
    }*/
}
