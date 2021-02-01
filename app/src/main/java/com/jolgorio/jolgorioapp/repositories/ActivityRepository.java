package com.jolgorio.jolgorioapp.repositories;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.google.android.play.core.tasks.Task;
import com.google.errorprone.annotations.RestrictedApi;
import com.jolgorio.jolgorioapp.data.model.JolgorioActivity;
import com.jolgorio.jolgorioapp.tools.Configuration;
import com.jolgorio.jolgorioapp.tools.RestAPI;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ActivityRepository {
    private static ActivityRepository instance;
    private ArrayList<JolgorioActivity> activities;


    public static ActivityRepository getInstance(){
        if(instance == null){
            instance = new ActivityRepository();
        }
        return instance;
    }

    public void loadData(){
        
    }



    public ArrayList<JolgorioActivity> loadAllActivities() throws IOException {
        new RestAPI.RestQuery().execute(Configuration.getRestApiUrl() + "actividades");
        return null;
    }

}
