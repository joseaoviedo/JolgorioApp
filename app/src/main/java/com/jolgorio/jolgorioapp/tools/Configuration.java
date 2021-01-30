package com.jolgorio.jolgorioapp.tools;

public class Configuration {
    private static String sqlServerURL = "sql5.freemysqlhosting.net";
    private static String sqlDatabase = "sql5389294";
    private static String sqlUsername = "sql5389294";
    private static String sqlPassword = "4TdnTSsNPY";
    private static String sqlMainSchema = "sql5389294";

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
