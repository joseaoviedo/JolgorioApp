package com.jolgorio.jolgorioapp.data.dummy;

import android.os.Parcelable;

import com.jolgorio.jolgorioapp.data.model.JolgorioLogro ;

import java.util.ArrayList;

public class LogrosDummy {

    private static ArrayList<JolgorioLogro > dummy = new ArrayList<>();
    private static String sampleTextArtistica ="Completar 10 actividades artísticas";
    private static String sampleTextDeportiva ="Completar 10 actividades Deportivas";
    private static String sampleTextCultural ="Completar 10 actividades culturales";


    public static ArrayList<JolgorioLogro > getDummyData(){
        if(dummy.isEmpty()){
            loadData();
        }
        return dummy;
    }

    public JolgorioLogro getByID(int id){
        if(dummy.isEmpty()){
            loadData();
        }

        for(JolgorioLogro j: dummy){
            if(j.getId() == id){
                return j;
            }
        }
        return null;
    }

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

        dummy.add(logro1);
        dummy.add(logro2);
        dummy.add(logro3);
        dummy.add(logro4);
        dummy.add(logro5);
        dummy.add(logro6);
        dummy.add(logro7);
        dummy.add(logro8);
        dummy.add(logro9);
        dummy.add(logro10);
        dummy.add(logro11);
        dummy.add(logro12);
    }
}
