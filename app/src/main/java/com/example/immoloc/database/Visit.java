package com.example.immoloc.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "visit",
        foreignKeys =
                {@ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Ad.class,
                        parentColumns = "id",
                        childColumns = "ad_id",
                        onDelete = ForeignKey.CASCADE)})

public class Visit {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "ad_id")
    public int adId;

    @ColumnInfo(name = "user_id")
    public int userId;

    public String time;

    @ColumnInfo(name = "nb_times_visited")
    String nbTimesVisited;


    public int getId(){
        return id;
    }

    public void setId(int id) { this.id = id; }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}