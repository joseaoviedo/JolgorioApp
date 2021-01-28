package com.jolgorio.jolgorioapp.data.dummy;

import com.google.api.client.util.DateTime;
import com.jolgorio.jolgorioapp.data.model.JolgorioCallLog;
import com.jolgorio.jolgorioapp.data.model.JolgorioUser;

import java.util.ArrayList;
import java.util.Date;

public class CallLogDummy {
    private static ArrayList<JolgorioCallLog> callLogs;

    public static void loadData(){
        callLogs = new ArrayList<>();
        ArrayList<JolgorioUser> contacts = ContactsDummy.getContacts();
    }

    public static ArrayList<JolgorioCallLog> getCallLogs(){
        loadData();
        return callLogs;
    }
}
