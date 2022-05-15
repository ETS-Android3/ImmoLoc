package com.example.immoloc.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CategoryDao {

    // Lors d'une recherche de catégorie
    @Query("SELECT category_type FROM category WHERE category_type LIKE :inputCategory")
    Category getCatType(String inputCategory);

    @Insert
    void insert(Category cat);

    @Delete
    void delete(Category cat);
}
