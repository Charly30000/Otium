package com.charly.otium.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.charly.otium.dao.ItemSerieDao;
import com.charly.otium.dao.TypeDao;
import com.charly.otium.database.OtiumDatabase;
import com.charly.otium.models.entities.ItemSerieEntity;
import com.charly.otium.models.entities.TypeEntity;

import java.util.Date;
import java.util.List;

public class ItemSerieRepository {
    private ItemSerieDao itemSerieDao;
    private LiveData<List<ItemSerieEntity>> allItemSerieEntity;

    public ItemSerieRepository(Application application) {
        OtiumDatabase db = OtiumDatabase.getDatabase(application);
        itemSerieDao = db.getItemSerieDao();
        allItemSerieEntity = itemSerieDao.getAll();
    }

    public LiveData<List<ItemSerieEntity>> getAll() {
        return allItemSerieEntity;
    }

    public LiveData<ItemSerieEntity> getById(int id) {
        return itemSerieDao.getById(id);
    }

    public void insert(ItemSerieEntity itemSerieEntity) {
        OtiumDatabase.databaseWriteExecutor.execute(() -> {
            itemSerieDao.insert(itemSerieEntity);
        });
    }

    public void update(ItemSerieEntity itemSerieEntity) {
        OtiumDatabase.databaseWriteExecutor.execute(() -> {
            itemSerieEntity.setLastModification(new Date());
            itemSerieDao.update(itemSerieEntity);
        });
    }

    public void delete(ItemSerieEntity itemSerieEntity) {
        OtiumDatabase.databaseWriteExecutor.execute(() -> {
            itemSerieDao.delete(itemSerieEntity);
        });
    }
}
