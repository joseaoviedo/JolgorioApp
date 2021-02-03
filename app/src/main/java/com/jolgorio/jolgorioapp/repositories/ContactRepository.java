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
import com.jolgorio.jolgorioapp.tools.Configuration;
import com.jolgorio.jolgorioapp.tools.RestAPI;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;

public class ContactRepository extends Fragment {
    private static boolean dataLoaded;
    private static ArrayList<JolgorioUser> contacts;
    private static ArrayList<JolgorioUser> favContacts;
    private static ContactRepository instance;


    private ArrayList<String> phoneContacts;

    public static ContactRepository getInstance(){
        if(instance == null){
            instance = new ContactRepository();
        }
        return instance;
    }

    /*
    Almacena los usuarios que están relacionados con un número de teléfono en los contactos
     */
    private ContactRepository(){
        contacts = new ArrayList<>();
        favContacts = new ArrayList<>();
    }

    public ArrayList<JolgorioUser> getFavContacts() {
        return favContacts;
    }



    public  ArrayList<JolgorioUser> getContacts(){
        return contacts;
    }

    /*
    Verifica si un usuario corresponde a un contacto favorito
     */
    public boolean isFavorite(JolgorioUser contact){
        return favContacts.contains(contact);
    }

    /*
    Agrega un usuario a la lista de contactos favoritos
     */
    public void addFavorite(JolgorioUser contact){
        if (!favContacts.contains(contact)){
            favContacts.add(contact);
        }
    }

    /*
    Remueve un usuario de la lista de contactos favoritos
     */
    public void removeFavorite(JolgorioUser contact){
        favContacts.remove(contact);
    }

    /*
    Recibe una lista de string correspondiente a los números de teléfono de los contactos del celular,
    busca si existen usuarios registrados con ese número de teléfono, de ser así los agrega al
    repositorio
     */
    public void setContactList(List<String> contacts){
        ArrayList<String> contactList = new ArrayList<>();
        Iterator<String> iterator = contacts.iterator();
        while(iterator.hasNext()){
            String contact = iterator.next();
            contact = contact.trim();
            contact = contact.replace("+506", "");
            if(!contactList.contains(contact)){
                contactList.add(contact);
            }
        }
        phoneContacts = contactList;
        for(String number: phoneContacts){
            Log.d("Contacto", number);
        }
    }

    /*
    Carga los datos relacionados a un número de teléfono
     */
    public void loadData(){
        if(!dataLoaded) {
            if (contacts.isEmpty()) {
                for(String number: phoneContacts){
                    JolgorioUser user = loadUserFromDatabase(number);
                    if(user != null){
                        contacts.add(user);
                    }
                }
            }
            if (favContacts.isEmpty()) {
                favContacts = new ArrayList<>();
            }
        }
        dataLoaded = true;
    }

    public JolgorioUser loadUserFromDatabase(String number){
        try{
            JSONObject user = new RestAPI.GetQuerySingle().execute(Configuration.restApiUrl
                    + "usuario/numero/" + number + "/").get();
            int id = user.getInt("idusuario");
            String name = user.getString("nombre");
            String surname1 = user.getString("apellido1");
            String surname2 = user.getString("apellido2");
            int districtId = user.getInt("iddistrito");
            String email = user.getString("email");
            String birthDate = user.getString("fechanac");
            String userNumber = user.getString("numero");
            int sexo = user.getInt("sexo");
            String photoURL = user.getString("urlfoto");
            if(photoURL.isEmpty()){
                photoURL = "https://icon-library.com/images/default-user-icon/default-user-icon-4.jpg";
            }
            JolgorioUser result = new JolgorioUser(id, userNumber, email, name,surname1, surname2, photoURL);
            return result;
        }catch (Exception e){
            return null;
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
