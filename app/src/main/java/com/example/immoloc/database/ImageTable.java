package com.example.immoloc.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "imagetable")
public class ImageTable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "ad_id", index = true)
    public int ad;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name="img", typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}