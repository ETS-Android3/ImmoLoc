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

    // Supprimer une annonce à partir de l'id de celle-ci
    @Query("DELETE FROM adtable WHERE id =:ad")
    void deleteParticularAd(String ad);

    // Requête de modification d'une annonce
    @Query("UPDATE adtable SET date_debut_loc=:date_deb, date_fin_loc=:date_fin, " +
            " text=:desctext, price=:prix, area=:surface, nbPieces=:pieces, adresse=:adr, " +
            "nbSalleDeau=:water_room, nbChambres=:nbChambres WHERE id =:idAd")
    void updateMyAd(String idAd, String date_deb, String date_fin, String desctext, String prix, String surface,
                    String pieces, String adr, String water_room, String nbChambres);

    // Requête de recherche d'une annonce
    /*@Query("SELECT * FROM adtable WHERE date_debut_loc=:date_deb AND date_fin_loc=:date_fin AND " +
            " text=:desctext AND price=:prix AND area=:surface AND nbPieces=:pieces AND adresse=:adr AND " +
            "nbSalleDeau=:water_room AND nbChambres=:nbChambres") // OR
    void searchAnAd(String idAd, String date_deb, String date_fin, String desctext, String prix, String surface,
                    String pieces, String adr, String water_room, String nbChambres);*/

    // Mais avant essayer avec peu de champs voir si cela fonctionne :
    @Query("SELECT * FROM adtable, city, category WHERE city.id = adtable.id AND adtable.id = category.id " +
            " AND price BETWEEN :prixMin AND :prixMax AND area BETWEEN :areaMin AND :areaMax" +
            " AND city.name =:cityName AND category_type =:catType")
    List<AdTable> searchAnAd(String prixMin, String prixMax, String areaMin, String areaMax, String cityName, String catType);

    @Query("SELECT name FROM adtable, city WHERE adtable.id = city.id AND adtable.id =:ad_id")
    String getCityName(long ad_id);

    @Query("SELECT zip_code FROM adtable, city WHERE adtable.id = city.id AND adtable.id =:ad_id")
    String getZipCode(long ad_id);

    @Query("SELECT category_type FROM adtable, category WHERE adtable.id = category.id AND adtable.id =:ad_id")
    String getTypeDeBien(long ad_id);

    @Query("SELECT phone FROM adtable, user WHERE adtable.id = user.id AND user_id =:currentUserId")
    String getPhoneNb(long currentUserId);

    // Avoir une annonce courante de l'utilisateur courant
    @Query("SELECT * FROM adtable WHERE id = :id")
    AdTable getAd(long id);

    @Insert
    void insert(AdTable adTable);

    @Delete
    void delete(AdTable adTable);


}
