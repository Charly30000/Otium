package com.charly.otium.ui.home;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.charly.otium.models.entities.ItemSerieEntity;
import com.charly.otium.repository.ItemSerieRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private ItemSerieRepository itemSerieRepository;
    private LiveData<List<ItemSerieEntity>> allItemSerieEntity;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        itemSerieRepository = new ItemSerieRepository(application);
        allItemSerieEntity = itemSerieRepository.getAll();
    }

    public LiveData<List<ItemSerieEntity>> getAllItemSerieEntity() {
        return allItemSerieEntity;
    }

    public void insertItemSerieEntity(ItemSerieEntity itemSerieEntity) {
        itemSerieRepository.insert(itemSerieEntity);
    }

    public void updateItemSerieEntity(ItemSerieEntity itemSerieEntity) {
        itemSerieRepository.update(itemSerieEntity);
    }

    public void deleteItemSerieEntity(ItemSerieEntity itemSerieEntity) {
        itemSerieRepository.delete(itemSerieEntity);
    }

}