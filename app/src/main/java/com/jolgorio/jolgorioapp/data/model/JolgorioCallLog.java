package com.jolgorio.jolgorioapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.api.client.util.DateTime;

import java.util.Date;

public class JolgorioCallLog implements Parcelable {
    JolgorioUser userCalled;
    Date date;

    public JolgorioCallLog(JolgorioUser userCalled, Date date){
        this.userCalled = userCalled;
        this.date = date;
    }

    protected JolgorioCallLog(Parcel in) {
        userCalled = in.readParcelable(JolgorioUser.class.getClassLoader());
    }

    public static final Creator<JolgorioCallLog> CREATOR = new Creator<JolgorioCallLog>() {
        @Override
        public JolgorioCallLog createFromParcel(Parcel in) {
            return new JolgorioCallLog(in);
        }

        @Override
        public JolgorioCallLog[] newArray(int size) {
            return new JolgorioCallLog[size];
        }
    };

    public JolgorioUser getUserCalled() {
        return userCalled;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(userCalled, flags);
    }
}
