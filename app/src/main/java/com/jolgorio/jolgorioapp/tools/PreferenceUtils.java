package com.jolgorio.jolgorioapp.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {
    private Context mContext;
    private static PreferenceUtils instance;


    public static PreferenceUtils getInstance(){
        if(instance == null){
            instance = new PreferenceUtils();
        }
        return instance;
    }


    public void setMainContext(Context mContext){
        this.mContext = mContext;
    }


    public boolean isUserLogedIn(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("default", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("loggedInUserMail", "").equals("")){
            return false;
        }
        return true;
    }

    public String getLoggedInUserMail(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("default", Context.MODE_PRIVATE);
        return sharedPreferences.getString("loggedInUserMail","");
    }

    public void setLoggedInUserMail(String userMail){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("default", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("loggedInUserMail", userMail);
        editor.commit();
    }

}
