package com.charly.otium.file;

import android.net.Uri;

import androidx.fragment.app.FragmentActivity;

import com.charly.otium.exceptions.OtiumException;
import com.charly.otium.models.entities.ItemSerieEntity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface IFileRules {

    void writeFile(Uri uri, List<ItemSerieEntity> data, FragmentActivity activity) throws IOException, OtiumException;

    List<ItemSerieEntity> readFile(Uri uri, FragmentActivity activity) throws IOException, FileNotFoundException, OtiumException, ParseException;
}
