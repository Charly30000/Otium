package com.charly.otium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.charly.otium.models.entities.ItemSerieEntity;
import com.charly.otium.models.entities.TypeEntity;
import com.charly.otium.repository.ItemSerieRepository;
import com.charly.otium.repository.TypeRepository;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddSerieActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextTextTitle, editTextNote,
            editTextNumberSeason, editTextNumberChapter;
    private Button buttonSubstractSeason, buttonAddSeason, buttonSubstractChapter,
            buttonAddChapter, buttonSave, buttonDelete;
    private Spinner spinnerState, spinnerType;

    private TypeRepository typeRepository;
    private LiveData<List<TypeEntity>> types;

    private ItemSerieRepository itemSerieRepository;

    private ArrayAdapter<String> arrayAdapterTypes;
    private ArrayList<String> arrayListTypesString;
    private ArrayList<TypeEntity> arrayListTypesEntity;

    private boolean isModifying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_serie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initRepository();
        initArrays();
        findViews();
        checkIsModifying();
        clickListener();
        loadTypes();
    }

    private void initRepository() {
        typeRepository = new TypeRepository(getApplication());
        itemSerieRepository = new ItemSerieRepository(getApplication());
    }

    private void checkIsModifying() {
        isModifying = false;
        Bundle bundle = getIntent().getExtras();
        try {
            boolean checkIsModifying = bundle.getBoolean("isModifying");
            isModifying = checkIsModifying;
        } catch (NullPointerException ex) {
            System.out.println("No existe el parametro, " +
                    "se supondrá que no se esta modificando");
        } catch (Exception ex) {
            System.err.println(ex.getStackTrace().toString());
        }
        if (isModifying) {
            buttonDelete.setVisibility(View.VISIBLE);
        }
    }

    private void initArrays() {
        arrayListTypesString = new ArrayList<>();
        arrayListTypesEntity = new ArrayList<>();
    }

    private void loadTypes() {
        types = typeRepository.getAll();
        types.observe(this, new Observer<List<TypeEntity>>() {
            @Override
            public void onChanged(List<TypeEntity> typeEntities) {
                if (typeEntities == null || typeEntities.isEmpty()) {
                    Snackbar.make(findViewById(android.R.id.content),
                            "No hay tipos en la BBDD", Snackbar.LENGTH_LONG).show();
                } else {
                    for (TypeEntity ty: typeEntities) {
                        arrayListTypesString.add(ty.getType());
                        arrayListTypesEntity.add(ty);
                    }
                    arrayAdapterTypes = new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_spinner_dropdown_item, arrayListTypesString);
                    spinnerType.setAdapter(arrayAdapterTypes);
                }
            }
        });
    }

    private void findViews() {
        // EditText
        editTextTextTitle = findViewById(R.id.editTextTextTitle);
        editTextNote = findViewById(R.id.editTextNote);
        editTextNumberSeason = findViewById(R.id.editTextNumberSeason);
        editTextNumberChapter = findViewById(R.id.editTextNumberChapter);
        // Button
        buttonSubstractSeason = findViewById(R.id.buttonSubstractSeason);
        buttonAddSeason = findViewById(R.id.buttonAddSeason);
        buttonSubstractChapter = findViewById(R.id.buttonSubstractChapter);
        buttonAddChapter = findViewById(R.id.buttonAddChapter);
        buttonSave = findViewById(R.id.buttonSave);
        buttonDelete = findViewById(R.id.buttonDelete);
        // Spinner
        spinnerState = findViewById(R.id.spinnerState);
        spinnerType = findViewById(R.id.spinnerType);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void clickListener() {
        buttonSubstractSeason.setOnClickListener(this);
        buttonAddSeason.setOnClickListener(this);
        buttonSubstractChapter.setOnClickListener(this);
        buttonAddChapter.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.buttonSubstractSeason:
                substractNumber(editTextNumberSeason);
                break;
            case R.id.buttonAddSeason:
                addNumber(editTextNumberSeason);
                break;
            case R.id.buttonSubstractChapter:
                substractNumber(editTextNumberChapter);
                break;
            case R.id.buttonAddChapter:
                addNumber(editTextNumberChapter);
                break;
            case R.id.buttonSave:
                saveSerie();
                break;
            case R.id.buttonDelete:
                Toast.makeText(AddSerieActivity.this,
                        "Metodo no implementado", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void saveSerie() {
        String title = editTextTextTitle.getText().toString();
        String stringSeason = editTextNumberSeason.getText().toString();
        String stringChapter = editTextNumberChapter.getText().toString();
        String stringState = spinnerState.getSelectedItem().toString();
        int numberSeason = 1;
        int numberChapter = 1;
        if (spinnerType.getSelectedItem() == null) {
            Snackbar.make(findViewById(android.R.id.content),
                    "No hay tipos en la BBDD", Snackbar.LENGTH_LONG).show();
            return;
        }
        String stringType = spinnerType.getSelectedItem().toString();
        String annotation = editTextNote.getText().toString();

        int typeId = findType(stringType);
        if (typeId == -1) {
            Snackbar.make(findViewById(android.R.id.content),
                    "No se ha encontrado el tipo que intentas seleccionar",
                    Snackbar.LENGTH_LONG).show();
            return;
        }

        boolean isOk = checkStringValues(title, stringSeason,
                stringChapter, stringState, stringType);
        if (!isOk) {
            Snackbar.make(findViewById(android.R.id.content),
                    "Completa todos los campos", Snackbar.LENGTH_LONG).show();
            return;
        }
        try {
            numberSeason = Integer.parseInt(stringSeason);
            numberChapter = Integer.parseInt(stringChapter);
        } catch (NumberFormatException ex) {
            Snackbar.make(findViewById(android.R.id.content),
                    "Los campos Season y Chapter deben ser numeros enteros",
                    Snackbar.LENGTH_LONG).show();
            return;
        } catch (Exception ex) {
            Snackbar.make(findViewById(android.R.id.content),
                    "Error desconocido, comprueba los parametros",
                    Snackbar.LENGTH_LONG).show();
            return;
        }

        if (!isModifying) {
            Date now = new Date();
            ItemSerieEntity saveItemSerie = new ItemSerieEntity(0, title, now, now,
                    typeId, numberSeason, numberChapter, stringState, annotation, "");
            itemSerieRepository.insert(saveItemSerie);
            Toast.makeText(this, "Guardado correctamente", Toast.LENGTH_SHORT)
                    .show();
            finish();
        } else {
            // TODO: Implementar para modificar
            Snackbar.make(findViewById(android.R.id.content),
                    "Metodo no implementado",
                    Snackbar.LENGTH_LONG).show();
        }

    }

    private int findType(String stringType) {
        int id = -1;
        for (TypeEntity et: arrayListTypesEntity) {
            if (et.getType().equals(stringType)) {
                id = et.getTypeId();
            }
        }
        return id;
    }

    private boolean checkStringValues(String... values) {
        for (String s: values) {
            if (s == null || s.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void addNumber(EditText editTextNumber) {
        try {
            int number = Integer.parseInt(editTextNumber.getText().toString());
            editTextNumber.setText(String.valueOf(++number));
        } catch (NumberFormatException ex) {
            System.err.println(ex.getStackTrace());
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            System.err.println(ex.getStackTrace());
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void substractNumber(EditText editTextNumber) {
        try {
            int number = Integer.parseInt(editTextNumber.getText().toString());
            editTextNumber.setText(String.valueOf(--number));
        } catch (NumberFormatException ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}