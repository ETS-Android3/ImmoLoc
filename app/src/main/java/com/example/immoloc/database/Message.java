package com.example.immoloc.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "message",
    foreignKeys =
            {@ForeignKey(entity = User.class,
                    parentColumns = "id",
                    childColumns = "user_id_sender",
                    onDelete = ForeignKey.CASCADE),
                    @ForeignKey(entity = User.class,
                            parentColumns = "id",
                            childColumns = "user_id_receiver",
                            onDelete = ForeignKey.CASCADE),
                    @ForeignKey(entity = Ad.class,
                            parentColumns = "id",
                            childColumns = "ad_id",
                            onDelete = ForeignKey.CASCADE)})
public class Message {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "ad_id")
    public int adId;

    @ColumnInfo(name = "user_id_receiver")
    public int userIdReceiver;

    @ColumnInfo(name = "user_id_sender")
    public int userIdSender;

    public String time;

    @ColumnInfo(name = "message_subject")
    String messageSubject;

    @ColumnInfo(name = "message_text")
    String messageText;

    @ColumnInfo(name = "message_date")
    String messageDate;

    public int getId(){
        return id;
    }

    public void setId(int id) { this.id = id; }

    public int getUserIdReceiver() {
        return userIdReceiver;
    }

    public void setUserIdReceiver(int userId) {
        this.userIdReceiver = userId;
    }

    public int getUserIdSender() {
        return userIdSender;
    }

    public void setUserIdSender(int userId) {
        this.userIdSender = userId;
    }

    public int getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public String getMessageSubject() {
        return messageSubject;
    }

    public void setMessageSubject(String message) {
        this.messageSubject = message;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String text) {
        this.messageText = text;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }
}
