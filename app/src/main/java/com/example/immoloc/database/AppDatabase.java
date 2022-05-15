package com.example.immoloc.database;

import android.content.Context;
import android.media.Image;
import android.view.View;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.DeleteTable;
import androidx.room.RenameColumn;
import androidx.room.RenameTable;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(
        version = 5,
        entities = {User.class, ImageTable.class},
        autoMigrations = {
                @AutoMigration(
                        from = 2,
                        to = 5,
                           spec = AppDatabase.MyAutoMigration.class
                )
        }
)
public abstract class AppDatabase extends RoomDatabase {


    public abstract UserDao userDao();

    public abstract ImageDao imgDao();

    private static AppDatabase instance;
    private static final String DB_name = "immoLoc";


    // Singleton pattern when instantiating an AppDatabase object.
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_name)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }

    @RenameColumn(tableName = "user", fromColumnName = "id", toColumnName = "user_id")
    @RenameColumn(tableName = "image", fromColumnName = "image", toColumnName = "img")
    @RenameTable(fromTableName = "image", toTableName = "imagetable")

    static class MyAutoMigration implements AutoMigrationSpec {
    }


}




