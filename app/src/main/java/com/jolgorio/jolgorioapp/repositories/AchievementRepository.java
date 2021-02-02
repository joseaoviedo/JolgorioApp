package com.jolgorio.jolgorioapp.repositories;

import com.jolgorio.jolgorioapp.data.model.JolgorioAchievement;
import com.jolgorio.jolgorioapp.tools.Configuration;
import com.jolgorio.jolgorioapp.tools.RestAPI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AchievementRepository {

    private boolean dataLoaded;
    private ArrayList<JolgorioAchievement> type1;
    private ArrayList<JolgorioAchievement> type2;
    private ArrayList<JolgorioAchievement> type3;
    private static AchievementRepository instance;


    public static AchievementRepository getInstance(){
        if(instance == null){
            instance = new AchievementRepository();
        }
        return instance;
    }

    private AchievementRepository(){
        dataLoaded = false;
        type1 = new ArrayList<>();
        type2 = new ArrayList<>();
        type3 = new ArrayList<>();
    }

    public ArrayList<JolgorioAchievement> getType1() {
        return type1;
    }

    public ArrayList<JolgorioAchievement> getType2() {
        return type2;
    }

    public ArrayList<JolgorioAchievement> getType3() {
        return type3;
    }



    public ArrayList<JolgorioAchievement> loadType1(){
        ArrayList<JolgorioAchievement> result = new ArrayList<>();
        try{
            JSONArray response = new RestAPI.GetQuery().execute(Configuration.restApiUrl +
                    "logros/" + 1 + "/").get();
            for(int i = 0; i < response.length(); i++){
                JSONObject object = response.getJSONObject(i);
                int id = object.getInt("idlogro");
                int tipo = object.getInt("tipo");
                String desc = object.getString("descripcion");
                JolgorioAchievement ach = new JolgorioAchievement(id, tipo, desc);
                result.add(ach);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<JolgorioAchievement> loadType2(){
        ArrayList<JolgorioAchievement> result = new ArrayList<>();
        try{
            JSONArray response = new RestAPI.GetQuery().execute(Configuration.restApiUrl +
                    "logros/" + 2 + "/").get();
            for(int i = 0; i < response.length(); i++){
                JSONObject object = response.getJSONObject(i);
                int id = object.getInt("idlogro");
                int tipo = object.getInt("tipo");
                String desc = object.getString("descripcion");
                JolgorioAchievement ach = new JolgorioAchievement(id, tipo, desc);
                result.add(ach);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<JolgorioAchievement> loadType3(){
        ArrayList<JolgorioAchievement> result = new ArrayList<>();
        try{
            JSONArray response = new RestAPI.GetQuery().execute(Configuration.restApiUrl +
                    "logros/" + 3 + "/").get();
            for(int i = 0; i < response.length(); i++){
                JSONObject object = response.getJSONObject(i);
                int id = object.getInt("idlogro");
                int tipo = object.getInt("tipo");
                String desc = object.getString("descripcion");
                JolgorioAchievement ach = new JolgorioAchievement(id, tipo, desc);
                result.add(ach);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void loadData() {
        if (!dataLoaded) {
            type1 = loadType1();
            type2 = loadType2();
            type3 = loadType3();
        }
        dataLoaded = true;
    }

}
