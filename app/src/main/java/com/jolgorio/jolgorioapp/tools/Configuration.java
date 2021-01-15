package com.jolgorio.jolgorioapp.tools;

public class Configuration {
    private static String sqlServerURL = "32.232.179.89:3306";
    private static String sqlDatabase = "jolgorio";
    private static String sqlUsername = "jolgorioapp";
    private static String sqlPassword = "jolgoriotest";
    private static String sqlMainSchema = "application";

    public static String getSqlServerURL(){
        return sqlServerURL;
    }

    public static String getSqlUsername(){
        return sqlUsername;
    }

    public static String getSqlPassword(){
        return sqlPassword;
    }

    public static String getSqlDatabase(){
        return sqlDatabase;
    }

    public static String getSqlMainSchema(){
        return sqlMainSchema;
    }
}
