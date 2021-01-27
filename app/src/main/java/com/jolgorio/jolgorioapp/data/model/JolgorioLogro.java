package com.jolgorio.jolgorioapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class JolgorioLogro implements Parcelable {
    private int id;
    private int type;
    private String title;

    public JolgorioLogro(int id, int type, String title) {
        this.id = id;
        this.type = type;
        this.title = title;
    }

    protected JolgorioLogro(Parcel in) {
        id = in.readInt();
        type = in.readInt();
        title = in.readString();
    }

    public static final Parcelable.Creator<JolgorioLogro> CREATOR = new Parcelable.Creator<JolgorioLogro>() {
        @Override
        public JolgorioLogro createFromParcel(Parcel in) {
            return new JolgorioLogro(in);
        }

        @Override
        public JolgorioLogro[] newArray(int size) {
            return new JolgorioLogro[size];
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

}
