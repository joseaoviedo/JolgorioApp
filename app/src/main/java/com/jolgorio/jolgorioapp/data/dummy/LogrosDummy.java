package com.jolgorio.jolgorioapp.data.dummy;

import com.jolgorio.jolgorioapp.data.model.JolgorioLogro ;
import com.jolgorio.jolgorioapp.data.model.JolgorioUser;

import java.util.ArrayList;

public class LogrosDummy {

    private static ArrayList<JolgorioLogro > artisticas = new ArrayList<>();
    private static ArrayList<JolgorioLogro > deportivas = new ArrayList<>();
    private static ArrayList<JolgorioLogro > culturales = new ArrayList<>();
    private static String sampleTextArtistica ="Completar 10 actividades artísticas";
    private static String sampleTextDeportiva ="Completar 10 actividades Deportivas";
    private static String sampleTextCultural ="Completar 10 actividades culturales";


    private static void loadData(){
        JolgorioLogro logro1 = new JolgorioLogro(1, 1, sampleTextArtistica);
        JolgorioLogro  logro2 = new JolgorioLogro(2, 1, sampleTextArtistica);
        JolgorioLogro  logro3 = new JolgorioLogro(3, 1, sampleTextArtistica);
        JolgorioLogro  logro4 = new JolgorioLogro(4, 1, sampleTextArtistica);
        JolgorioLogro  logro5 = new JolgorioLogro(5, 2, sampleTextDeportiva);
        JolgorioLogro  logro6 = new JolgorioLogro(6, 2, sampleTextDeportiva);
        JolgorioLogro  logro7 = new JolgorioLogro(7, 2, sampleTextDeportiva);
        JolgorioLogro  logro8 = new JolgorioLogro(8, 2, sampleTextDeportiva);
        JolgorioLogro  logro9 = new JolgorioLogro(9, 3, sampleTextCultural);
        JolgorioLogro  logro10 = new JolgorioLogro(10, 3, sampleTextCultural);
        JolgorioLogro  logro11 = new JolgorioLogro(11, 3, sampleTextCultural);
        JolgorioLogro  logro12 = new JolgorioLogro(12, 3, sampleTextCultural);

        artisticas.add(logro1);
        artisticas.add(logro2);
        artisticas.add(logro3);
        artisticas.add(logro4);

        deportivas.add(logro5);
        deportivas.add(logro6);
        deportivas.add(logro7);
        deportivas.add(logro8);

        culturales.add(logro9);
        culturales.add(logro10);
        culturales.add(logro11);
        culturales.add(logro12);
    }

    public static ArrayList<JolgorioLogro> getArtisticas(){
        if(artisticas.isEmpty()){
            loadData();
        }
        return artisticas;
    }

    public static ArrayList<JolgorioLogro> getDeportiva(){
        if(deportivas.isEmpty()){
            loadData();
        }
        return deportivas;
    }

    public static ArrayList<JolgorioLogro> getCultural(){
        if(culturales.isEmpty()){
            loadData();
        }
        return culturales;
    }
}
