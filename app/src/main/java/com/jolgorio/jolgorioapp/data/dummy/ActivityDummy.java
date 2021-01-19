package com.jolgorio.jolgorioapp.data.dummy;

import com.jolgorio.jolgorioapp.data.model.JolgorioActivity;

import java.util.ArrayList;

public class ActivityDummy {

    public static ArrayList<JolgorioActivity> getDummyData(){
        ArrayList<JolgorioActivity> dummy = new ArrayList<>();

        JolgorioActivity act1 = new JolgorioActivity(1, 1, "Pintar botellas", "DEFAULT", "DEFAULT", "DEFAULT", "DEFAULT", true);
        JolgorioActivity act2 = new JolgorioActivity(2, 1, "Pintar óleo", "DEFAULT", "DEFAULT", "DEFAULT", "DEFAULT", false);
        JolgorioActivity act3 = new JolgorioActivity(3, 1, "Pintar acuarela", "DEFAULT", "DEFAULT", "DEFAULT", "DEFAULT", false);
        JolgorioActivity act4 = new JolgorioActivity(4, 1, "Dibujo a lapiz", "DEFAULT", "DEFAULT", "DEFAULT", "DEFAULT", false);
        JolgorioActivity act5 = new JolgorioActivity(5, 2, "Zumba", "DEFAULT", "DEFAULT", "DEFAULT", "DEFAULT", true);
        JolgorioActivity act6 = new JolgorioActivity(6, 2, "Caminata", "DEFAULT", "DEFAULT", "DEFAULT", "DEFAULT", false);
        JolgorioActivity act7 = new JolgorioActivity(7, 2, "Yoga", "DEFAULT", "DEFAULT", "DEFAULT", "DEFAULT", true);
        JolgorioActivity act8 = new JolgorioActivity(8, 2, "Estiramientos", "DEFAULT", "DEFAULT", "DEFAULT", "DEFAULT", false);
        JolgorioActivity act9 = new JolgorioActivity(9, 3, "Leyendas costarriceses", "DEFAULT", "DEFAULT", "DEFAULT", "DEFAULT", false);
        JolgorioActivity act10 = new JolgorioActivity(10, 3, "Música de los 60s", "DEFAULT", "DEFAULT", "DEFAULT", "DEFAULT", true);
        JolgorioActivity act11 = new JolgorioActivity(11, 3, "Cuentos populares", "DEFAULT", "DEFAULT", "DEFAULT", "DEFAULT", false);
        JolgorioActivity act12 = new JolgorioActivity(12, 3, "Artistas costarricenses", "DEFAULT", "DEFAULT", "DEFAULT", "DEFAULT", true);

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

        return dummy;
    }
}
