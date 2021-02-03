package com.jolgorio.jolgorioapp.repositories;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.google.android.play.core.tasks.Task;
import com.google.errorprone.annotations.RestrictedApi;
import com.jolgorio.jolgorioapp.data.model.JolgorioActivity;
import com.jolgorio.jolgorioapp.data.model.JolgorioUser;
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
    private float type1Completed;
    private float type2Completed;
    private float type3Completed;

    /*
    Este repositorio almacena la información de las actividades, la misma es retribuida por medio
    del API Rest
     */
    public static ActivityRepository getInstance(){
        if(instance == null){
            instance = new ActivityRepository();
        }
        return instance;
    }

    /*
    Carga los datos si no están cargados
     */
    public void loadData(){
        if(isLoaded){
            return;
        }
        try {
            activities = loadAllActivities();
            verifyCompleted();
        } catch (Exception e) {
            e.printStackTrace();
        }
        isLoaded = true;
    }

    /*
    Establece una actividad como completada, tanto a nivel local como a nivel remoto
     */
    public void onActivityComplete(JolgorioActivity act){
        int type = act.getType();
        try {
            JolgorioUser user = LogedInUserRepository.getInstance().getLogedInUser();
            String jsonToSend = "{\"idusuario\":" + user.getId() +  ", \"idactividad\":" + act.getId() +"}";
            JSONObject response = new RestAPI.PostQuery().execute(Configuration.restApiUrl +
                    "actividades/completada", jsonToSend).get();
            if(response.getString("status").equals("success")) {
                act.setCompleted(true);
                switch (type) {
                    case 1:
                        type1Completed++;
                        break;
                    case 2:
                        type2Completed++;
                        break;
                    case 3:
                        type3Completed++;
                        break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<JolgorioActivity> getActivities(){
        return activities;
    }

    /*
    Procesa la lista de materiales y devuelve un string de la lista de materiales acomodada
     */
    public String processMaterials(JSONArray array){
        String result = "";
        try{
            for(int i = 0; i < array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                result = result + ("-" + object.getString("nombre") + "\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /*
    Trae todas las actividades completadas por el usuario el último mes y las compara con las que
    hay en el repositorio para determinar si ya han sido completadas
     */
    public void verifyCompleted(){
        try{
            JolgorioUser user = LogedInUserRepository.getInstance().getLogedInUser();
            JSONArray response = new RestAPI.GetQuery().execute(Configuration.restApiUrl +
                    "actividades/" + user.getId() + "/mesactual").get();
            for(int i = 0; i < response.length(); i++){
                JSONObject object = response.getJSONObject(i);
                int id = object.getInt("idactividad");
                switch (object.getInt("tipo")){
                    case 1:
                        type1Completed++;
                        break;
                    case 2:
                        type2Completed++;
                        break;
                    case 3:
                        type3Completed++;
                        break;
                }
                for(JolgorioActivity act: activities){
                    if(id == act.getId()){
                        act.setCompleted(true);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /*
    Calcula el porcentaje de completitud de las actividades basado en la cantidad máxima establecida
     */
    public float calculateActivityProgress(int type){
        switch (type){
            case 1:
                return (type1Completed / Configuration.MAX_ACTIVITY_NUMBER) * 100;
            case 2:
                return (type2Completed / Configuration.MAX_ACTIVITY_NUMBER) * 100;
            case 3:
                return (type3Completed / Configuration.MAX_ACTIVITY_NUMBER) * 100;
        }
        return 0f;
    }

    /*
    Carga todas las actividades que se encuentren activas en la base de datos, devuelve un arreglo
    de objetos de tipo JolgorioActivity
     */
    public ArrayList<JolgorioActivity> loadAllActivities() throws IOException, ExecutionException, InterruptedException {
        JSONArray response = new RestAPI.GetQuery().execute(Configuration.restApiUrl + "actividades/").get();
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
                JSONArray materials = new RestAPI.GetQuery().execute(Configuration.restApiUrl + "materiales/" + id).get();
                String matertialsAsString = processMaterials(materials);
                JolgorioActivity act = new JolgorioActivity(id, type, title, desc, matertialsAsString,
                        videoURL, timeDesc, false);
                result.add(act);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
