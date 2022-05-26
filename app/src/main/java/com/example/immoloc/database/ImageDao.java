package com.example.immoloc.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ImageDao {

    @Insert
    void insert(ImageTable... images);

    @Query("SELECT * FROM imagetable")
    List<ImageTable> getAllImage();

    @Query("SELECT * FROM imagetable where id = :imageId")
    List<ImageTable> getImageByImageId(int imageId);

    // Avoir l'image de l'annonce d'id 'i'
    @Query("SELECT * FROM imagetable WHERE ad_id =:idOfTheAd")
    byte[] getImg(String idOfTheAd);
/*
    @Query("INSERT INTO imagetable (path_img) VALUES (:img_path) ")
    void insertImgPath(String img_path);

 */
}
