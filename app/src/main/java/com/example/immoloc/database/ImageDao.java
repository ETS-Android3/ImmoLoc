package com.example.immoloc.database;

import android.media.Image;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.ArrayList;
import java.util.List;

@Dao
public interface ImageDao {

    @Insert
    void insert(ImageTable... images);

    @Query("SELECT * FROM imagetable")
    List<ImageTable> getAllImage();

    @Query("SELECT * FROM imagetable where id = :imageId")
    List<ImageTable> getImageByImageId(int imageId);
/*
    @Query("INSERT INTO imagetable (path_img) VALUES (:img_path) ")
    void insertImgPath(String img_path);

 */
}
