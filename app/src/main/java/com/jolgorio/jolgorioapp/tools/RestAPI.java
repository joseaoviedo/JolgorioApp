package com.jolgorio.jolgorioapp.tools;

import android.os.AsyncTask;
import android.util.Log;

import com.jolgorio.jolgorioapp.repositories.ActivityRepository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RestAPI {
    public static class PostQuery extends AsyncTask<String, Void, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            String url = strings[0];
            String postJSON = strings[1];
            JSONObject result = null;
            try {
                URL obj = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                try(OutputStream os = connection.getOutputStream()){
                    byte[] input = postJSON.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
                try(BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println(response.toString());
                    result = new JSONObject(response.toString());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }
    }

    public static class GetQuery extends AsyncTask<String, Void, JSONArray>{

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

    public static class GetQuerySingle extends AsyncTask<String, Void, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... urls) {
            String url = urls[0];
            JSONObject result = null;
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
                result = new JSONObject(response.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }
    }
}
