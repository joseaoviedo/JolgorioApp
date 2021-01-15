package com.jolgorio.jolgorioapp.data.model;

public class User {
    private int id;
    private String username;
    private String email;
    private String name;
    private String surname1;
    private String surname2;
    private String photoURL;

    public User(int id, String username, String email, String name, String surname1, String surname2, String photoURL){
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
        this.photoURL = photoURL;
    }

    public int getId(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    public String getEmail(){
        return email;
    }

    public String getName(){
        return name;
    }

    public String getSurname1(){
        return surname1;
    }

    public String getSurname2(){
        return surname2;
    }

    public String getPhotoURL(){
        return photoURL;
    }
}
