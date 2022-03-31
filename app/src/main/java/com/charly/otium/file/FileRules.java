package com.charly.otium.file;

import com.charly.otium.models.entities.ItemSerieEntity;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.List;

public interface FileRules {
    // Return true if file is created, false if not
    void writeFile(String filename, List<ItemSerieEntity> data) throws IOException;

    // Read file
    List<ItemSerieEntity> readFile(String filename) throws IOException;
}
