package com.example.immoloc.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MessageDao {

    @Query("SELECT * FROM message")
    List<Message> getAll();

    // Chercher les messages d'un utilisateur en particulier
    @Query("SELECT * FROM message WHERE id IN (:idSender)")
    List<Message> loadAllByIds(int[] idSender);

    // Chercher un message par mot clé
    @Query("SELECT message_text FROM message WHERE message_text LIKE :keyword")
    User findByText(String keyword);

    @Insert
    void insert(Message msg);

    @Delete
    void delete(Message msg);

}
