package com.jolgorio.jolgorioapp.data.dummy;

import com.jolgorio.jolgorioapp.data.model.JolgorioUser;

import java.util.ArrayList;

public class ContactsDummy {
    private static ArrayList<JolgorioUser> contacts = new ArrayList<>();
    private static ArrayList<JolgorioUser> favContacts = new ArrayList<>();

    private static String defaultPhoto = "https://moonvillageassociation.org/wp-content/uploads/2018/06/default-profile-picture1.jpg";


    private static void loadData(){
        JolgorioUser u1 = new JolgorioUser(1, "test1", "12341234", "DEFAULT", "Juan", "Gonzales", "Castillo", defaultPhoto);
        JolgorioUser u2 = new JolgorioUser(2, "test2", "14141515", "DEFAULT", "Juana", "Castillo", "Gonzales", defaultPhoto);
        JolgorioUser u3 = new JolgorioUser(3, "test3", "12451245", "DEFAULT", "Mario", "Vargas", "Chavez", defaultPhoto);
        JolgorioUser u4 = new JolgorioUser(4, "test4", "85758575", "DEFAULT", "Maria", "Chavez", "Vargas", defaultPhoto);
        JolgorioUser u5 = new JolgorioUser(5, "test5", "96969696", "DEFAULT", "Luis", "Gonzales", "Castillo", defaultPhoto);
        JolgorioUser u6 = new JolgorioUser(6, "test6", "32353235", "DEFAULT", "Luisa", "Castillo", "Gonzales", defaultPhoto);
        JolgorioUser u7 = new JolgorioUser(7, "test7", "14521452", "DEFAULT", "Fernando", "Vargas", "Chavez", defaultPhoto);
        JolgorioUser u8 = new JolgorioUser(8, "test8", "74857485", "DEFAULT", "Fernanda", "Chavez", "Vargas", defaultPhoto);

        contacts.add(u1);
        contacts.add(u2);
        contacts.add(u3);
        contacts.add(u4);
        contacts.add(u5);
        contacts.add(u6);
        contacts.add(u7);
        contacts.add(u8);

        favContacts.add(u3);
        favContacts.add(u5);
        favContacts.add(u6);
    }

    public static ArrayList<JolgorioUser> getContacts(){
        if(contacts.isEmpty()){
            loadData();
        }
        return contacts;
    }

    public static ArrayList<JolgorioUser> getFavContacts(){
        if(contacts.isEmpty()){
            loadData();
        }
        return favContacts;
    }
}
