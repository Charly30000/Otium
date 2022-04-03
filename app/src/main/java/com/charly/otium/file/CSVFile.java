package com.charly.otium.file;

import android.net.Uri;

import androidx.fragment.app.FragmentActivity;

import com.charly.otium.exceptions.OtiumException;
import com.charly.otium.models.entities.ItemSerieEntity;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class CSVFile implements IFileRules {

    @Override
    public void writeFile(Uri uri, List<ItemSerieEntity> data, FragmentActivity activity) throws IOException, OtiumException {
        OutputStream outputStream;
        outputStream = activity.getContentResolver().openOutputStream(uri);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));

        String header = "title,createAt,lastModification,type,season,chapter,state,annotation,image\n";
        bw.write(header);

        for (ItemSerieEntity its: data) {
            ItemSerieEntity itsGood = containsAndReplaceSpecialCharacter(its);
            if (itsGood == null) {
                throw new OtiumException("Por favor, elimina el caracter especial \"\\\". El archivo no se guardara");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String createAtDate = sdf.format(its.getCreateAt());
            String lastModifiedDate = sdf.format(its.getLastModification());

            String itsResult = String.format("%s,%s,%s,%s,%d,%d,%s,%s,%s%n",
                    itsGood.getTitle(), createAtDate, lastModifiedDate,
                    itsGood.getType(), itsGood.getSeason(),
                    itsGood.getChapter(), itsGood.getState(), itsGood.getAnnotation(), itsGood.getImage());

            bw.write(itsResult);
        }

        bw.flush();
        bw.close();

    }

    @Override
    public List<ItemSerieEntity> readFile(String filename) throws IOException, OtiumException {
        /*Convert String to Date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "31-08-1982 10:20:56";
        Date date = sdf.parse(dateInString);
        System.out.println(date); //Tue Aug 31 10:20:56 SGT 1982*/
        return null;
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
