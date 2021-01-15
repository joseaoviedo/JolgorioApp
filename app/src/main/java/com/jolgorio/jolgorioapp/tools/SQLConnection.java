package com.jolgorio.jolgorioapp.tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnection {
    private static SQLConnection instance;
    private Connection connection;

    public static synchronized SQLConnection getInstance(){
        if(instance != null){
            return instance;
        }

        else{
           instance = new SQLConnection();
           return instance;
        }
    }

    private SQLConnection(){
        String connectionUrl =
                "jdbc:sqlserver://" + Configuration.getSqlServerURL() + ";"
                        + "database=" + Configuration.getSqlDatabase() + ";"
                        + "user=" + Configuration.getSqlUsername() + ";"
                        + "password="+  Configuration.getSqlPassword() + ";"
                        + "encrypt=true;"
                        + "trustServerCertificate=false;";
        try{
            connection = DriverManager.getConnection(connectionUrl);
            return;
        }catch(Exception e){
            return;
        }
    }

    public ResultSet executeQuery(String query){
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
