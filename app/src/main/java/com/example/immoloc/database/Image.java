package com.example.immoloc.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "image", foreignKeys = {
        @ForeignKey(entity = Ad.class,
                parentColumns = "id",
                childColumns = "ad_id",
                onDelete = ForeignKey.CASCADE),
})
public class Image {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "ad_id")
    public int ad;

    @ColumnInfo(name = "name")
    public int name;

    @ColumnInfo(name = "description")
    public int description;

    @ColumnInfo(name="image", typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

}