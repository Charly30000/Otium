package com.charly.otium.common;

import java.util.ArrayList;

public class State {
    public static final String LEIDO = "Leido";
    public static final String PENDIENTE = "Pendiente";
    public static final String SIGUIENDO = "Siguiendo";
    public static final String FAVORITO = "Favorito";
    public static final String LO_TENGO = "Lo tengo";
    public static final String ABANDONADO = "Abandonado";
    public static final String FINALIZADO = "Finalizado";

    public ArrayList<String> getAll() {
        ArrayList<String> states = new ArrayList<>();
        states.add(LEIDO);
        states.add(PENDIENTE);
        states.add(SIGUIENDO);
        states.add(FAVORITO);
        states.add(LO_TENGO);
        states.add(ABANDONADO);
        states.add(FINALIZADO);
        return states;
    }
}
