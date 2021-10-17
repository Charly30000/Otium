package com.charly.otium;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AddSerieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_serie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}