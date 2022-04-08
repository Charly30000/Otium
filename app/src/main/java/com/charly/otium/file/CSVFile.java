package com.charly.otium.file;

import android.net.Uri;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.charly.otium.common.State;
import com.charly.otium.common.Type;
import com.charly.otium.config.Config;
import com.charly.otium.exceptions.OtiumException;
import com.charly.otium.models.entities.ItemSerieEntity;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVFile implements IFileRules {

    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    private static final String MATCH_DATE = "[0-9]{2}\\/[0-9]{2}\\/[0-9]{4}\\s[0-9]{2}:[0-9]{2}:[0-9]{2}";

    @Override
    public void writeFile(Uri uri, List<ItemSerieEntity> data, FragmentActivity activity) throws IOException, OtiumException {
        OutputStream outputStream = activity.getContentResolver().openOutputStream(uri);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));

        String header = "title,createAt,lastModification,type,season,chapter,state,annotation,image,version\n";
        bw.write(header);

        for (ItemSerieEntity its: data) {
            ItemSerieEntity itsGood = containsAndReplaceSpecialCharacter(its);
            if (itsGood == null) {
                throw new OtiumException("Por favor, elimina el caracter especial \"\\\". El archivo no se guardara");
            }
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            String createAtDate = sdf.format(its.getCreateAt());
            String lastModifiedDate = sdf.format(its.getLastModification());

            String itsResult = String.format("%s,%s,%s,%s,%d,%d,%s,%s,%s,%s%n",
                    itsGood.getTitle(), createAtDate, lastModifiedDate,
                    itsGood.getType(), itsGood.getSeason(),
                    itsGood.getChapter(), itsGood.getState(), itsGood.getAnnotation(), itsGood.getImage(),
                    Config.VERSION_APP);

            bw.write(itsResult);
        }

        bw.flush();
        bw.close();

    }

    @Override
    public List<ItemSerieEntity> readFile(Uri uri, FragmentActivity activity) throws IOException, FileNotFoundException, OtiumException, ParseException {
        List<ItemSerieEntity> result = new ArrayList<>();
        InputStream inputStream = activity.getContentResolver().openInputStream(uri);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String text;
        int count = 0;
        while ((text = br.readLine()) != null) {
            String[] header = {"title","createAt","lastModification","type","season","chapter","state","annotation","image", "version"};
            if (count == 0) {
                String[] headerFile = text.split(",");
                if (headerFile.length != header.length) {
                    throw new OtiumException("Los parametros de la cabecera no son correctos");
                }
                for (int i = 0; i < header.length; i++) {
                    if (!header[i].equals(headerFile[i])) {
                        throw new OtiumException("Los parametros de la cabecera no son correctos");
                    }
                }
                count++;
                continue;
            }
            String[] params = text.split(",");
            if (params.length != header.length) {
                throw new OtiumException("La cantidad de parametros es incorrecta");
            }
            Log.d("params", text);
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            if (!params[1].matches(MATCH_DATE) || !params[2].matches(MATCH_DATE)) {
                throw new OtiumException("Las fechas no son validas, deben de seguir el formato: "
                        + DATE_FORMAT);
            }
            Date createAt = sdf.parse(params[1]);
            Date lastModified = sdf.parse(params[2]);
            int season = Integer.parseInt(params[4]);
            int chapter = Integer.parseInt(params[5]);
            verifyType(params[3]);
            verifyState(params[6]);

            Map<String, Object> data = new HashMap<>();
            data.put("title", params[0].replace("\\c", ",").trim());
            data.put("createAt", createAt);
            data.put("lastModification", lastModified);
            data.put("type", params[3].replace("\\c", ",").trim());
            data.put("season", season);
            data.put("chapter", chapter);
            data.put("state", params[6].replace("\\c", ",").trim());
            data.put("annotation", params[7].replace("\\n", "\n").replace("\\c", ",").trim());
            data.put("image", params[8].replace("\\c", ",").trim());
            data.put("version", params[9]);

            if (cotainsBar(data)) {
                throw new OtiumException("No se pueden almacenar parametros con el caracter '\\'");
            }

            ItemSerieEntity its = new ItemSerieEntity(0, (String) data.get("title"), (Date) data.get("createAt"),
                    (Date) data.get("lastModification"), (String) data.get("type"), (int) data.get("season"),
                    (int) data.get("chapter"), (String) data.get("state"),
                    (String) data.get("annotation"), (String) data.get("image"));

            if (its.getTitle().isEmpty() || its.getSeason() < 0 || its.getChapter() < 0) {
                throw new OtiumException("Deben de rellenarse todos los parametros necesarios");
            }

            result.add(its);

            count++;
        }
        return result;
    }

    private boolean cotainsBar(Map<String, Object> values) {
        if (((String) values.get("title")).contains("\\") || ((String) values.get("type")).contains("\\")
        || ((String) values.get("state")).contains("\\") || ((String) values.get("annotation")).contains("\\")
        || ((String) values.get("image")).contains("\\")) {
            return true;
        }
        return false;
    }

    private void verifyType(String param) throws OtiumException {
        ArrayList<String> allTypes = new Type().getAll();
        boolean finded = false;
        for (String s: allTypes) {
            if (s.equals(param)) {
                finded = true;
                break;
            }
        }
        if (!finded) {
            throw new OtiumException(String.format("El type '%s' no es valido", param));
        }
    }

    private void verifyState(String param) throws OtiumException {
        ArrayList<String> allStates = new State().getAll();
        boolean finded = false;
        for (String s: allStates) {
            if (s.equals(param)) {
                finded = true;
                break;
            }
        }
        if (!finded) {
            throw new OtiumException(String.format("El state '%s' no es valido", param));
        }
    }

    private ItemSerieEntity containsAndReplaceSpecialCharacter(ItemSerieEntity its) {
        if (its.getTitle().contains("\\") || its.getType().contains("\\") || its.getState().contains("\\")
                || its.getAnnotation().contains("\\") || its.getImage().contains("\\")) {
            return null;
        }
        ItemSerieEntity itemSerieEntity = new ItemSerieEntity(its.getItemSerieId(),
                its.getTitle().replace("\n", "\\n").replace(",", "\\c").trim(),
                its.getCreateAt(),
                its.getLastModification(),
                its.getType().replace("\n", "\\n").replace(",", "\\c").trim(),
                its.getSeason(),
                its.getChapter(),
                its.getState().replace("\n", "\\n").replace(",", "\\c").trim(),
                its.getAnnotation().replace("\n", "\\n").replace(",", "\\c").trim(),
                its.getImage().replace("\n", "\\n").replace(",", "\\c").trim()
        );

        return itemSerieEntity;
    }
}
