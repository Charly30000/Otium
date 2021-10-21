package com.charly.otium.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.charly.otium.models.entities.ItemSerieEntity;

import java.util.List;

@Dao
public interface ItemSerieDao {
    @Insert
    void insert(ItemSerieEntity itemSerieEntity);

    @Update
    void update(ItemSerieEntity itemSerieEntity);

    @Query("DELETE FROM items_series WHERE id = :idItemSerie")
    void deleteById(int idItemSerie);

    @Query("SELECT * FROM items_series ORDER BY last_modification ASC")
    LiveData<List<ItemSerieEntity>> getAll();

    @Query("SELECT * FROM items_series WHERE id = :idItemSerie")
    LiveData<ItemSerieEntity> getById(int idItemSerie);
}
