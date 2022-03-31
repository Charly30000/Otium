package com.charly.otium.file;

import com.charly.otium.models.entities.ItemSerieEntity;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVFile implements FileRules {

    @Override
    public void writeFile(String filename, List<ItemSerieEntity> data) throws IOException {
        String csv = filename;
        CSVWriter writer = new CSVWriter(new FileWriter(csv));
        String[] header= new String[]{"title","createAt","lastModification","type", "season",
                "chapter", "state", "annotation", "image"};
        writer.writeNext(header);
        List<String[]> allData = new ArrayList<String[]>();
        for (ItemSerieEntity its: data) {
            String[] dataSave = new String[]{its.getTitle(), its.getCreateAt().toString(),
                    its.getLastModification().toString(), its.getType(), String.valueOf(its.getSeason()),
                    String.valueOf(its.getChapter()), its.getState(), its.getAnnotation(), its.getImage()};
            allData.add(dataSave);
        }
        writer.writeAll(allData);
        writer.close();
    }

    @Override
    public List<ItemSerieEntity> readFile(String filename) throws IOException {
        List<ItemSerieEntity> result = new ArrayList<>();
        Map mapping = new HashMap();
        mapping.put("title", "title");
        mapping.put("createAt", "createAt");
        mapping.put("lastModification", "lastModification");
        mapping.put("type", "type");
        mapping.put("season", "season");
        mapping.put("chapter", "chapter");
        mapping.put("state", "state");
        mapping.put("annotation", "annotation");
        mapping.put("image", "image");

        HeaderColumnNameTranslateMappingStrategy strategy = new HeaderColumnNameTranslateMappingStrategy();
        strategy.setType(ItemSerieEntity.class);
        strategy.setColumnMapping(mapping);

        CSVReader csvReader = new CSVReader(new FileReader(filename));
        CsvToBean csvToBean = new CsvToBean();
        csvToBean.setMappingStrategy(strategy);
        csvToBean.setCsvReader(csvReader);
        List<ItemSerieEntity> list = csvToBean.parse();

        for (ItemSerieEntity its: list) {
            result.add(its);
        }

        return result;
    }
}
