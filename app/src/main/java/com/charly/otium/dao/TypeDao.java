package com.charly.otium.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.charly.otium.models.entities.TypeEntity;

import java.util.List;

@Dao
public interface TypeDao {
    @Insert
    void insert(TypeEntity typeEntity);

    @Update
    void update(TypeEntity typeEntity);

    @Query("DELETE FROM items_series WHERE id = :idItemSerie")
    void deleteById(int idTypeEntity);

    @Query("SELECT * FROM items_series ORDER BY last_modification ASC")
    LiveData<List<TypeEntity>> getAll();

    @Query("SELECT * FROM items_series WHERE id = :idItemSerie")
    LiveData<TypeEntity> getById(int idTypeEntity);
}
