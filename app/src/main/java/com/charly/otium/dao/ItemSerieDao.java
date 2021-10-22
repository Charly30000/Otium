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

    @Delete
    void delete(ItemSerieEntity itemSerieEntity);

    @Query("SELECT itemSerieId, title, create_at, last_modification, type_id, " +
            "season, chapter, state, annotation, image " +
            "FROM items_series " +
            "ORDER BY last_modification ASC")
    LiveData<List<ItemSerieEntity>> getAll();

    @Query("SELECT itemSerieId, title, create_at, last_modification, type_id, " +
            "season, chapter, state, annotation, image " +
            "FROM items_series " +
            "WHERE itemSerieId = :idItemSerie")
    LiveData<ItemSerieEntity> getById(int idItemSerie);
}
