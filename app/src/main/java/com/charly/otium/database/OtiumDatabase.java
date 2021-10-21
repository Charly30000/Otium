package com.charly.otium.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.charly.otium.dao.ItemSerieDao;
import com.charly.otium.dao.TypeDao;
import com.charly.otium.models.entities.ItemSerieEntity;
import com.charly.otium.models.entities.TypeEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ItemSerieEntity.class, TypeEntity.class}, version = 1)
public abstract class OtiumDatabase extends RoomDatabase {

    public abstract ItemSerieDao getItemSerieDao();
    public abstract TypeDao getTypeDao();
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile OtiumDatabase INSTANCE;
    public static OtiumDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (OtiumDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            OtiumDatabase.class, "otium_database")
                    .build();
                }
            }
        }

        return INSTANCE;
    }
}
