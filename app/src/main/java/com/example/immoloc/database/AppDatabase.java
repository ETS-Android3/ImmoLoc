package com.example.immoloc.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.DeleteColumn;
import androidx.room.RenameColumn;
import androidx.room.RenameTable;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(
        version = 10,
        entities = {User.class, ImageTable.class, AdTable.class, City.class, Category.class, Message.class, Visit.class},
        autoMigrations = {
                @AutoMigration(
                        from = 9,
                        to = 10,
                           spec = AppDatabase.MyAutoMigration.class
                )
        }
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract ImageDao imgDao();
    public abstract AdDao adDao();
    public abstract CategoryDao catDao();
    public abstract CityDao cityDao();

    private static AppDatabase instance;
    private static final String DB_name = "immoLoc";
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);



    public static synchronized AppDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_name)
                    .allowMainThreadQueries() .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }


    @RenameColumn(tableName = "user", fromColumnName = "id", toColumnName = "user_id")
    @RenameColumn(tableName = "image", fromColumnName = "image", toColumnName = "img")
    @RenameTable(fromTableName = "image", toTableName = "imagetable")
    @RenameTable(fromTableName = "ad", toTableName = "adtable")

    static class MyAutoMigration implements AutoMigrationSpec {

    }

}




