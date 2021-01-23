package com.jolgorio.jolgorioapp.repositories;

import androidx.lifecycle.MutableLiveData;

import com.jolgorio.jolgorioapp.data.dummy.ContactsDummy;
import com.jolgorio.jolgorioapp.data.model.JolgorioUser;

import java.util.ArrayList;
import java.util.List;

public class ContactRepository {
    private static boolean dataLoaded;
    private static ArrayList<JolgorioUser> contacts;
    private static ArrayList<JolgorioUser> favContacts;
    private static ContactRepository instance;

    public static ContactRepository getInstance(){
        if(instance == null){
            instance = new ContactRepository();
        }
        return instance;
    }

    private ContactRepository(){
        dataLoaded = false;
        contacts = new ArrayList<>();
        favContacts = new ArrayList<>();
    }

    public ArrayList<JolgorioUser> getFavContacts() {
        return favContacts;
    }


    public  ArrayList<JolgorioUser> getContacts(){
        return contacts;
    }

    public boolean isFavorite(JolgorioUser contact){
        return favContacts.contains(contact);
    }

    public void addFavorite(JolgorioUser contact){
        if (!favContacts.contains(contact)){
            favContacts.add(contact);
        }
    }

    public void removeFavorite(JolgorioUser contact){
        favContacts.remove(contact);
    }

    public void commitChanges(){}

    public void loadData(){
        dataLoaded = true;
        if(contacts.isEmpty()){
            contacts = ContactsDummy.getContacts();
        }
        if(favContacts.isEmpty()){
            favContacts = ContactsDummy.getFavContacts();
        }
    }
}
