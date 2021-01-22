package com.jolgorio.jolgorioapp.data.dummy;

import com.jolgorio.jolgorioapp.data.model.JolgorioActivity;

import java.util.ArrayList;

public class ActivityDummy {
    private static ArrayList<JolgorioActivity> dummy = new ArrayList<>();
    private static String sampleText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed" +
            " do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam" +
            ", quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
            "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat " +
            "nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui " +
            "officia deserunt mollit anim id est laborum.";

    private static String videoLink = "https://www.youtube.com/watch?v=61QSHrOuGEA";

    public static ArrayList<JolgorioActivity> getDummyData(){
        if(dummy.isEmpty()){
            loadData();
        }
        return dummy;
    }

    public JolgorioActivity getByID(int id){
        if(dummy.isEmpty()){
            loadData();
        }

        for(JolgorioActivity j: dummy){
            if(j.getId() == id){
                return j;
            }
        }
        return null;
    }

    private static void loadData(){
        JolgorioActivity act1 = new JolgorioActivity(1, 1, "Pintar botellas", sampleText, sampleText, videoLink, sampleText, true);
        JolgorioActivity act2 = new JolgorioActivity(2, 1, "Pintar óleo", sampleText, sampleText, videoLink, sampleText, false);
        JolgorioActivity act3 = new JolgorioActivity(3, 1, "Pintar acuarela", sampleText, sampleText, videoLink, sampleText, false);
        JolgorioActivity act4 = new JolgorioActivity(4, 1, "Dibujo a lapiz", sampleText, sampleText, videoLink, sampleText, false);
        JolgorioActivity act5 = new JolgorioActivity(5, 2, "Zumba", sampleText, sampleText, videoLink, sampleText, true);
        JolgorioActivity act6 = new JolgorioActivity(6, 2, "Caminata", sampleText, sampleText, videoLink, sampleText, false);
        JolgorioActivity act7 = new JolgorioActivity(7, 2, "Yoga", sampleText, sampleText, videoLink, sampleText, true);
        JolgorioActivity act8 = new JolgorioActivity(8, 2, "Estiramientos", sampleText, sampleText, videoLink, sampleText, false);
        JolgorioActivity act9 = new JolgorioActivity(9, 3, "Leyendas costarriceses", sampleText, sampleText, videoLink, sampleText, false);
        JolgorioActivity act10 = new JolgorioActivity(10, 3, "Música de los 60s", sampleText, sampleText, videoLink, sampleText, true);
        JolgorioActivity act11 = new JolgorioActivity(11, 3, "Cuentos populares", sampleText, sampleText, videoLink, sampleText, false);
        JolgorioActivity act12 = new JolgorioActivity(12, 3, "Artistas costarricenses", sampleText, sampleText, videoLink, sampleText, true);

        dummy.add(act1);
        dummy.add(act2);
        dummy.add(act3);
        dummy.add(act4);
        dummy.add(act5);
        dummy.add(act6);
        dummy.add(act7);
        dummy.add(act8);
        dummy.add(act9);
        dummy.add(act10);
        dummy.add(act11);
        dummy.add(act12);
    }
}
