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
import android.widget.Toast;

import com.charly.otium.common.State;
import com.charly.otium.common.Type;

import java.util.ArrayList;

public class AddMassiveListActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSaveMassive;
    private EditText etMassive;
    private Spinner spinnerStateMassive, spinnerTypeMassive;
    private TextView tvInstructions;

    private ArrayList<String> arrayListStatesString;
    private ArrayList<String> arrayListTypesString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_massive_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViews();
        clickListeners();
        loadSpinners();
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
                Toast.makeText(this, "Metodo no implementado", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvInstructions:
                ViewGroup.LayoutParams params = tvInstructions.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                tvInstructions.setLayoutParams(params);
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}