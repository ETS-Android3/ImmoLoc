package com.example.immoloc.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AdDao {


    @Query("SELECT title FROM ad WHERE title = :inputTitle")
    Ad getAdByTitle(String inputTitle);

    @Query("SELECT title FROM ad WHERE price = :inputPrice")
    Ad getAdByPrice(String inputPrice);

    // à recompléter par la suite lors des tests

    @Insert
    void insert(Ad ad);

    @Delete
    void delete(Ad ad);
}
