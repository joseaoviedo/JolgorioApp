package com.jolgorio.jolgorioapp.data.model;

import com.jolgorio.jolgorioapp.tools.Configuration;
import com.jolgorio.jolgorioapp.tools.SQLConnection;

public class UserDAO {
    private SQLConnection connection;
    private String schema;

    public UserDAO(){
        connection = SQLConnection.getInstance();
        schema = Configuration.getSqlMainSchema();
    }

    public User getUserById(int id){
        String query = "SELECT * FROM " + schema + ".Usuario WHERE idUsuario = " + id;
        return null;
    }
}
