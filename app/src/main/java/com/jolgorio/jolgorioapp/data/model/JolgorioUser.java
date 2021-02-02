package com.jolgorio.jolgorioapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class JolgorioUser implements Parcelable {
    private int id;
    private String number;
    private String email;
    private String name;
    private String surname1;
    private String surname2;
    private String photoURL;

    public JolgorioUser(int id, String number, String email, String name, String surname1, String surname2, String photoURL){
        this.id = id;
        this.number = number;
        this.email = email;
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
        this.photoURL = photoURL;
    }

    protected JolgorioUser(Parcel in) {
        id = in.readInt();
        number = in.readString();
        email = in.readString();
        name = in.readString();
        surname1 = in.readString();
        surname2 = in.readString();
        photoURL = in.readString();
    }

    public static final Creator<JolgorioUser> CREATOR = new Creator<JolgorioUser>() {
        @Override
        public JolgorioUser createFromParcel(Parcel in) {
            return new JolgorioUser(in);
        }

        @Override
        public JolgorioUser[] newArray(int size) {
            return new JolgorioUser[size];
        }
    };

    public String getNumber() {
        return number;
    }

    public void setId(int id) {
        this.id = id;
    }



    public void setNumber(String number) {
        this.number = number;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public int getId(){
        return id;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(number);
        dest.writeString(email);
        dest.writeString(name);
        dest.writeString(surname1);
        dest.writeString(surname2);
        dest.writeString(photoURL);
    }
}
