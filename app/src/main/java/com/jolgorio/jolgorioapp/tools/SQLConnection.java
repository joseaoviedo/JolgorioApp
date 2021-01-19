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
                "jdbc:mysql://" + Configuration.getSqlServerURL() + "/" + Configuration.getSqlDatabase() + "?cloudSqlInstance=phonic-vortex-258523:us-central1:jolgoriotest&socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=" + Configuration.getSqlUsername() + "&password=" + Configuration.getSqlPassword();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(connectionUrl, Configuration.getSqlUsername(), Configuration.getSqlPassword());
            System.out.println("==========Conectado===============");
        }catch(Exception e){
            e.printStackTrace();
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
