package com.charly.otium.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.charly.otium.dao.TypeDao;
import com.charly.otium.database.OtiumDatabase;
import com.charly.otium.models.entities.TypeEntity;

import java.util.List;

public class TypeRepository {
    private TypeDao typeDao;
    private LiveData<List<TypeEntity>> allTypeEntity;

    public TypeRepository(Application application) {
        OtiumDatabase db = OtiumDatabase.getDatabase(application);
        typeDao = db.getTypeDao();
        allTypeEntity = typeDao.getAll();
    }

    public LiveData<List<TypeEntity>> getAll() {
        return allTypeEntity;
    }

    public LiveData<TypeEntity> getById(int id) {
        return typeDao.getById(id);
    }

    public LiveData<Integer> getCountTypes() {
        return typeDao.getCountTypes();
    };

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

    public void delete(TypeEntity typeEntity) {
        OtiumDatabase.databaseWriteExecutor.execute(() -> {
            typeDao.delete(typeEntity);
        });
    }

}
