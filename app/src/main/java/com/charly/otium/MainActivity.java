package com.charly.otium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.charly.otium.repository.ItemSerieRepository;
import com.charly.otium.repository.TypeRepository;

public class MainActivity extends AppCompatActivity {

    private ItemSerieRepository itemSerieRepository;
    private TypeRepository typeRepository;
    private LiveData<Integer> countTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //initRepository();
        //checkCreateTypes();
        initDashboardActivity();
    }
/*
    private void initRepository() {
        itemSerieRepository = new ItemSerieRepository(getApplication());
        typeRepository = new TypeRepository(getApplication());
    }

    private void checkCreateTypes() {
        countTypes = typeRepository.getCountTypes();
        countTypes.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer quantity) {
                if (quantity != null) {
                    if (quantity == 0) {
                        createTypes();
                    } else {
                        initDashboardActivity();
                    }
                } else {
                    createTypes();
                }
            }
        });
    }

    private void createTypes() {
        System.out.println("Creando tipos...");
        try {
            typeRepository.insert(new TypeEntity(1,"Serie"));
            typeRepository.insert(new TypeEntity(2,"Anime"));
            typeRepository.insert(new TypeEntity(3,"Pelicula"));
            typeRepository.insert(new TypeEntity(4,"Manga"));
            typeRepository.insert(new TypeEntity(5,"Libro"));
            typeRepository.insert(new TypeEntity(6,"FanFic"));
            typeRepository.insert(new TypeEntity(7,"Videojuego"));
            typeRepository.insert(new TypeEntity(8,"Otro"));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println(ex.getCause());
            System.err.println(ex.getStackTrace().toString());
        }

        initDashboardActivity();
    }
*/
    private void initDashboardActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}