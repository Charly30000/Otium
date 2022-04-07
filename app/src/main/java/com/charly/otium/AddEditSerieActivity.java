package com.charly.otium;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.charly.otium.common.State;
import com.charly.otium.common.Type;
import com.charly.otium.models.entities.ItemSerieEntity;
import com.charly.otium.models.entities.TypeEntity;
import com.charly.otium.repository.ItemSerieRepository;
import com.charly.otium.repository.TypeRepository;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AddEditSerieActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextTitle, editTextNote,
            editTextNumberSeason, editTextNumberChapter;
    private Button buttonSubstractSeason, buttonAddSeason, buttonSubstractChapter,
            buttonAddChapter, buttonSave, buttonDelete;
    private Spinner spinnerState, spinnerType;

    //private TypeRepository typeRepository;
    //private LiveData<List<TypeEntity>> types;

    private ItemSerieRepository itemSerieRepository;

    private ArrayAdapter<String> arrayAdapterTypes;
    private ArrayList<String> arrayListTypesString;
    //private ArrayList<TypeEntity> arrayListTypesEntity;

    private ArrayAdapter<String> arrayAdapterItemSerie;
    private ArrayList<String> arrayListItemSerieString;

    public static final String EXTRA_IS_MODIFYING = "isModifying";
    public static final String EXTRA_ID_ITEM_SERIE = "idItemSerie";
    private int idItemSerieModifying;
    private boolean isModifying;
    private ItemSerieEntity itemSerieModifying;

    private LiveData<ItemSerieEntity> itemSerieEntityLiveData;
    private boolean isDeleting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_serie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initRepository();
        initArrays();
        findViews();
        loadTypesSpinner();
        loadItemSeriesSpinner();
        checkIsModifying();
        clickListener();
    }

    private void initRepository() {
        //typeRepository = new TypeRepository(getApplication());
        itemSerieRepository = new ItemSerieRepository(getApplication());
    }

    private void checkIsModifying() {
        Intent intent = getIntent();
        this.isModifying = false;
        if (intent.hasExtra(EXTRA_IS_MODIFYING)) {
            this.isModifying = intent.getBooleanExtra(EXTRA_IS_MODIFYING, false);
        }
        this.idItemSerieModifying = -1;
        if (intent.hasExtra(EXTRA_ID_ITEM_SERIE)) {
            this.idItemSerieModifying = intent.getIntExtra(EXTRA_ID_ITEM_SERIE, -1);
        }

        if (this.isModifying && this.idItemSerieModifying == -1) {
            Toast.makeText(getApplicationContext(), "No se encuentra el objeto a modificar",
                    Toast.LENGTH_SHORT).show();
            finish();
        }

        if (this.isModifying) {
            buttonDelete.setVisibility(View.VISIBLE);
            populateParams(this.idItemSerieModifying);
        }
    }

    private void populateParams(int id) {
        itemSerieEntityLiveData = itemSerieRepository.getById(id);
        itemSerieEntityLiveData.observe(this, new Observer<ItemSerieEntity>() {
            @Override
            public void onChanged(ItemSerieEntity itemSerieEntity) {
                if (!isDeleting) {
                    itemSerieModifying = itemSerieEntity;
                    editTextTitle.setText(itemSerieEntity.getTitle());
                    editTextNumberSeason.setText(String.valueOf(itemSerieEntity.getSeason()));
                    editTextNumberChapter.setText(String.valueOf(itemSerieEntity.getChapter()));
                    editTextNote.setText(itemSerieEntity.getAnnotation());
                    spinnerState.setSelection(arrayAdapterItemSerie.getPosition(itemSerieEntity.getState()));
                    spinnerType.setSelection(arrayAdapterTypes.getPosition(itemSerieEntity.getType()));
                }

            }
        });
    }

    private void initArrays() {
        arrayListTypesString = new Type().getAll();
        //arrayListTypesEntity = new ArrayList<>();
        arrayListItemSerieString = new State().getAll();
    }

    private void loadTypesSpinner() {
        arrayAdapterTypes = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, arrayListTypesString);
        spinnerType.setAdapter(arrayAdapterTypes);
        /*
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
         */
    }

    private void loadItemSeriesSpinner() {
        this.arrayAdapterItemSerie = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, arrayListItemSerieString);
        spinnerState.setAdapter(arrayAdapterItemSerie);
    }

    private void findViews() {
        // EditText
        editTextTitle = findViewById(R.id.editTextTitle);
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
                deleteSerie();
                break;
        }
    }

    private void deleteSerie() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Estás seguro de querer eliminarlo? No podrás volver a recuperarlo")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        isDeleting = true;
                        itemSerieRepository.delete(itemSerieModifying);
                        Toast.makeText(getApplicationContext(), "Eliminado correctamente", Toast.LENGTH_SHORT)
                                .show();
                        finish();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog titulo = builder.create();
        titulo.setTitle("¡Estas a punto de eliminarlo!");
        titulo.show();

    }

    private void saveSerie() {
        String title = editTextTitle.getText().toString().trim();
        String stringSeason = editTextNumberSeason.getText().toString().trim();
        String stringChapter = editTextNumberChapter.getText().toString().trim();
        String stringState = spinnerState.getSelectedItem().toString().trim();
        String stringType = spinnerType.getSelectedItem().toString();
        String annotation = editTextNote.getText().toString().trim();

        int numberSeason = 1;
        int numberChapter = 1;
        if (spinnerType.getSelectedItem() == null) {
            Snackbar.make(findViewById(android.R.id.content),
                    "No hay tipos en la BBDD", Snackbar.LENGTH_LONG).show();
            return;
        }
        /*
        int typeId = findType(stringType);
        if (typeId == -1) {
            Snackbar.make(findViewById(android.R.id.content),
                    "No se ha encontrado el tipo que intentas seleccionar",
                    Snackbar.LENGTH_LONG).show();
            return;
        }
        */
        boolean isOk = checkStringValues(
                title,
                stringSeason,
                stringChapter,
                stringState,
                stringType,
                annotation
        );
        if (!isOk) {
            Snackbar.make(findViewById(android.R.id.content),
                    "Completa todos los campos, el caracter \"\\\" esta prohibido",
                    Snackbar.LENGTH_LONG).show();
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
        Date now = new Date();
        if (!isModifying) {
            ItemSerieEntity saveItemSerie = new ItemSerieEntity(0, title, now, now,
                    stringType, numberSeason, numberChapter, stringState, annotation, "");

            itemSerieRepository.insert(saveItemSerie);

            Toast.makeText(this, "Guardado correctamente", Toast.LENGTH_SHORT)
                    .show();

            finish();
        } else {
            ItemSerieEntity modifyItemSerie = new ItemSerieEntity(itemSerieModifying.getItemSerieId(), title,
                    itemSerieModifying.getCreateAt(), now,
                    stringType, numberSeason, numberChapter, stringState, annotation, "");
            itemSerieRepository.update(modifyItemSerie);
            Toast.makeText(this, "Modificado correctamente", Toast.LENGTH_SHORT)
                    .show();
            finish();
        }

    }
/*
    private int findType(String stringType) {
        int id = -1;
        for (TypeEntity et: arrayListTypesEntity) {
            if (et.getType().equals(stringType)) {
                id = et.getTypeId();
            }
        }
        return id;
    }
*/
    private boolean checkStringValues(String... values) {
        for (String s: values) {
            if (s == null || s.isEmpty() || s.contains("\\")) {
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