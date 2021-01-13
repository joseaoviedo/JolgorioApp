package com.jolgorio.jolgorioapp.tools;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.*;

public class Security {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getHash(String input){
        byte[] inputBytes = input.getBytes();
        String hashValue = "";
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(inputBytes);
            byte[] digestedBytes = messageDigest.digest();
            hashValue = digestedBytes.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return hashValue;
    }
}
