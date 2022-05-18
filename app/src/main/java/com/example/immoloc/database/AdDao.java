package com.example.immoloc.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AdDao {


    @Query("SELECT * FROM adtable WHERE title = :inputTitle")
    AdTable getAdByTitle(String inputTitle);

    @Query("SELECT * FROM adtable WHERE price = :inputPrice")
    AdTable getAdByPrice(String inputPrice);

    @Query("SELECT * FROM adtable")
    LiveData<List<AdTable>> getAllAds();

    @Insert
    void insert(AdTable adTable);

    @Delete
    void delete(AdTable adTable);
}
