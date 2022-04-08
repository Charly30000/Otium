package com.charly.otium;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.charly.otium.common.State;
import com.charly.otium.common.Type;
import com.charly.otium.models.entities.ItemSerieEntity;
import com.charly.otium.repository.ItemSerieRepository;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;

public class AddMassiveListActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSaveMassive;
    private EditText etMassive;
    private Spinner spinnerStateMassive, spinnerTypeMassive;
    private TextView tvInstructions;

    private ArrayList<String> arrayListStatesString;
    private ArrayList<String> arrayListTypesString;

    private ItemSerieRepository itemSerieRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_massive_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViews();
        clickListeners();
        loadSpinners();
        initRepository();
    }

    private void initRepository() {
        itemSerieRepository = new ItemSerieRepository(getApplication());
    }

    private void loadSpinners() {
        arrayListStatesString = new State().getAll();
        ArrayAdapter<String> arrayAdapterStates = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, arrayListStatesString);
        spinnerStateMassive.setAdapter(arrayAdapterStates);

        arrayListTypesString = new Type().getAll();
        ArrayAdapter<String> arrayAdapterTypes = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, arrayListTypesString);
        spinnerTypeMassive.setAdapter(arrayAdapterTypes);
    }

    private void clickListeners() {
        btnSaveMassive.setOnClickListener(this);
        tvInstructions.setOnClickListener(this);
    }

    private void findViews() {
        btnSaveMassive = findViewById(R.id.btnSaveMassive);
        etMassive = findViewById(R.id.etMassive);
        spinnerStateMassive = findViewById(R.id.spinnerStateMassive);
        spinnerTypeMassive = findViewById(R.id.spinnerTypeMassive);
        tvInstructions = findViewById(R.id.tvInstructions);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnSaveMassive:
                saveMassive();
                break;
            case R.id.tvInstructions:
                ViewGroup.LayoutParams params = tvInstructions.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                tvInstructions.setLayoutParams(params);
                break;
        }
    }

    private void saveMassive() {
        String titles = etMassive.getText().toString().trim();
        if (titles.contains("\\")) {
            Snackbar.make(getCurrentFocus(),
                    "No se admite el caracter '\\', no se guardara",
                    Snackbar.LENGTH_LONG).show();
            return;
        }
        if (titles.isEmpty()) {
            Snackbar.make(getCurrentFocus(),
                    "Introduce algun titulo",
                    Snackbar.LENGTH_LONG).show();
            return;
        }
        String[] titlesSplit = titles.split("\n");
        String selectedState = spinnerStateMassive.getSelectedItem().toString();
        String selectedType = spinnerTypeMassive.getSelectedItem().toString();
        if (selectedState == null || selectedType == null
                || selectedState.isEmpty() || selectedType.isEmpty()) {
            Snackbar.make(getCurrentFocus(),
                    "Debes de seleccionar un Estado y un Tipo",
                    Snackbar.LENGTH_LONG).show();
            return;
        }
        for (String title: titlesSplit) {
            if (!title.isEmpty()) {
                Date dateNow = new Date();
                ItemSerieEntity saveItemSerie = new ItemSerieEntity(0, title.trim(),
                        dateNow, dateNow, selectedType, 1, 1, selectedState,
                        "", "");
                itemSerieRepository.insert(saveItemSerie);
            }
        }

        etMassive.setText("");
        Snackbar.make(getCurrentFocus(),
                "Titulos insertados correctamente",
                Snackbar.LENGTH_LONG).show();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}