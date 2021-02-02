package com.jolgorio.jolgorioapp.repositories;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.loader.app.LoaderManager;

import com.jolgorio.jolgorioapp.data.dummy.ContactsDummy;
import com.jolgorio.jolgorioapp.data.model.JolgorioUser;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ContactRepository extends Fragment {
    private static boolean dataLoaded;
    private static ArrayList<JolgorioUser> contacts;
    private static ArrayList<JolgorioUser> favContacts;
    private static ContactRepository instance;




    @SuppressLint("InlinedApi")
    private final static String[] FROM_COLUMNS = {
            Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME
    };
    private final static int[] TO_IDS = {
            android.R.id.text1
    };
    // Define global mutable variables
    // Define a ListView object
    ListView contactsList;
    // Define variables for the contact the user selects
    // The contact's _ID value
    long contactId;
    // The contact's LOOKUP_KEY
    String contactKey;
    // A content URI for the selected contact
    Uri contactUri;
    // An adapter that binds the result Cursor to the ListView
    private SimpleCursorAdapter cursorAdapter;

    public static ContactRepository getInstance(){
        if(instance == null){
            instance = new ContactRepository();
        }
        return instance;
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

    public JolgorioUser getContactFromPhone(String phoneNumber){
        for(JolgorioUser j: contacts){
            if(j.getNumber().equals(phoneNumber)){
                return j;
            }
        }
        return null;
    }
}
