package com.jolgorio.jolgorioapp.data.dummy;

import com.jolgorio.jolgorioapp.data.model.JolgorioAchievement;

import java.util.ArrayList;

public class LogrosDummy {

    private static ArrayList<JolgorioAchievement> artisticas = new ArrayList<>();
    private static ArrayList<JolgorioAchievement> deportivas = new ArrayList<>();
    private static ArrayList<JolgorioAchievement> culturales = new ArrayList<>();
    private static String sampleTextArtistica ="Completar 10 actividades artísticas";
    private static String sampleTextDeportiva ="Completar 10 actividades Deportivas";
    private static String sampleTextCultural ="Completar 10 actividades culturales";


    private static void loadData(){
        JolgorioAchievement logro1 = new JolgorioAchievement(1, 1, sampleTextArtistica);
        JolgorioAchievement logro2 = new JolgorioAchievement(2, 1, sampleTextArtistica);
        JolgorioAchievement logro3 = new JolgorioAchievement(3, 1, sampleTextArtistica);
        JolgorioAchievement logro4 = new JolgorioAchievement(4, 1, sampleTextArtistica);
        JolgorioAchievement logro5 = new JolgorioAchievement(5, 2, sampleTextDeportiva);
        JolgorioAchievement logro6 = new JolgorioAchievement(6, 2, sampleTextDeportiva);
        JolgorioAchievement logro7 = new JolgorioAchievement(7, 2, sampleTextDeportiva);
        JolgorioAchievement logro8 = new JolgorioAchievement(8, 2, sampleTextDeportiva);
        JolgorioAchievement logro9 = new JolgorioAchievement(9, 3, sampleTextCultural);
        JolgorioAchievement logro10 = new JolgorioAchievement(10, 3, sampleTextCultural);
        JolgorioAchievement logro11 = new JolgorioAchievement(11, 3, sampleTextCultural);
        JolgorioAchievement logro12 = new JolgorioAchievement(12, 3, sampleTextCultural);

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

    public static ArrayList<JolgorioAchievement> getArtisticas(){
        if(artisticas.isEmpty()){
            loadData();
        }
        return artisticas;
    }

    public static ArrayList<JolgorioAchievement> getDeportiva(){
        if(deportivas.isEmpty()){
            loadData();
        }
        return deportivas;
    }

    public static ArrayList<JolgorioAchievement> getCultural(){
        if(culturales.isEmpty()){
            loadData();
        }
        return culturales;
    }
}
