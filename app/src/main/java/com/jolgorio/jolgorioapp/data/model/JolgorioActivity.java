package com.jolgorio.jolgorioapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class JolgorioActivity implements Parcelable {
    private int id;
    private int type;
    private String title;
    private String description;
    private String materials;
    private String videoLink;
    private String timeDescription;
    private boolean completed;

    /*
    Este modelo contiene la información de las actividades de la aplicación
     */
    public JolgorioActivity(int id, int type, String title, String description, String materials, String videoLink, String timeDescription, boolean completed) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.materials = materials;
        this.videoLink = videoLink;
        this.timeDescription = timeDescription;
        this.completed = completed;
    }

    protected JolgorioActivity(Parcel in) {
        id = in.readInt();
        type = in.readInt();
        title = in.readString();
        description = in.readString();
        materials = in.readString();
        videoLink = in.readString();
        timeDescription = in.readString();
        completed = in.readByte() != 0;
    }

    public static final Creator<JolgorioActivity> CREATOR = new Creator<JolgorioActivity>() {
        @Override
        public JolgorioActivity createFromParcel(Parcel in) {
            return new JolgorioActivity(in);
        }

        @Override
        public JolgorioActivity[] newArray(int size) {
            return new JolgorioActivity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(type);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(materials);
        dest.writeString(videoLink);
        dest.writeString(timeDescription);
        dest.writeByte((byte) (completed ? 1 : 0));
    }

    public int getId() {
        return id;
    }

    public int getType(){
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getTimeDescription(){
        return timeDescription;
    }

    public void setTimeDescription(String timeDescription){
        this.timeDescription = timeDescription;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean getCompleted(){
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
