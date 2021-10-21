package com.charly.otium;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddSerieActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextTextPersonName, editTextTextMultiLine, editTextNumberSeason, editTextNumberChapter;
    private Button buttonSubstractSeason, buttonAddSeason, buttonSubstractChapter, buttonAddChapter, buttonSave;
    private Spinner spinnerState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_serie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViews();
        clickListener();
    }

    private void findViews() {
        // EditText
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        editTextTextMultiLine = findViewById(R.id.editTextTextMultiLine);
        editTextNumberSeason = findViewById(R.id.editTextNumberSeason);
        editTextNumberChapter = findViewById(R.id.editTextNumberChapter);
        // Button
        buttonSubstractSeason = findViewById(R.id.buttonSubstractSeason);
        buttonAddSeason = findViewById(R.id.buttonAddSeason);
        buttonSubstractChapter = findViewById(R.id.buttonSubstractChapter);
        buttonAddChapter = findViewById(R.id.buttonAddChapter);
        buttonSave = findViewById(R.id.buttonSave);
        // Spinner
        spinnerState = findViewById(R.id.spinnerState);
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
                break;
        }
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