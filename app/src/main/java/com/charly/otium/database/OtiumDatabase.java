package com.charly.otium.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.charly.otium.config.Config;
import com.charly.otium.dao.ItemSerieDao;
import com.charly.otium.dao.TypeDao;
import com.charly.otium.models.entities.ItemSerieEntity;
import com.charly.otium.models.entities.TypeEntity;

import java.util.Date;
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
                            OtiumDatabase.class, Config.DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private TypeDao typeDao;
        private ItemSerieDao itemSerieDao;

        private PopulateDbAsyncTask(OtiumDatabase db) {
            typeDao = db.getTypeDao();
            itemSerieDao = db.getItemSerieDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            typeDao.insert(new TypeEntity(1,"Serie"));
            typeDao.insert(new TypeEntity(2,"Anime"));
            typeDao.insert(new TypeEntity(3,"Pelicula"));
            typeDao.insert(new TypeEntity(4,"Manga"));
            typeDao.insert(new TypeEntity(5,"Libro"));
            typeDao.insert(new TypeEntity(6,"FanFic"));
            typeDao.insert(new TypeEntity(7,"Videojuego"));
            typeDao.insert(new TypeEntity(8,"Otro"));

            //Test
            itemSerieDao.insert(new ItemSerieEntity(0, "Test 1", new Date(), new Date(),
                    1, 1, 2, "Lo tengo", "aaaaa", ""));
            itemSerieDao.insert(new ItemSerieEntity(0, "Test 2", new Date(), new Date(),
                    3, 5, 965, "Lo tengo", "", ""));
            itemSerieDao.insert(new ItemSerieEntity(0, "Test 3", new Date(), new Date(),
                    4, 256, 1023, "Lo tengo", "", ""));
            itemSerieDao.insert(new ItemSerieEntity(0, "Test 4", new Date(), new Date(),
                    6, 1, 2, "Lo tengo", "", ""));
            itemSerieDao.insert(new ItemSerieEntity(0, "Test 5", new Date(), new Date(),
                    7, 1, 2, "Lo tengo", "", ""));
            itemSerieDao.insert(new ItemSerieEntity(0, "Test 6", new Date(), new Date(),
                    2, 1, 2, "Lo tengo", "", ""));
            itemSerieDao.insert(new ItemSerieEntity(0, "Test 7", new Date(), new Date(),
                    1, 1, 2, "Lo tengo", "", ""));
            itemSerieDao.insert(new ItemSerieEntity(0, "Test 8", new Date(), new Date(),
                    1, 1, 2, "Lo tengo", "", ""));
            itemSerieDao.insert(new ItemSerieEntity(0, "Test 9", new Date(), new Date(),
                    1, 1, 2, "Lo tengo", "", ""));

            Log.d("info", "Base de datos rellenada");

            return null;
        }
    }
}
