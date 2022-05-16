package com.example.immoloc.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AdDao {


    @Query("SELECT * FROM adtable WHERE title = :inputTitle")
    AdTable getAdByTitle(String inputTitle);

    @Query("SELECT * FROM adtable WHERE price = :inputPrice")
    AdTable getAdByPrice(String inputPrice);

    // à recompléter par la suite lors des tests

    @Insert
    void insert(AdTable adTable);

    @Delete
    void delete(AdTable adTable);
}
