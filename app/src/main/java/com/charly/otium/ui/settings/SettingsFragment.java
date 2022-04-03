package com.charly.otium.ui.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.charly.otium.databinding.FragmentGalleryBinding;
import com.charly.otium.exceptions.OtiumException;
import com.charly.otium.file.CSVFile;
import com.charly.otium.file.IFileRules;
import com.charly.otium.models.entities.ItemSerieEntity;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private FragmentGalleryBinding binding;

    private Button btnCreateCsv;

    private List<ItemSerieEntity> listItemSerieEntity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        findViews();
        clickListeners();

        settingsViewModel.getAllItemSerieEntity().observe(getActivity(), new Observer<List<ItemSerieEntity>>() {
            @Override
            public void onChanged(List<ItemSerieEntity> itemSerieEntities) {
                listItemSerieEntity = itemSerieEntities;
            }
        });

        /*final TextView textView = binding.textGallery;

        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    private void clickListeners() {
        btnCreateCsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCsv();
            }
        });
    }

    private void findViews() {
        btnCreateCsv = binding.btnCreateCsv;
    }

    public void createCsv() {

        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/comma-separated-values");
        intent.putExtra(Intent.EXTRA_TITLE, "invoice.csv");
        intent = Intent.createChooser(intent, "Elige la ubicacion");

        activityResultLauncherWriteCSV.launch(intent);

    }

    private ActivityResultLauncher<Intent> activityResultLauncherWriteCSV = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getData() != null) {
                            try {
                                List<ItemSerieEntity> allItemSerie = new ArrayList<>();
                                for (ItemSerieEntity its: listItemSerieEntity) {
                                    allItemSerie.add(its);
                                }
                                IFileRules writeCSV = new CSVFile();
                                writeCSV.writeFile(data.getData(),allItemSerie,getActivity());
                                Snackbar.make(binding.getRoot(),
                                        "Archivo creado correctamente",
                                        Snackbar.LENGTH_LONG).show();
                            } catch (IOException | OtiumException e) {
                                e.printStackTrace();
                                Snackbar.make(binding.getRoot(),
                                        "Ha ocurrido un error: " + e.getMessage(),
                                        Snackbar.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Snackbar.make(binding.getRoot(),
                                        "Ha ocurrido un error un error inesperado: " + e.getMessage(),
                                        Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }
    );

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}