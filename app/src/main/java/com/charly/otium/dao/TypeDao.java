package com.charly.otium.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.charly.otium.models.entities.TypeEntity;

import java.util.List;

@Dao
public interface TypeDao {
    @Insert
    long insert(TypeEntity typeEntity);

    @Update
    int update(TypeEntity typeEntity);

    @Delete
    int delete(TypeEntity typeEntity);

    @Query("SELECT typeId, type FROM types")
    LiveData<List<TypeEntity>> getAll();

    @Query("SELECT typeId, type FROM types WHERE typeId = :idTypeEntity")
    LiveData<TypeEntity> getById(int idTypeEntity);
}
