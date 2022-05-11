package com.example.immoloc.database;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.immoloc.database.Image;

import java.util.List;

public interface ImageDao {

    @Insert
    void insert(Image... images);

    @Query("SELECT * FROM Image")
    List<Image> getAllImage();

    @Query("SELECT * FROM Image where id = :imageId")
    List<Image> getImageByImageId(int imageId);
}
