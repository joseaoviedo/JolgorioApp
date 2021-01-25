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

        callLogs.add(new JolgorioCallLog(contacts.get(0), new Date(2021, 1, 2)));
        callLogs.add(new JolgorioCallLog(contacts.get(3), new Date(2020, 2, 16)));
        callLogs.add(new JolgorioCallLog(contacts.get(4), new Date(2020, 6, 19)));
        callLogs.add(new JolgorioCallLog(contacts.get(6), new Date(2021, 1, 3)));
    }

    public static ArrayList<JolgorioCallLog> getCallLogs(){
        loadData();
        return callLogs;
    }
}
