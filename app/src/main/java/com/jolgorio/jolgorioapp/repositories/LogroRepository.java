package com.jolgorio.jolgorioapp.repositories;

import com.jolgorio.jolgorioapp.data.dummy.ContactsDummy;
import com.jolgorio.jolgorioapp.data.dummy.LogrosDummy;
import com.jolgorio.jolgorioapp.data.model.JolgorioLogro;
import com.jolgorio.jolgorioapp.data.model.JolgorioUser;

import java.util.ArrayList;

public class LogroRepository {

    private static boolean dataLoaded;
    private static ArrayList<JolgorioLogro> logroArtistico;
    private static ArrayList<JolgorioLogro> logroDeportivo;
    private static ArrayList<JolgorioLogro> logroCultural;
    private static LogroRepository instance;

    public static LogroRepository getInstance(){
        if(instance == null){
            instance = new LogroRepository();
        }
        return instance;
    }

    private LogroRepository(){
        dataLoaded = false;
        logroArtistico = new ArrayList<>();
        logroDeportivo = new ArrayList<>();
        logroCultural = new ArrayList<>();
    }

    public ArrayList<JolgorioLogro> getLogrosArtisticos() {
        return logroArtistico;
    }

    public ArrayList<JolgorioLogro> getLogrosDeportivo() {
        return logroDeportivo;
    }

    public ArrayList<JolgorioLogro> getLogrosCultural() {
        return logroCultural;
    }

    public void commitChanges(){}

    public void loadData(){
        dataLoaded = true;
        if(logroArtistico.isEmpty()){
            logroArtistico = LogrosDummy.getArtisticas();
        }
        if(logroDeportivo.isEmpty()){
            logroDeportivo = LogrosDummy.getDeportiva();
        }
        if(logroCultural.isEmpty()){
            logroCultural = LogrosDummy.getCultural();
        }
    }
}
