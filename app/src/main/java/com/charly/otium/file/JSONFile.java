package com.charly.otium.file;

import android.net.Uri;

import androidx.fragment.app.FragmentActivity;

import com.charly.otium.common.Type;
import com.charly.otium.config.Config;
import com.charly.otium.exceptions.OtiumException;
import com.charly.otium.models.entities.ItemSerieEntity;
import com.charly.otium.models.entitiesGson.FileJson;
import com.charly.otium.models.entitiesGson.ItemSerieGson;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JSONFile implements IFileRules {

    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    private static final String MATCH_DATE = "[0-9]{2}\\/[0-9]{2}\\/[0-9]{4}\\s[0-9]{2}:[0-9]{2}:[0-9]{2}";

    @Override
    public void writeFile(Uri uri, List<ItemSerieEntity> data, FragmentActivity activity) throws IOException, OtiumException {
        Gson gson = new Gson();
        List<ItemSerieGson> itemSerieGsonList = new ArrayList<>();
        for (ItemSerieEntity its: data) {

            if (its.getTitle().contains("\\") || its.getType().contains("\\") || its.getState().contains("\\")
                    || its.getAnnotation().contains("\\") || its.getImage().contains("\\")) {
                throw new OtiumException(String.format("El caracter \"\\\" en el dato '%s' no esta permitido, accion suspendida",
                        its.getTitle()));
            }

            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            String createAt = sdf.format(its.getCreateAt());
            String lastModification = sdf.format(its.getLastModification());
            itemSerieGsonList.add(new ItemSerieGson(its.getTitle().trim(), createAt,
                    lastModification, its.getType().trim(), its.getSeason(), its.getChapter(),
                    its.getState().trim(), its.getAnnotation().trim(), its.getImage().trim()));
        }

        FileJson fileJson = new FileJson(itemSerieGsonList, Config.VERSION_APP, new Type().getAll());
        String json = gson.toJson(fileJson);

        OutputStream outputStream = activity.getContentResolver().openOutputStream(uri);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
        bw.write(json);
        bw.flush();
        bw.close();

    }

    @Override
    public List<ItemSerieEntity> readFile(Uri uri, FragmentActivity activity) throws IOException, FileNotFoundException, OtiumException, ParseException {
        String json = "";

        InputStream inputStream = activity.getContentResolver().openInputStream(uri);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        String text = "";
        while ((text = br.readLine()) != null) {
            json += text;
        }

        Gson gson = new Gson();
        FileJson fileJson = gson.fromJson(json, FileJson.class);
        List<ItemSerieEntity> result = new ArrayList<>();
        List<ItemSerieGson> itgList = fileJson.getItemSerieGson();

        for (ItemSerieGson itg: itgList) {
            if (itg.getTitle().contains("\\") || itg.getType().contains("\\") || itg.getState().contains("\\")
                    || itg.getAnnotation().contains("\\") || itg.getImage().contains("\\")) {
                throw new OtiumException("El caracter \"\\\" no esta permitido, accion suspendida");
            }

            if (!itg.getCreateAt().matches(MATCH_DATE) || !itg.getLastModification().matches(MATCH_DATE)) {
                throw new OtiumException("Las fechas no son validas, deben de seguir el formato: "
                        + DATE_FORMAT);
            }
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Date createAt = sdf.parse(itg.getCreateAt());
            Date lastModification = sdf.parse(itg.getLastModification());
            result.add(new ItemSerieEntity(0, itg.getTitle(), createAt, lastModification,
                    itg.getType(), itg.getSeason(), itg.getChapter(), itg.getState(), itg.getAnnotation(),
                    itg.getImage()));

        }

        return result;
    }
}
