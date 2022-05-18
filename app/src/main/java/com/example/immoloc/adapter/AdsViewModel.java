package com.example.immoloc.adapter;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.immoloc.database.AdTable;

import java.util.List;

public class AdsViewModel extends AndroidViewModel {

    private AdsRepository mRepository;

    private final LiveData<List<AdTable>> allWords;

    public AdsViewModel(Application application) {
        super(application);
        mRepository = new AdsRepository(application);
        allWords = mRepository.getAllAds();
    }

    public LiveData<List<AdTable>> getAllAds() {
        return allWords;
    }

    void insert(AdTable ad) {
        mRepository.insert(ad);
    }

   // void delete(AdTable ad) { mRepository.delete(ad); }

}
