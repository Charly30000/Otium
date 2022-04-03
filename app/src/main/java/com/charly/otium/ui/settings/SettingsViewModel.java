package com.charly.otium.ui.settings;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.charly.otium.models.entities.ItemSerieEntity;
import com.charly.otium.repository.ItemSerieRepository;

import java.util.List;

public class SettingsViewModel extends AndroidViewModel {

    private ItemSerieRepository itemSerieRepository;
    private LiveData<List<ItemSerieEntity>> allItemSerieEntity;

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        itemSerieRepository = new ItemSerieRepository(application);
        allItemSerieEntity = itemSerieRepository.getAll();
    }

    public LiveData<List<ItemSerieEntity>> getAllItemSerieEntity() {
        return allItemSerieEntity;
    }

}