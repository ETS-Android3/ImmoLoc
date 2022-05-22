package com.example.immoloc.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface AdDao {

    @Query("SELECT * FROM adtable")
    List<AdTable> getAll();

    // Avoir les annonces de l'utilisateur courant d'id "id"
    @Query("SELECT * FROM adtable WHERE user_id =:id")
    List<AdTable> getAdsOfUser(long id);

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

    // Supprimer une annonce à partir de l'id de celle-ci
    @Query("DELETE FROM adtable WHERE id =:ad")
    void deleteParticularAd(String ad);

    // Test premièrement uniquement avec ce champ
    @Query("UPDATE adtable SET date_debut_loc=:date_deb WHERE id =:idAd")
    void updateDateDebut(String date_deb, String idAd);

    @Query("UPDATE adtable SET date_debut_loc=:date_deb, date_fin_loc=:date_fin, " +
            " text=:desctext, price=:prix, area=:surface WHERE id =:idAd")
    void updateMyAd(String idAd, String date_deb, String date_fin, String desctext, String prix, String surface);

    // Avoir une annonce courante de l'utilisateur courant
    @Query("SELECT * FROM adtable WHERE id = :id")
    AdTable getAd(long id);


}
