package com.jolgorio.jolgorioapp.repositories;

import android.provider.CallLog;

import com.jolgorio.jolgorioapp.data.dummy.CallLogDummy;
import com.jolgorio.jolgorioapp.data.model.JolgorioCallLog;

import java.util.ArrayList;

public class CallLogRepository {
    private ArrayList<JolgorioCallLog> callLogs;
    private static CallLogRepository instance;
    private boolean dataLoaded;

    private CallLogRepository(){
        callLogs = new ArrayList<>();
        dataLoaded = false;
    }

    public static CallLogRepository getInstance(){
        if(instance == null){
            instance = new CallLogRepository();
        }
        return instance;
    }

    public ArrayList<JolgorioCallLog> getCallLogs(){
        return callLogs;
    }

    public void addCallLog(JolgorioCallLog callLog){
        if(!callLogs.contains(callLog)){
            callLogs.add(callLog);
        }
    }

    public void removeCallLog(JolgorioCallLog callLog){
        callLogs.remove(callLog);
    }

    public void loadData(){
        if(!dataLoaded){
            callLogs = CallLogDummy.getCallLogs();
            dataLoaded = true;
        }
    }

    public void commitChanges(){

    }
}
