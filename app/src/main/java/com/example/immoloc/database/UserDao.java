package com.example.immoloc.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE email = :mail")
    User findEmail(String mail);

    @Query("SELECT * FROM user WHERE id = :id")
    User getUser(long id);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Query("SELECT * FROM user WHERE email = :mail AND password = :pass")
    User login(String mail, String pass);

    // Changement/Réinitialisation du mot de passe
    @Query("UPDATE user SET password=:new_password WHERE email =:userEmail")
    void updatePassword(String new_password, String userEmail);

    @Query("SELECT first_name FROM user WHERE email =:userEmail AND password =:pass")
    String findName(String userEmail, String pass);

    // Lors de l'inscription, pas deux fois le même mail
    @Query("SELECT EXISTS(SELECT * FROM user WHERE email = :mail)")
    boolean isRecordExistsMail(String mail);

    @Update
    void update(User user);

    @Insert
    void insert(User user);

    // Insère une nouvelle image ou la modifie
    @Query("UPDATE user SET userImg =:image WHERE id =:userid")
    void addPicture(byte[] image ,int userid);

    @Delete
    void delete(User user);

}
