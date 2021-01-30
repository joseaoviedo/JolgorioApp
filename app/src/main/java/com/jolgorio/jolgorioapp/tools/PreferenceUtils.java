package com.jolgorio.jolgorioapp.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {
    public static boolean isUserLogedIn(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("default", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("loggedInUserId", "").equals("")){
            return false;
        }
        return true;
    }

    public static String getLoggedInUserId(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("default", Context.MODE_PRIVATE);
        return sharedPreferences.getString("loggedInUserId","");
    }
}
