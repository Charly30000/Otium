package com.charly.otium.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.charly.otium.dao.TypeDao;
import com.charly.otium.database.OtiumDatabase;
import com.charly.otium.models.entities.TypeEntity;

import java.util.List;

public class TypeRepository {
    private TypeDao typeDao;

    public TypeRepository(Application application) {
        OtiumDatabase db = OtiumDatabase.getDatabase(application);
        typeDao = db.getTypeDao();
    }

    public LiveData<List<TypeEntity>> getAll() {
        return typeDao.getAll();
    }

    public LiveData<TypeEntity> getById(int id) {
        return typeDao.getById(id);
    }

    public void insert(TypeEntity typeEntity) {
        OtiumDatabase.databaseWriteExecutor.execute(() -> {
            typeDao.insert(typeEntity);
        });
    }

    public void update(TypeEntity typeEntity) {
        OtiumDatabase.databaseWriteExecutor.execute(() -> {
            typeDao.update(typeEntity);
        });
    }

    public void deleteById(TypeEntity typeEntity) {
        OtiumDatabase.databaseWriteExecutor.execute(() -> {
            typeDao.delete(typeEntity);
        });
    }

}
