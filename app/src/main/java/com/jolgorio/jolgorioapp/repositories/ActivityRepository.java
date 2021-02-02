package com.jolgorio.jolgorioapp.repositories;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.google.android.play.core.tasks.Task;
import com.google.errorprone.annotations.RestrictedApi;
import com.jolgorio.jolgorioapp.data.dummy.ActivityDummy;
import com.jolgorio.jolgorioapp.data.model.JolgorioActivity;
import com.jolgorio.jolgorioapp.tools.Configuration;
import com.jolgorio.jolgorioapp.tools.RestAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ActivityRepository {
    private static ActivityRepository instance;
    private ArrayList<JolgorioActivity> activities;
    private boolean isLoaded;

    public static ActivityRepository getInstance(){
        if(instance == null){
            instance = new ActivityRepository();
        }
        return instance;
    }

    public void loadData(){
        if(isLoaded){
            return;
        }
        try {
            activities = loadAllActivities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        isLoaded = true;
    }

    public ArrayList<JolgorioActivity> getActivities(){
        return activities;
    }

    public String processMaterials(JSONArray array){
        String result = "";
        try{
            for(int i = 0; i < array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                result = result + (object.getString("nombre") + "\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<JolgorioActivity> loadAllActivities() throws IOException, ExecutionException, InterruptedException {
       /* JSONArray response = new RestAPI.RestQuery().execute(Configuration.getRestApiUrl() + "actividades").get();
        ArrayList<JolgorioActivity> result = new ArrayList<>();
        try{
            for(int i = 0; i < response.length(); i++){
                JSONObject object = response.getJSONObject(i);
                int id = object.getInt("idactividad");
                int type = object.getInt("tipo");
                String title = object.getString("titulo");
                String desc = object.getString("descripcion");
                String videoURL = object.getString("enlace");
                String timeDesc = object.getString("descripcion_tmp");
                JSONArray materials = new RestAPI.RestQuery().execute(Configuration.getRestApiUrl() + "materiales/" + id).get();
                String matertialsAsString = processMaterials(materials);
                JolgorioActivity act = new JolgorioActivity(id, type, title, desc, matertialsAsString,
                        videoURL, timeDesc, false);
                result.add(act);
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/
        return ActivityDummy.getDummyData();
    }

}
