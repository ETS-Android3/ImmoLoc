package com.example.immoloc.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")
public class Category {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "category_type")
    public String categoryType;

    /* Il faudras certainement définir plus exactement
    les valeurs possibles pour catégorie du style:
    Studio, Duplex , F&, F2, F3 .. , maison, maison avec jardin,
    maison mitoyenne ...
     */

    public int getId(){
        return id;
    }
    public void setId(int id) { this.id = id; }

    public String getCategoryType() {
        return categoryType;
    }
    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

}
