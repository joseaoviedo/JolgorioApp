package com.jolgorio.jolgorioapp.tools;
import android.util.Log;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnection {
    private static SQLConnection instance;
    private Connection connection;

    public static synchronized SQLConnection getInstance(){
        if(instance == null) {
            instance = new SQLConnection();
        }
        return instance;
    }

    private SQLConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://sql5.freemysqlhosting.net/sql5389294",
                    "sql5389294", "4TdnTSsNPY");
        }catch(Exception e){
            Log.d("SQL", "Fallo al conectar con la base de datos SQL");
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String query){
        if(connection == null){
            return null;
        }
        ResultSet resultSet = null;
        try{
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            return resultSet;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
