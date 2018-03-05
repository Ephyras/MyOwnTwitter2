package com.example.javier.mot2;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Javier on 2/5/2018.
 */

@Database(entities = {Message.class}, version = 6, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MessageDao messageDao();

    private static AppDatabase _instance;

    public AppDatabase() {
    }


    public static AppDatabase getAppDB(Context context) {
        if (_instance == null) {
            _instance = Room.databaseBuilder(context,
                    AppDatabase.class, "mydb").fallbackToDestructiveMigration().build();
        }
        return _instance;
    }
}
