package com.jolgorio.jolgorioapp.tools;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RestAPI {
    public static class RestQuery extends AsyncTask<String, Void, JSONArray>{

        @Override
        protected JSONArray doInBackground(String... urls) {
            String url = urls[0];
            JSONArray result = null;
            try {
                URL obj = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                connection.setRequestMethod("GET");
                connection.getResponseCode();
                String input;
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer response = new StringBuffer();
                while ((input = in.readLine()) != null) {
                    response.append(input);
                }
                in.close();
                Log.d("RESTFUL API","Response: " + response.toString());
                result = new JSONArray(response.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }
    }
}
