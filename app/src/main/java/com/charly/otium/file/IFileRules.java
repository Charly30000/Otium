package com.charly.otium.file;

import android.net.Uri;

import androidx.fragment.app.FragmentActivity;

import com.charly.otium.databinding.FragmentGalleryBinding;
import com.charly.otium.exceptions.OtiumException;
import com.charly.otium.models.entities.ItemSerieEntity;

import java.io.IOException;
import java.util.List;

public interface IFileRules {

    void writeFile(Uri uri, List<ItemSerieEntity> data, FragmentActivity activity) throws IOException, OtiumException;

    List<ItemSerieEntity> readFile(String filename) throws IOException, OtiumException;
}
