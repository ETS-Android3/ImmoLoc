package com.example.immoloc.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "imagetable")
public class ImageTable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(defaultValue = "0")
    public int id;

    @ColumnInfo(name = "ad_id", index = true, defaultValue = "0")
    public int ad;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name="img", typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

   // @ColumnInfo(name="path_img")
    //public String pathImg;

  //  public String getPathImg() { return pathImg; }
    //public void setPathImg(String pathImg) { this.pathImg = pathImg; }

    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAd() { return ad; }
    public void setAd(int ad) { this.ad = ad; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}