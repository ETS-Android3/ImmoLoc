package com.example.immoloc.database;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

public interface CityDao {

    @Query("SELECT * FROM city")
    List<City> getAll();

    @Query("SELECT * FROM city WHERE name = :cityName")
    City findByName(String cityName);

    @Query("SELECT name FROM city WHERE price_loc_m2 = :price")
    City findByPrice(double price);

    @Insert
    void insert(City city);

    @Delete
    void delete(City city);


}
