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

    // Avoir l'image de l'annonce d'id 'i'
 /*   @Query("SELECT * FROM imagetable WHERE ad_id =:idOfTheAd")
    List<ImageTable> getImg(String idOfTheAd, String idUsr); */

   /* @Query("INSERT INTO imagetable (img, ad_id, imgUsrId) VALUES (:image, :adid, :usrid) ")
    void insertImg(ImageTable image, String adid, String usrid);*/


}
