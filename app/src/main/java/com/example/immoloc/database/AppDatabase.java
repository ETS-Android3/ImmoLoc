package com.example.immoloc.database;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RenameColumn;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.AutoMigrationSpec;

@Database(
        version = 2,
        entities = {User.class},
        autoMigrations = {
                @AutoMigration(
                        from = 1,
                        to = 2,
                           spec = AppDatabase.MyAutoMigration.class
                )
        }
)

public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    private static AppDatabase instance;
    private static final String DB_name = "immoLoc";


    // Singleton pattern when instantiating an AppDatabase object.
    public static synchronized AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_name)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
    @RenameColumn(tableName="user", fromColumnName ="id", toColumnName ="user_id")
    static class MyAutoMigration implements AutoMigrationSpec { }


}



