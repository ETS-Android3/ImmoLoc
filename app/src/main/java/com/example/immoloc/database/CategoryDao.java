package com.example.immoloc.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CategoryDao {

    // Lors d'une recherche de catégorie
    @Query("SELECT * FROM category WHERE category_type LIKE :inputCategory")
    Category getCatType(String inputCategory);

    @Query("SELECT id FROM category WHERE category_type =:inputCat")
    int getIdByType(String inputCat);

    @Insert
    void insert(Category cat);

    @Delete
    void delete(Category cat);
}
