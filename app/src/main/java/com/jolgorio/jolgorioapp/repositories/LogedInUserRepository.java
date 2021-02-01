package com.jolgorio.jolgorioapp.repositories;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.jolgorio.jolgorioapp.data.model.JolgorioUser;
import com.jolgorio.jolgorioapp.tools.Configuration;

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
        user = new JolgorioUser(01,"14141515", "juancho@test.com", "Juancho", "Juancho", "Juancho", "https://images.squarespace-cdn.com/content/v1/5a0dd6831f318dcf5130a0d5/1604423127155-BG7RY98W1AEAXSF4ME44/ke17ZwdGBToddI8pDm48kJ_EVeTaLbkp1ZvFgIdkixp7gQa3H78H3Y0txjaiv_0fDoOvxcdMmMKkDsyUqMSsMWxHk725yiiHCCLfrh8O1z5QPOohDIaIeljMHgDF5CVlOqpeNLcJ80NK65_fV7S1UZRyDxde4pqLy1X6gpjxcbRkC_kl2YmpjbA5JCKclEdkFy8rxPAX8IibwtzfQZemqQ/julia-press.jpeg?format=2500w");
        firebaseAuth = FirebaseAuth.getInstance();
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
