package com.jolgorio.jolgorioapp.repositories;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.jolgorio.jolgorioapp.data.model.JolgorioUser;
import com.jolgorio.jolgorioapp.tools.Configuration;
import com.jolgorio.jolgorioapp.tools.PreferenceUtils;
import com.jolgorio.jolgorioapp.tools.RestAPI;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class LogedInUserRepository {
    private static LogedInUserRepository instance;
    private JolgorioUser user;
    private FirebaseAuth firebaseAuth;
    private String userUniqueId;

    private int gender;
    private int districtID;
    private String birthdate;

    public static LogedInUserRepository getInstance(){
        if(instance == null){
            instance = new LogedInUserRepository();
        }
        return instance;
    }

    private LogedInUserRepository(){
        //Juan: 12341234
        //Miguel: 14141515
        userUniqueId = UUID.randomUUID().toString();
    }

    public void loadLoggedUser(){
        String mail = PreferenceUtils.getInstance().getLoggedInUserMail();
        try{
            JSONObject user = new RestAPI.GetQuerySingle().execute(Configuration.restApiUrl +
                    "usuario/correo/" + mail + "/").get();
            int id = user.getInt("idusuario");
            String name = user.getString("nombre");
            String surname1 = user.getString("apellido1");
            String surname2 = user.getString("apellido2");
            int districtId = user.getInt("iddistrito");
            String email = user.getString("email");
            String birthDate = user.getString("fechanac");
            String number = user.getString("numero");
            int sexo = user.getInt("sexo");
            String photoURL = user.getString("urlfoto");
            if(photoURL.isEmpty()) {
                photoURL = "https://icon-library.com/images/default-user-icon/default-user-icon-4.jpg";
            }
            setExtraData(sexo, districtId, birthDate);
            this.user = new JolgorioUser(id, number, email, name, surname1, surname2, photoURL);
        }catch (Exception e){

        }
    }

    private void setExtraData(int gender, int districtID, String birthdate){
        this.gender = gender;
        this.districtID = districtID;
        this.birthdate = birthdate;
    }

    private int getGender(){
        return gender;
    }

    private int getDistrictID(){
        return districtID;
    }

    private String getBirthdate(){
        return birthdate;
    }

    public String getUserUniqueId(){
        return userUniqueId;
    }

    public JolgorioUser getLogedInUser(){
        return user;
    }

    public void onLogin(Context context) throws IOException {
    }

    public boolean isLogedIn(){
        return user != null;
    }

    public void logOut(){
        user = null;
    }

}
