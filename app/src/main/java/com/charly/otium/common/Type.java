package com.charly.otium.common;

import java.util.ArrayList;

public class Type {
    public static final String SERIE = "Serie";
    public static final String ANIME = "Anime";
    public static final String PELICULA = "Pelicula";
    public static final String MANGA = "Manga";
    public static final String LIBRO = "Libro";
    public static final String FANFIC = "FanFic";
    public static final String VIDEOJUEGO = "Videojuego";
    public static final String OTRO = "Otro";

    public ArrayList<String> getAll() {
        ArrayList<String> types = new ArrayList<>();
        types.add(SERIE);
        types.add(ANIME);
        types.add(PELICULA);
        types.add(MANGA);
        types.add(LIBRO);
        types.add(FANFIC);
        types.add(VIDEOJUEGO);
        types.add(OTRO);
        return types;
    }
}
